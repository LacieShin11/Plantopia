package plantopia.sungshin.plantopia.Diray;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import plantopia.sungshin.plantopia.MainActivity;
import plantopia.sungshin.plantopia.R;
import plantopia.sungshin.plantopia.User.ApplicationController;
import plantopia.sungshin.plantopia.User.AutoLoginManager;
import plantopia.sungshin.plantopia.User.ServerURL;
import plantopia.sungshin.plantopia.User.ServiceApiForUser;
import plantopia.sungshin.plantopia.User.UserData;
import plantopia.sungshin.plantopia.customView.SquareImgView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WriteNewDiaryActivity extends AppCompatActivity {
    private static final int TAKING_PIC = 10;
    private static final String BUCKET_NAME = "plantopiabucket";

    private Unbinder unbinder;
    ServiceApiForUser service;
    Uri diaryUri = null;
    @BindView(R.id.diary_img)
    SquareImgView diaryImg;
    @BindView(R.id.diary_edit)
    EditText diaryEdit;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_new_diary);
        unbinder = ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressBar.setVisibility(View.INVISIBLE);

        //서버 연결
        ApplicationController applicationController = ApplicationController.getInstance();
        applicationController.buildService(ServerURL.URL, 3000);
        service = ApplicationController.getInstance().getService();

        diaryImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence[] list = {"사진 촬영", "갤러리 사진 선택"};
                AlertDialog.Builder builder = new AlertDialog.Builder(WriteNewDiaryActivity.this);
                builder.setTitle("사진 추가")
                        .setItems(list, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                                    if (takePictureIntent.resolveActivity(getPackageManager()) != null)
                                        startActivityForResult(takePictureIntent, TAKING_PIC);

                                } else {
                                    Crop.pickImage(WriteNewDiaryActivity.this);
                                }
                            }
                        });

                builder.show();
            }
        });
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
            diaryUri = Crop.getOutput(result);
            diaryImg.setImageURI(diaryUri);

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Crop.getOutput(result));
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_diary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_submit) {
            if (diaryEdit.getText().toString().isEmpty())
                Toast.makeText(getApplicationContext(), "내용을 작성해주세요.", Toast.LENGTH_SHORT).show();
            else if (diaryUri == null)
                Toast.makeText(getApplicationContext(), "사진을 선택해주세요.", Toast.LENGTH_SHORT).show();
            else {
                //DB에 저장
                progressBar.setVisibility(View.VISIBLE);
                final File uploadFile = new File(diaryUri.getPath());

                CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                        getApplicationContext(),
                        "us-east-2:f6e0a9b0-267d-46c4-9512-01c8d7502762", // 자격 증명 풀 ID
                        Regions.US_EAST_2 // 리전
                );

                AmazonS3 s3 = new AmazonS3Client(credentialsProvider);
                s3.setRegion(Region.getRegion(Regions.AP_NORTHEAST_2));
                s3.setEndpoint("s3.ap-northeast-2.amazonaws.com");
                TransferUtility utility = new TransferUtility(s3, getApplicationContext());

                utility.upload(BUCKET_NAME, "diary_" + uploadFile.getName() + ".png", uploadFile); //s3에 파일 업로드

                final UserData userData = AutoLoginManager.getInstance(getApplicationContext()).getUser();

                GregorianCalendar today = new GregorianCalendar();
                int year = today.get(today.YEAR);
                int month = today.get(today.MONTH) + 1;
                int day = today.get(today.DAY_OF_MONTH);

                final DiaryItem diaryItem = new DiaryItem(userData.getUser_id(), ServerURL.BUCKET + "diary_" + uploadFile.getName() + ".png",
                        diaryEdit.getText().toString(), year + "년 " + month + "월 " + day + "일");

                //다이어리 insert 통신
                Call<UserData> addDiaryCall = service.addDiary(diaryItem);
                addDiaryCall.enqueue(new Callback<UserData>() {
                    @Override
                    public void onResponse(Call<UserData> call, Response<UserData> response) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(WriteNewDiaryActivity.this, "다이어리 작성 완료", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<UserData> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        t.printStackTrace();
                        Toast.makeText(WriteNewDiaryActivity.this, R.string.name_error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return true;
    }
}
