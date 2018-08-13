package plantopia.sungshin.plantopia;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import butterknife.ButterKnife;

public class ProfileSettingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);
        ButterKnife.bind(this);
    }

    public void logoutBtnOnClicked(View view) {
        finish();
        AutoLoginManager.getInstance(getApplicationContext()).logout();
    }

    public void cameraBtnOnClicked(View view) {
    }
}
