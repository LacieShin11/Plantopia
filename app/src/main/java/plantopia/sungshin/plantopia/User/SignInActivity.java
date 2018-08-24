package plantopia.sungshin.plantopia.User;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import plantopia.sungshin.plantopia.MainActivity;
import plantopia.sungshin.plantopia.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {
    private static final int LOGIN_SUCCESS = 5;
    private ServiceApiForUser service2;
    public static boolean isOktoken = false;

    @BindView(R.id.email)
    AutoCompleteTextView mEmailView;
    @BindView(R.id.password)
    EditText mPasswordView;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.sign_in_finish)
    Button signInFinishBtn;
    @BindView(R.id.ggl_btn)
    ImageButton googleBtn;
    @BindView(R.id.sign_up_btn)
    Button signUpBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        final int LOGIN_SUCCESS = 5;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

        //서버 연결
        ApplicationController applicationController = ApplicationController.getInstance();
        applicationController.buildService(ServerURL.URL, 3000);
        service2 = ApplicationController.getInstance().getService();

        isOktoken = checkToken(MainActivity.UserToken);

        signInFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }

    //토큰 중복 확인
    public boolean checkToken(String Token){
        //progressBar.setVisibility(View.VISIBLE);
        UserData userData = new UserData();
        userData.setUser_device(Token);
        //토큰값을 저장

        Call<UserData> userDataCall = service2.checkToken(userData);

        userDataCall.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                //progressBar.setVisibility(View.INVISIBLE);

                UserData checkToken = response.body(); //받아온 데이터 받을 객체
                Log.d("checkToken", checkToken.getMsg());

                isOktoken = checkToken.getMsg().equals("Already exist");

                if (checkToken.getMsg().equals("Already exist")) {
                    //Toast.makeText(getApplicationContext(), "토큰 이미 존재", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                //progressBar.setVisibility(View.INVISIBLE);
            }
        });

        return isOktoken;
    }

    public void cancelOnClicked(View view) {
        finish();
    }

    public void signUpBtnOnClicked(View view) {
        if (isOktoken) {
            Toast.makeText(getApplicationContext(), R.string.sign_up_warn_token, Toast.LENGTH_LONG).show();
        } //같은 토큰이 있을 경우
        else {
            Intent signUpIntent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(signUpIntent);
            finish();
        }
    }

    public void userLogin(final String userEmail, final String userPwd) {
        progressBar.setVisibility(View.VISIBLE);

        UserData loginResult = new UserData(userEmail, userPwd);
        Call<UserData> userDataCall = service2.getLoginResult(loginResult);
        userDataCall.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                progressBar.setVisibility(View.INVISIBLE);

                if (response.isSuccessful()) {
                    UserData loginResult = response.body(); //받아온 데이터 받을 객체

                    Log.d("loginResult", loginResult.getMsg());

                    if (loginResult.isResult()) {
                        AutoLoginManager.getInstance(getApplicationContext()).userLogin(loginResult);
                        Toast.makeText(getApplicationContext(),
                                loginResult.getUser_name() + "님 환영합니다!", Toast.LENGTH_SHORT).show();

                        setResult(LOGIN_SUCCESS);
                        finish();
                    }
                    //아이디, 비밀번호를 잘못 입력했을 경우
                    else {
                        if (loginResult.getMsg().equals("Account not exist"))
                            mEmailView.setError(getString(R.string.sign_up_warning7));
                        else if (loginResult.getMsg().equals("Password wrong"))
                            mPasswordView.setError(getString(R.string.sign_up_warning6));
                    }
                } else {
                    Log.e("서버 문제 발생", response.code() + "");
                    Toast.makeText(getApplicationContext(), "로그인 오류 발생", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);

                Log.e("서버 접속 에러 발생 ", t.getMessage());
                Toast.makeText(getApplicationContext(), "로그인 오류 발생", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void attemptLogin() {
        mEmailView.setError(null);
        mPasswordView.setError(null);

        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // 비밀번호 유효성 확인
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.sign_up_warning4));
            focusView = mPasswordView;
            cancel = true;
        } else if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.sign_up_warning2));
            focusView = mPasswordView;
            cancel = true;
        }

        // 이메일 주소 유효성 확인
        if (TextUtils.isEmpty(email) || !isEmailValid(email)) {
            mEmailView.setError(getString(R.string.sign_up_warning5));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            userLogin(email, password);
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 8;
    }


}
