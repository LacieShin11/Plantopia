package plantopia.sungshin.plantopia;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {
    @BindView(R.id.email_warning_text)
    TextView emailWarningText;
    @BindView(R.id.pwd_warning_text)
    TextView pwdWarningText;
    @BindView(R.id.pwd_warning_text2)
    TextView pwdWarningText2;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.sign_up_email_edit)
    EditText emailEdit;
    @BindView(R.id.sign_up_pwd_edit)
    EditText pwdEdit;
    @BindView(R.id.sign_up_pwd_confirm)
    EditText pwdConfirmEdit;
    @BindView(R.id.sign_up_finish)
    Button signUpFinish;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        emailWarningText.setVisibility(View.GONE);
        pwdWarningText2.setVisibility(View.GONE);
        pwdWarningText.setVisibility(View.GONE);

        View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                switch (v.getId()) {
                    case R.id.sign_up_email_edit:
                        if (!hasFocus) {
                            if (emailEdit.getText().toString().isEmpty()) {
                                emailWarningText.setText("이메일을 입력해주세요.");
                                emailWarningText.setVisibility(View.VISIBLE);
                            } else {
                                emailWarningText.setVisibility(View.GONE);
                            }
                        }
                        break;

                    case R.id.sign_up_pwd_edit:
                        if (!hasFocus) {
                            if (pwdEdit.getText().toString().isEmpty()) {
                                pwdWarningText.setText("비밀번호를 입력해주세요.");
                                pwdWarningText.setVisibility(View.VISIBLE);
                            } else if (pwdEdit.length() < 8) {
                                pwdWarningText.setText("8자 이상의 비밀번호를 입력해주세요.");
                                pwdWarningText.setVisibility(View.VISIBLE);
                            } else {
                                pwdWarningText.setVisibility(View.GONE);
                            }
                        }
                        break;

                    case R.id.sign_up_pwd_confirm:
                        if (!hasFocus) {
                            if (pwdEdit.equals("")) {
                                pwdWarningText2.setText("비밀번호를 한번 더 입력해주세요.");
                                pwdWarningText2.setVisibility(View.VISIBLE);
                            } else if (!pwdConfirmEdit.getText().toString().equals(pwdEdit.getText().toString())) {
                                pwdWarningText2.setText("비밀번호가 일치하지 않습니다.");
                                pwdWarningText2.setVisibility(View.VISIBLE);
                            } else {
                                pwdWarningText2.setVisibility(View.GONE);
                            }
                        }
                        break;
                }
            }
        };

        //완료버튼 눌렀을 때 이번트
        //타자 입력 이벤트

        emailEdit.setOnFocusChangeListener(focusChangeListener);
        pwdEdit.setOnFocusChangeListener(focusChangeListener);
        pwdConfirmEdit.setOnFocusChangeListener(focusChangeListener);

        signUpFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //모든 입력양식이 올바를 때 통신 시작
                if (emailWarningText.getVisibility() == View.GONE && pwdWarningText.getVisibility() == View.GONE
                        && pwdWarningText2.getVisibility() == View.GONE) {
                    registerUser();
                } else {
                    Toast.makeText(getApplicationContext(), "입력양식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registerUser() {
        final String emailStr = emailEdit.getText().toString();
        final String pwdStr = pwdEdit.getText().toString();
        final String pwdConfirmStr = pwdConfirmEdit.getText().toString();
        final String registerUrl = "sftp://ec2-user@ec2-13-125-227-30.ap-northeast-2.compute.amazonaws.com/home/ec2-user/login.js";

        StringRequest request = new StringRequest(Request.Method.POST, registerUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);

                        try {
                            JSONObject obj = new JSONObject(response);

                            if (!obj.getBoolean("error")) {
                                JSONObject userJson = obj.getJSONObject("user");

                                //creating a new user object
                                UserData user = new UserData(userJson.getInt("id"),
                                        userJson.getString("username"),
                                        userJson.getString("email")
                                );

                                AutoLoginManager.getInstance(getApplicationContext()).userLogin(user);

                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), "로그인 오류 발생. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "로그인 오류 발생. 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                Log.d("로그인 에러", error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", emailStr);
                params.put("pwd", pwdStr);

                return params;
            }
        };
    }

    public void cancelOnClicked(View view) {
        finish();
    }
}
