package plantopia.sungshin.plantopia;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignInActivity extends AppCompatActivity {
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
    }

    public void finishBtnOnClicked(View view) {

    }

    public void signUpBtnOnClicked(View view) {
        Intent signUpIntent = new Intent(SignInActivity.this, SignUpActivity.class);
        startActivity(signUpIntent);
        finish();
    }
}
