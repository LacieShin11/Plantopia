package plantopia.sungshin.plantopia.Diray;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import plantopia.sungshin.plantopia.R;

public class EditDiaryActivity extends AppCompatActivity {
    @BindView(R.id.diary_img)
    ImageView diaryImg;
    @BindView(R.id.diary_edit)
    EditText diaryText;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_diary);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressBar.setVisibility(View.INVISIBLE);

        Intent intent = getIntent();
        Glide.with(this).load(intent.getStringExtra("imgPath")).into(diaryImg);
        diaryText.setText(intent.getStringExtra("content"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_diary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.menu_submit:
                break;

            case R.id.menu_delete:
                AlertDialog.Builder builder = new AlertDialog.Builder(EditDiaryActivity.this);
                builder.setTitle("수정 취소")
                        .setMessage("수정을 취소하시겠습니까? 편집한 내용이 삭제됩니다.")
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                setResult(RESULT_CANCELED);
                                finish();
                            }
                        }).show();

                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditDiaryActivity.this);
        builder.setTitle("수정 취소")
                .setMessage("수정을 취소하시겠습니까? 편집한 내용이 삭제됩니다.")
                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setResult(RESULT_CANCELED);
                        finish();
                    }
                }).show();
    }
}
