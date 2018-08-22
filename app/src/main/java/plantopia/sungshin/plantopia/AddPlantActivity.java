package plantopia.sungshin.plantopia;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.joooonho.SelectableRoundedImageView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddPlantActivity extends AppCompatActivity {

    PlantItem newPlant = new PlantItem();
    @BindView(R.id.plant_img)
    SelectableRoundedImageView plantImg;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.plant_name_text)
    TextView plantNameText;
    @BindView(R.id.plant_edit_layout)
    TextInputLayout plantEditLayout;
    @BindView(R.id.plant_edit)
    TextInputEditText plantEdit;
    @BindView(R.id.search_plant_btn)
    ImageButton searchPlantBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);
        ButterKnife.bind(this);
        setTitle(getString(R.string.add_plant));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_diary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.menu_submit) {

        } else if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return true;
    }

    public void cameraBtnOnClicked(View view) {

    }

    public void changeNameBtnOnClicked(View view) {

    }

    public void searchBtnOnClicked(View view ){

    }
}
