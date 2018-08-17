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
import plantopia.sungshin.plantopia.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignInActivity extends AppCompatActivity {
    private static final int LOGIN = 2;
    private ServiceApiForUser service;

    @BindView(R.id.email)
    AutoCompleteTextView mEmailView;
    @BindView(R.id.password)
    EditText mPasswordView;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.sign_in_finish)
    Button signInFinishBtn;
    @BindView(R.id.facebook_btn)
    ImageButton facebookBtn;
    @BindView(R.id.ggl_btn)
    ImageButton googleBtn;
    @BindView(R.id.sign_up_btn)
    Button signUpBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

        //네트워크 연결
        ApplicationController applicationController = ApplicationController.getInstance();
        Log.d("url", new ServerURL().URL);
        applicationController.buildService(new ServerURL().URL, 3000);
        service = ApplicationController.getInstance().getService();

        signInFinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }

    public void cancelOnClicked(View view) {
        finish();
    }

    public void signUpBtnOnClicked(View view) {
        Intent signUpIntent = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(signUpIntent);
        finish();
    }

    public void userLogin(final String userEmail, final String userPwd) {
        LoginResult loginResult = new LoginResult(userEmail, userPwd);
        Call<LoginResult> userDataCall = service.getLoginResult(loginResult);
        userDataCall.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                if (response.isSuccessful()) {
                    LoginResult loginResult = response.body(); //받아온 데이터 받을 객체

                    if (loginResult.isResult()) {
                        Toast.makeText(getApplicationContext(),
                                loginResult.getUser_name() + "님 환영합니다!", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    //아이디, 비밀번호를 잘못 입력했을 경우
                    else {
                        mEmailView.setError(getString(R.string.sign_up_warning6));
                        mPasswordView.setError(getString(R.string.sign_up_warning6));
                    }
                } else {
                    Log.e("서버 문제 발생", response.code() + "");
                    Toast.makeText(getApplicationContext(), "로그인 오류 발생", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
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
            //showProgress(true);
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
