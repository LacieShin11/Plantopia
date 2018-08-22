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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import plantopia.sungshin.plantopia.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    static final int NEXT = 7;
    boolean isOk = false;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.sign_up_finish)
    Button signUpFinish;

    ServiceApiForUser service;
    @BindView(R.id.join_email)
    AutoCompleteTextView mEmailView;
    @BindView(R.id.join_pwd)
    AutoCompleteTextView mPwdView;
    @BindView(R.id.join_pwd_confirm)
    AutoCompleteTextView mPwdConfirmView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        //서버 연결
        ApplicationController applicationController = ApplicationController.getInstance();
        applicationController.buildService(ServerURL.URL, 3000);
        service = ApplicationController.getInstance().getService();
    }

    private void attemptRegister() {
        mEmailView.setError(null);
        mPwdView.setError(null);
        mPwdConfirmView.setError(null);

        String email = mEmailView.getText().toString();
        String password = mPwdView.getText().toString();
        String passwordConfirm = mPwdConfirmView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // 비밀번호 유효성 확인
        if (TextUtils.isEmpty(password)) {
            mPwdView.setError(getString(R.string.sign_up_warning4));
            focusView = mPwdView;
            cancel = true;
        } else if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPwdView.setError(getString(R.string.sign_up_warning2));
            focusView = mPwdView;
            cancel = true;
        }

        //비밀번호 재입력 유효성 확인
        if (TextUtils.isEmpty(passwordConfirm)) {
            mPwdConfirmView.setError(getString(R.string.sign_up_warning8));
            focusView = mPwdConfirmView;
            cancel = true;
        } else if (!TextUtils.isEmpty(passwordConfirm) && !passwordConfirm.equals(password)) {
            mPwdConfirmView.setError(getString(R.string.sign_up_warning3));
            focusView = mPwdConfirmView;
            cancel = true;
        }

        // 이메일 주소 유효성 확인
        if (TextUtils.isEmpty(email) || !isEmailValid(email)) {
            mEmailView.setError(getString(R.string.sign_up_warning5));
            focusView = mEmailView;
            cancel = true;
        } else if (checkDuplication(email)) {
            mEmailView.setError(getString(R.string.sign_up_warning));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else { //닉네임 설정 페이지로 넘어가기
            Intent intent = new Intent(SignUpActivity.this, SignUpActivity2.class);
            intent.putExtra("email", email);
            intent.putExtra("password", password);
            startActivityForResult(intent, NEXT);
            finish();
        }
    }

    //이메일 중복 확인
    private boolean checkDuplication(String email) {
        progressBar.setVisibility(View.VISIBLE);

        UserData userData = new UserData();
        userData.setUser_email(email);

        Call<UserData> userDataCall = service.checkDuplication(userData);
        userDataCall.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                progressBar.setVisibility(View.INVISIBLE);

                UserData checkResult = response.body(); //받아온 데이터 받을 객체
                Log.d("checkResult", checkResult.getMsg());

                isOk = checkResult.getMsg().equals("Already exist");

                if (checkResult.getMsg().equals("Already exist")) {
                    mEmailView.setError(getString(R.string.sign_up_warning));
                }
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(SignUpActivity.this, R.string.name_error, Toast.LENGTH_SHORT).show();
            }
        });

        return isOk;
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 8;
    }

    public void cancelOnClicked(View view) {
        finish();
    }

    public void finishBtnOnClicked(View view) {
        attemptRegister();
    }
}
