package plantopia.sungshin.plantopia;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignInActivity extends AppCompatActivity {
    private static final int LOGIN = 2;

    @BindView(R.id.email_warning_text)
    TextView emailWarningText;
    @BindView(R.id.pwd_warning_text)
    TextView pwdWarningText;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.sign_in_email_edit)
    EditText emailEdit;
    @BindView(R.id.sign_in_pwd)
    EditText pwdEdit;
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

        emailWarningText.setVisibility(View.GONE);
        pwdWarningText.setVisibility(View.GONE);

        emailEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

            }
        });
    }

    public void cancelOnClicked(View view) {
        finish();
    }

    public void finishBtnOnClicked(View view) {
        if (!emailEdit.getText().toString().isEmpty() && !pwdEdit.getText().toString().isEmpty()) {
            userLogin();
        } else {
            Toast.makeText(getApplicationContext(), "입력양식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    public void signUpBtnOnClicked(View view) {
        Intent signUpIntent = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(signUpIntent);
        finish();
    }

    private void userLogin() {
        final String emailStr = emailEdit.getText().toString();
        final String pwdStr = pwdEdit.getText().toString();
        final String loginUrl = "http://ec2-user@ec2-13-125-227-30.ap-northeast-2.compute.amazonaws.com/home/ec2-user/login.js";
        //sftp://ec2-user@ec2-13-125-227-30.ap-northeast-2.compute.amazonaws.com/home/ec2-user/login.js

        StringRequest stringRequest = new StringRequest(Request.Method.POST, loginUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressBar.setVisibility(View.GONE);

                        try {
                            //converting response to json object
                            JSONObject obj = new JSONObject(response);

                            //if no error in response
                            if (!obj.getBoolean("error")) {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                                //getting the user from the response
                                JSONObject userJson = obj.getJSONObject("user");

                                //creating a new user object
                                UserData user = new UserData(
                                        userJson.getInt("id"),
                                        userJson.getString("username"),
                                        userJson.getString("email")
                                );

                                //storing the user in shared preferences
                                AutoLoginManager.getInstance(getApplicationContext()).userLogin(user);

                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
