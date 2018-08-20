package plantopia.sungshin.plantopia;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import plantopia.sungshin.plantopia.Music.MusicFragment;
import plantopia.sungshin.plantopia.User.AutoLoginManager;
import plantopia.sungshin.plantopia.User.SignInActivity;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_EXTERNAL_STORAGE = 0;
    static final int WRITE_DIARY = 2;
    static final int LOGIN = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_custom);

        BottomNavigationViewEx bnve = (BottomNavigationViewEx) findViewById(R.id.bnve);

        //BottomNavigation 디자인 설정
        bnve.enableAnimation(true); //위로 떠오르는 애니메이션
        bnve.enableShiftingMode(false);
        bnve.enableItemShiftingMode(false);
        bnve.setTextVisibility(false);
        bnve.setPadding(0, 25, 0, 25);
        bnve.setForegroundGravity(Gravity.CENTER);

        getSupportActionBar().setElevation(0); //액션바 그림자 제거

        loadFragment(new plantopia.sungshin.plantopia.Home.HomeFragment());

        //BottomNavigation 터치 이벤트
        bnve.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()) {
                    case R.id.menu_home:
                        fragment = new plantopia.sungshin.plantopia.Home.HomeFragment();
                        break;

                    case R.id.menu_search:
                        fragment = new SearchFragment();
                        break;

                    case R.id.menu_edit:
                        if (AutoLoginManager.getInstance(getApplicationContext()).isLoggedIn()) {
                            Intent intent = new Intent(MainActivity.this, WriteNewDiaryActivity.class);
                            startActivityForResult(intent, WRITE_DIARY);
                        } else {
                            Toast.makeText(getApplicationContext(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                            startActivityForResult(intent, LOGIN);
                        }
                        break;

                    case R.id.menu_music:
                        fragment = new MusicFragment();
                        break;

                    case R.id.menu_info:
                        fragment = new InfoFragment();
                        break;
                }

                return loadFragment(fragment);
            }
        });

        askForPermission();
    }

    //FrameLayout xml 파일 교체
    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }

        return false;
    }

    //권한 요청
    private void askForPermission() {
        String[] permissions = new String[]{
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA
        };
        ActivityCompat.requestPermissions(this, permissions, REQUEST_EXTERNAL_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_EXTERNAL_STORAGE) {
            for (int i = 0; i < permissions.length; i++) {
                String permission = permissions[i];
                int grantResult = grantResults[i];

                if (permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "앱을 이용하기 위해 접근 권한 허가가 필요합니다.", Toast.LENGTH_LONG).show();
                        this.finish();
                    }
                }

                else if (permission.equals(Manifest.permission.CAMERA)) {
                    if (grantResult != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "앱을 이용하기 위해 접근 권한 허가가 필요합니다.", Toast.LENGTH_LONG).show();
                        this.finish();
                    }
                }
            }
        }
    }
}
