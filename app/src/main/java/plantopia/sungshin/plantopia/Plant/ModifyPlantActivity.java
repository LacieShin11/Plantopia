package plantopia.sungshin.plantopia.Plant;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.bumptech.glide.Glide;
import com.joooonho.SelectableRoundedImageView;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import plantopia.sungshin.plantopia.R;
import plantopia.sungshin.plantopia.User.ApplicationController;
import plantopia.sungshin.plantopia.User.AutoLoginManager;
import plantopia.sungshin.plantopia.User.ServerURL;
import plantopia.sungshin.plantopia.User.ServiceApiForUser;
import plantopia.sungshin.plantopia.User.UserData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModifyPlantActivity extends AppCompatActivity {

    private static final String BUCKET_NAME = "plantopiabucket";
    PlantItem plantItem;
    Uri plantUri;
    private ServiceApiForUser service;
    @BindView(R.id.plant_img)
    SelectableRoundedImageView plantImg;
    @BindView(R.id.name_edit)
    TextInputEditText nameEdit;
    @BindView(R.id.name_edit_layout)
    TextInputLayout textInputLayout;
    @BindView(R.id.plant_type_edit)
    AutoCompleteTextView plantTypeEdit;
    @BindView(R.id.search_plant_btn)
    ImageButton searchBtn;
    @BindView(R.id.connect_switch)
    Switch connectSwitch;
    @BindView(R.id.connect_edit)
    EditText connectEdit;
    @BindView(R.id.switch_layout)
    LinearLayout switchLayout;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.line)
    View line;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_plant);
        ButterKnife.bind(this);

        setTitle("플랜트 설정");
        progressBar.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        plantItem = (PlantItem) intent.getSerializableExtra("plantItem");

        //서버 연결
        ApplicationController applicationController = ApplicationController.getInstance();
        applicationController.buildService(ServerURL.URL, 3000);
        service = ApplicationController.getInstance().getService();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setStatus();
        connectSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switchLayout.setVisibility((isChecked) ? View.VISIBLE : View.GONE);
                line.setVisibility((isChecked) ? View.VISIBLE : View.GONE);
            }
        });

        textInputLayout.setCounterEnabled(true);
        textInputLayout.setCounterMaxLength(12);

        nameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (nameEdit.getText().toString().length() > 12)
                    showMessage(getString(R.string.check_name3));
                else hideMessage();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void showMessage(String msg) {
        textInputLayout.setErrorEnabled(true);
        textInputLayout.setError(msg);
    }

    private void hideMessage() {
        textInputLayout.setErrorEnabled(false);
        textInputLayout.setError(null);
    }

    //기존 식물 정보 가져오기
    public void setStatus() {
        Intent intent = getIntent();
        plantItem = (PlantItem) intent.getSerializableExtra("plantItem");
        nameEdit.setText(plantItem.getPlant_name());
        plantTypeEdit.setText(plantItem.getPlant_type());

        connectSwitch.setChecked(plantItem.isPlant_connect() == 1);

        switchLayout.setVisibility(connectSwitch.isChecked() ? View.VISIBLE : View.GONE);
        line.setVisibility((connectSwitch.isChecked()) ? View.VISIBLE : View.GONE);

        if (switchLayout.getVisibility() == View.VISIBLE) {
            connectEdit.setEnabled(true);
            connectEdit.setText(plantItem.getPlant_id() + "");
        } else {
            connectEdit.setEnabled(false);
        }

        Glide.with(this).load(plantItem.getPlant_img()).into(plantImg);
    }

    public void cameraBtnOnClicked(View view) {

    }

    public void deleteBtnOnClicked(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ModifyPlantActivity.this);
        builder.setTitle("플랜트 삭제")
                .setMessage("식물을 삭제하시겠습니까? 삭제한 식물의 기록은 복구가 불가능합니다.")
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Call<UserData> diaryCall = service.deletePlant(plantItem.getPlant_id());
                        diaryCall.enqueue(new Callback<UserData>() {
                            @Override
                            public void onResponse(Call<UserData> call, Response<UserData> response) {
                                Toast.makeText(ModifyPlantActivity.this, "삭제 완료", Toast.LENGTH_SHORT).show();
                                setResult(RESULT_OK);
                                finish();
                            }

                            @Override
                            public void onFailure(Call<UserData> call, Throwable t) {
                                t.printStackTrace();
                                Toast.makeText(ModifyPlantActivity.this, R.string.name_error, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_diary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_submit) {
            if (nameEdit.getText().toString().isEmpty())
                Toast.makeText(getApplicationContext(), "내용을 작성해주세요.", Toast.LENGTH_SHORT).show();
            else {
                //DB에 저장
                progressBar.setVisibility(View.VISIBLE);

                plantItem.setPlant_name(nameEdit.getText().toString());
                plantItem.setPlant_type(plantTypeEdit.getText().toString());

                //다이어리 insert 통신
                Call<UserData> addDiaryCall = service.updatePlant(plantItem);
                addDiaryCall.enqueue(new Callback<UserData>() {
                    @Override
                    public void onResponse(Call<UserData> call, Response<UserData> response) {
                        progressBar.setVisibility(View.INVISIBLE);
                        Intent result = new Intent();
                        result.putExtra("plantItem", plantItem);
                        finish();
                    }

                    @Override
                    public void onFailure(Call<UserData> call, Throwable t) {
                        progressBar.setVisibility(View.INVISIBLE);
                        t.printStackTrace();
                        Toast.makeText(ModifyPlantActivity.this, R.string.name_error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        } else if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return true;
    }
}
