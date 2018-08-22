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
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.soundcloud.android.crop.Crop;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import plantopia.sungshin.plantopia.ProfileSettingActivity;
import plantopia.sungshin.plantopia.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity2 extends AppCompatActivity {
    private static final int TAKING_PIC = 10;
    private static final String BUCKET_NAME = "plantopiabucket";
    private Uri profileImgUri = null;

    private ServiceApiForUser service;
    private UserData joinUser = new UserData();

    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.join_name_layout)
    TextInputLayout mNameLayout;
    @BindView(R.id.join_name)
    TextInputEditText mNameView;
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

        progressBar.setVisibility(View.INVISIBLE);

        //서버 연결
        ApplicationController applicationController = ApplicationController.getInstance();
        applicationController.buildService(ServerURL.URL, 3000);
        service = ApplicationController.getInstance().getService();

        //닉네임 최대 글자수 표시
        mNameLayout.setCounterEnabled(true);
        mNameLayout.setCounterMaxLength(10);

        mNameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mNameView.getText().toString().length() > 10)
                    showMessage(getString(R.string.check_name2));
                else hideMessage();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void userJoin(UserData userData) {
        progressBar.setVisibility(View.VISIBLE);

        if (profileImgUri != null) {
            final File uploadFile = new File(profileImgUri.getPath());

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

            if (mNameView.getText().toString().equals(""))
                userData.setUser_name("신규 사용자");
            else
                userData.setUser_name(mNameView.getText().toString());

            if (profileImgUri == null)
                userData.setUser_img("");
            else
                userData.setUser_img(ServerURL.BUCKET + "profile_" + uploadFile.getName() + ".png");

            userData.setCount_pot(0);
            userData.setCount_diary(0);
            userData.setCount_scrap(0);

            Call<UserData> userDataCall = service.getJoinResult(userData);
            userDataCall.enqueue(new Callback<UserData>() {
                @Override
                public void onResponse(Call<UserData> call, Response<UserData> response) {
                    progressBar.setVisibility(View.INVISIBLE);

                    Toast.makeText(SignUpActivity2.this, R.string.sign_up_success, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignUpActivity2.this, SignInActivity.class));
                    finish();
                }

                @Override
                public void onFailure(Call<UserData> call, Throwable t) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Log.d("회원가입", t.getMessage());
                    Toast.makeText(SignUpActivity2.this, R.string.name_error, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void skipBtnOnClicked(View view) {
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

    public void finishBtnOnClicked(View view) {
        boolean cancel = true;

        if (TextUtils.isEmpty(mNameView.getText()))
            showMessage(getString(R.string.check_name));
        else if (mNameView.getText().toString().length() > 10)
            showMessage(getString(R.string.check_name2));
        else if (profileImgUri == null)
            Toast.makeText(this, "프로필 사진을 선택해주세요.", Toast.LENGTH_SHORT).show();
        else {
            hideMessage();
            userJoin(joinUser);
        }
    }

    public void cameraBtnOnClicked(View view) {
        CharSequence[] list = {"사진 촬영", "갤러리 사진 선택"};
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity2.this);
        builder.setTitle("사진 추가")
                .setItems(list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                            if (takePictureIntent.resolveActivity(getPackageManager()) != null)
                                startActivityForResult(takePictureIntent, TAKING_PIC);

                        } else if (which == 1) {
                            Crop.pickImage(SignUpActivity2.this);
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
            profileImgUri = Crop.getOutput(result);
            profileImg.setImageURI(Crop.getOutput(result));
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity2.this);
        builder.setTitle(getString(R.string.cancel_join))
                .setMessage(getString(R.string.cancel_join_msg))
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }

    private void showMessage(String msg) {
        mNameLayout.setErrorEnabled(true);
        mNameLayout.setError(msg);
    }

    private void hideMessage() {
        mNameLayout.setErrorEnabled(false);
        mNameLayout.setError(null);
    }
}
