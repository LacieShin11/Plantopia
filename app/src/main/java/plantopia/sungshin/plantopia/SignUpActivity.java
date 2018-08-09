package plantopia.sungshin.plantopia;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity {
    @BindView(R.id.email_warning_text)
    TextView emailWarningText;
    @BindView(R.id.pwd_warning_text)
    TextView pwdWarningText;
    @BindView(R.id.pwd_warning_text2)
    TextView pwdWarningText2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

        emailWarningText.setVisibility(View.GONE);
        pwdWarningText2.setVisibility(View.GONE);
//        pwdWarningText.setVisibility(View.GONE);


    }
}
