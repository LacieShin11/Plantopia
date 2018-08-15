package plantopia.sungshin.plantopia;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.os.Build.VERSION_CODES.BASE;

public class SignInActivity extends AppCompatActivity {
    private static final int LOGIN = 2;

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

    private void userLogin(final String email, final String pwd) {
        final String URL_LOGIN = "http://ec2-13-125-140-255.ap-northeast-2.compute.amazonaws.com:3001/test/3/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL_LOGIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetService service = retrofit.create(GetService.class);
        Call<List<UserData>> call = service.listDummies("5");

        Callback dummies = new Callback<List<UserData>>(){
            @Override
            public void onResponse(Call<List<UserData>> call, retrofit2.Response<List<UserData>> response) {
                if (response.isSuccessful()) {
                    List<UserData> dummies = response.body();

                    for(UserData userData : dummies) {
                        Log.d("데이터 확인", userData.getUserEmail() + " " + userData.getUserPwd());
                    }
                } else {
                    Log.d("데이터 오류", String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<List<UserData>> call, Throwable t) {
                Log.d("데이터 실패", t.getMessage());
            }
        };

        call.enqueue(dummies);
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
