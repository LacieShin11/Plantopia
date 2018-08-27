package plantopia.sungshin.plantopia;

import android.Manifest;
import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.Calendar;

import plantopia.sungshin.plantopia.Diray.WriteNewDiaryActivity;
import plantopia.sungshin.plantopia.FCM.MyFirebaseInstanceIDService;
import plantopia.sungshin.plantopia.Music.MusicFragment;
import plantopia.sungshin.plantopia.Search.SearchFragment;
import plantopia.sungshin.plantopia.User.AutoLoginManager;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_EXTERNAL_STORAGE = 0;
    static final int WRITE_DIARY = 2;
    static final int LOGIN = 3;
    private static final String TAG = MainActivity.class.getSimpleName();
    public static String myToken; //내 기기의 토큰값
    public static String UserToken; //저장할 토큰값

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //푸시 알림-firebase
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        myToken= FirebaseInstanceId.getInstance().getToken(); //토큰 받아오기

        if (myToken != null) {
            Log.d(TAG, "token = " + FirebaseInstanceId.getInstance().getToken());
            UserToken = myToken;
        } //토큰 받아오기-토큰 없으면 로그 띄우기
        else{
            Log.d(TAG, "refreshed = " + FirebaseInstanceId.getInstance().getToken());
            UserToken = MyFirebaseInstanceIDService.refreshedToken;
        }//myToken == null
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.actionbar_custom);


        //**푸시 알림
      /*  final NotificationManager notifiationManager = (NotificationManager)MainActivity.this.getSystemService(MainActivity.this.NOTIFICATION_SERVICE);
        final Intent intent = new Intent(MainActivity.this.getApplicationContext(), PlantInfoActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(this);

        builder.setTicker("설명");
        builder.setContentTitle("제목");
        builder.setContentText("내용");
        builder.setWhen(System.currentTimeMillis());
        builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);

        Notification.InboxStyle inboxStyle = new Notification.InboxStyle(builder);//이것 때문에 sdk 최소버전 16으로 변경함
        inboxStyle.addLine("나 물 좀 줘~!");
        builder.setStyle(inboxStyle);

        notifiationManager.notify(0, builder.build());
*/

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
                            Toast.makeText(getApplicationContext(), "로그인이 필요한 기능입니다.", Toast.LENGTH_SHORT).show();
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

    //푸시 알림 연습
    public class AlarmHATT {
        private Context context;

        public AlarmHATT(Context context) {
            this.context = context;
        }

        public void Alarm() {
            AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            //Intent intent = new Intent(MainActivity.this, BroadCast.class);

            //PendingIntent sender = PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);

            Calendar calendar = Calendar.getInstance();
            //알람시간 calendar에 set해주기

/*            calendar.set(Calendar.YEAR, 2018);
            calendar.set(Calendar.MONTH, 8);
            calendar.set(Calendar.DATE, 22);*/

            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE), 16, 38, 0);

            //알람 예약
            //am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
        }
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
