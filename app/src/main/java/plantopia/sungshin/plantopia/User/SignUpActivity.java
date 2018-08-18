package plantopia.sungshin.plantopia.User;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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

public class SignUpActivity extends AppCompatActivity {
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.sign_up_finish)
    Button signUpFinish;

    @BindView(R.id.join_email)
    AutoCompleteTextView joinEmailEdit;
    @BindView(R.id.join_pwd)
    AutoCompleteTextView joinPwdEdit;
    @BindView(R.id.join_pwd_confirm)
    AutoCompleteTextView joinPwdConfirmEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.bind(this);

    }


    public void cancelOnClicked(View view) {
        finish();
    }
}
