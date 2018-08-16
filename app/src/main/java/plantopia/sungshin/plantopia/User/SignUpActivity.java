package plantopia.sungshin.plantopia.User;

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
import plantopia.sungshin.plantopia.R;

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

    }

    public void cancelOnClicked(View view) {
        finish();
    }
}
