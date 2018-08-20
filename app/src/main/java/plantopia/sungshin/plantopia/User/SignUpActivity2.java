package plantopia.sungshin.plantopia.User;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import plantopia.sungshin.plantopia.R;

public class SignUpActivity2 extends AppCompatActivity {

    private UserData joinUser;
    @BindView(R.id.join_name)
    AutoCompleteTextView mNameView;
    @BindView(R.id.change_profile_btn)
    ImageButton changeProfileBtn;
    @BindView(R.id.join_skip_btn)
    Button joinSkipBtn;
    @BindView(R.id.join_finish_btn)
    Button joinFinishBtn;
    @BindView(R.id.profile_img)
    CircleImageView profileImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up2);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        joinUser.setUser_email(intent.getStringExtra("email"));
        joinUser.setUser_pwd(intent.getStringExtra("password"));
    }


    private void userJoin(UserData userData) {

    }

    private void skipBtnOnClicked(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity2.this);
        builder.setTitle(getString(R.string.skip))
                .setMessage(getString(R.string.skip_msg))
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userJoin(joinUser);
                    }
                })
                .show();
    }

    private void finishBtnOnClicked(View view) {

    }
}
