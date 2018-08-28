package plantopia.sungshin.plantopia.User;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.bumptech.glide.Glide;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import plantopia.sungshin.plantopia.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileSettingActivity extends AppCompatActivity {
    private static final int TAKING_PIC = 10;
    private static final int LOGOUT = 4;
    private static final String BUCKET_NAME = "plantopiabucket";

    private ServiceApiForUser service;
    UserData loginedUser;
    @BindView(R.id.profile_img)
    CircleImageView profileImg;
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
        setTitle("프로필 설정");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //서버 연결
        ApplicationController applicationController = ApplicationController.getInstance();
        applicationController.buildService(ServerURL.URL, 3000);
        service = ApplicationController.getInstance().getService();

        //화면에 표시될 이메일, 닉네임 설정
        loginedUser = AutoLoginManager.getInstance(getApplicationContext()).getUser();
        emailAddressText.setText(loginedUser.getUser_email());
        idText.setText(loginedUser.getUser_name());

        if (loginedUser.getUser_img() == null)
            profileImg.setImageResource(R.drawable.add_profile_images_02);
        else
            Glide.with(getApplicationContext()).load(loginedUser.getUser_img()).into(profileImg);

        //닉네임 최대 글자수 표시
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
                    showMessage(getString(R.string.check_name2));
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
                showMessage(getString(R.string.check_name));
            else if (idEdit.getText().toString().length() > 10)
                showMessage(getString(R.string.check_name2));
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

    //닉네임 변경
    public void changeName(String name) {
        progressBar.setVisibility(View.VISIBLE);
        final UserData sendData = new UserData();
        sendData.setUser_email(loginedUser.getUser_email());
        sendData.setUser_name(name);

        Call<UserData> userDataCall = service.setUserName(sendData);
        userDataCall.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.d("닉네임", response.body().getUser_name());

                if (response.isSuccessful()) {
                    AutoLoginManager.getInstance(getApplicationContext()).setUserName(sendData.getUser_name());
                    idText.setText(sendData.getUser_name());
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
        CharSequence[] list = {"사진 촬영", "갤러리 사진 선택"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ProfileSettingActivity.this);
        builder.setTitle("프로필 사진 변경")
                .setItems(list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                            if (takePictureIntent.resolveActivity(getPackageManager()) != null)
                                startActivityForResult(takePictureIntent, TAKING_PIC);

                        } else if (which == 1) {
                            Crop.pickImage(ProfileSettingActivity.this);
                        }
                    }
                });

        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("경로", "resultCode : " + resultCode + " requestCode : " + requestCode);

        if (requestCode == TAKING_PIC && resultCode == RESULT_OK) {
            beginCrop(data.getData());
        } else if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(data.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, data);
        }
    }

    private void beginCrop(Uri source) {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        Uri destination = Uri.fromFile(new File(getCacheDir(), timestamp));
        Crop.of(source, destination).withAspect(1, 1).start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            changeImg(Crop.getOutput(result));

        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //프로필 이미지 변경
    public void changeImg(final Uri fileUri) {
        progressBar.setVisibility(View.VISIBLE);
        final File uploadFile = new File(fileUri.getPath());

        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                getApplicationContext(),
                "us-east-2:f6e0a9b0-267d-46c4-9512-01c8d7502762", // 자격 증명 풀 ID
                Regions.US_EAST_2 // 리전
        );

        AmazonS3 s3 = new AmazonS3Client(credentialsProvider);
        s3.setRegion(Region.getRegion(Regions.AP_NORTHEAST_2));
        s3.setEndpoint("s3.ap-northeast-2.amazonaws.com");
        TransferUtility utility = new TransferUtility(s3, getApplicationContext());

        utility.upload(BUCKET_NAME, "profile_" + uploadFile.getName() + ".png", uploadFile); //s3에 파일 업로드

        final UserData userData = AutoLoginManager.getInstance(getApplicationContext()).getUser();
        userData.setUser_img(ServerURL.BUCKET + "profile_" + uploadFile.getName() + ".png");

        Call<UserData> userDataCall = service.updateUserImg(userData);
        userDataCall.enqueue(new Callback<UserData>() {
            @Override
            public void onResponse(Call<UserData> call, Response<UserData> response) {
                progressBar.setVisibility(View.INVISIBLE);

                if (response.isSuccessful()) {
                    AutoLoginManager.getInstance(getApplicationContext()).setUserImg(response.body().getUser_img());

                    profileImg.setImageURI(fileUri);
                } else
                    Toast.makeText(ProfileSettingActivity.this, R.string.name_error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<UserData> call, Throwable t) {
                progressBar.setVisibility(View.INVISIBLE);
                Log.d("이미지", t.getMessage());
                Toast.makeText(ProfileSettingActivity.this, R.string.name_error, Toast.LENGTH_SHORT).show();
            }
        });
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();

        return true;
    }
}
