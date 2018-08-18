package plantopia.sungshin.plantopia;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import plantopia.sungshin.plantopia.User.ApplicationController;
import plantopia.sungshin.plantopia.User.AutoLoginManager;
import plantopia.sungshin.plantopia.User.ServerURL;
import plantopia.sungshin.plantopia.User.ServiceApiForUser;
import plantopia.sungshin.plantopia.User.UserData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileSettingActivity extends AppCompatActivity {
    private static final int LOGOUT = 4;

    private ServiceApiForUser service;
    UserData loginedUser;
    @BindView(R.id.change_profile_btn)
    ImageButton changProfileBtn;
    @BindView(R.id.change_name_btn)
    ImageButton changeNameBtn;
    @BindView(R.id.email_address_text)
    TextView emailAddressText;
    @BindView(R.id.id_text)
    TextView idText;
    @BindView(R.id.id_edit_layout)
    TextInputLayout inputLayout;
    @BindView(R.id.id_edit)
    TextInputEditText idEdit;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);
        ButterKnife.bind(this);

        //서버 연결
        ApplicationController applicationController = ApplicationController.getInstance();
        applicationController.buildService(ServerURL.URL, 3000);
        service = ApplicationController.getInstance().getService();

        //화면에 표시될 이메일, 닉네임 설정
        loginedUser = AutoLoginManager.getInstance(getApplicationContext()).getUser();
        emailAddressText.setText(loginedUser.getUser_email());
        idText.setText(loginedUser.getUser_name());

        inputLayout.setCounterEnabled(true);
        inputLayout.setCounterMaxLength(10);
        inputLayout.setVisibility(View.GONE);

        idEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (idEdit.getText().toString().length() > 10)
                    showMessage("10자 이하로 입력해주세요.");
                else hideMessage();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void logoutBtnOnClicked(View view) {
        AutoLoginManager.getInstance(getApplicationContext()).logout();
        setResult(LOGOUT);
        finish();
    }

    public void changeNameBtnOnClicked(View view) {
        if (idText.getVisibility() == View.VISIBLE) {
            inputLayout.setVisibility(View.VISIBLE);
            idEdit.setText(idText.getText());
            idText.setVisibility(View.GONE);
            changeNameBtn.setImageResource(R.drawable.submit);
        } else {
            if (idEdit.getText().toString().isEmpty())
                showMessage("이름을 입력해주세요.");
            else if (idEdit.getText().toString().length() > 10)
                showMessage("10자 이하로 입력해주세요.");
            //닉네임 변경
            else {
                hideMessage();
                changeName(idEdit.getText().toString());

                inputLayout.setVisibility(View.GONE);
                idText.setVisibility(View.VISIBLE);
                changeNameBtn.setImageResource(R.drawable.name);

                idText.setText(AutoLoginManager.getInstance(getApplicationContext()).getUser().getUser_name());
            }
        }
    }

    private void showMessage(String msg) {
        inputLayout.setErrorEnabled(true);
        inputLayout.setError(msg);
    }

    private void hideMessage() {
        inputLayout.setErrorEnabled(false);
        inputLayout.setError(null);
    }

    public void changeName(String name) {
        progressBar.setVisibility(View.VISIBLE);
        UserData sendData = new UserData(loginedUser.getUser_id(), loginedUser.getUser_email(), name);

        Call<UserData> userDataCall = service.setUserName(sendData);
        userDataCall.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.d("닉네임", response.body().getMsg());

                if (response.isSuccessful()) {
                    UserData newName = response.body(); //받아온 데이터 받을 객체
                    AutoLoginManager.getInstance(getApplicationContext()).setUserName(newName.getUser_name());
                    Log.d("닉네임", newName.getMsg());
                    idText.setText(newName.getUser_name());
                } else {
                    Toast.makeText(ProfileSettingActivity.this, R.string.name_error, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.d("닉네임", t.getMessage());
                Toast.makeText(ProfileSettingActivity.this, R.string.name_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void cameraBtnOnClicked(View view) {
    }

    public void deleteAccountBtnOnClicked(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileSettingActivity.this);
        builder.setTitle("회원 탈퇴")
                .setMessage("Plantopia 계정을 영구 삭제하시겠습니까? 삭제된 계정의 모든 데이터는 복구가 불가능합니다.")
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }
}
