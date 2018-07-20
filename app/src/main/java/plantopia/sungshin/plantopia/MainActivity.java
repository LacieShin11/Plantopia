package plantopia.sungshin.plantopia;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationViewEx bnve = (BottomNavigationViewEx) findViewById(R.id.bnve);

        //BottomNavigation 디자인 설정
        bnve.enableAnimation(true); //위로 떠오르는 애니메이션
        bnve.enableShiftingMode(false);
        bnve.enableItemShiftingMode(false);
        bnve.setTextVisibility(false);
        bnve.setPadding(0, 20, 0, 20);

        getSupportActionBar().setElevation(0); //액션바 그림자 제거

        loadFragment(new HomeFragment());

        //BottomNavigation 터치 이벤트
        bnve.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()) {
                    case R.id.menu_home:
                        fragment = new HomeFragment();
                        break;

                    case R.id.menu_search:
                        fragment = new SearchFragment();
                        break;

                    case R.id.menu_edit:
                        fragment = new EditFragment();
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

}
