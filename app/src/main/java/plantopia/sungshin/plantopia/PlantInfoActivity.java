package plantopia.sungshin.plantopia;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.ButterKnife;
import plantopia.sungshin.plantopia.ChatBot.Calathea;
import plantopia.sungshin.plantopia.ChatBot.Nagi;
import plantopia.sungshin.plantopia.ChatBot.Oroya;
import plantopia.sungshin.plantopia.ChatBot.Palm;
import plantopia.sungshin.plantopia.ChatBot.Stuckyi;

public class PlantInfoActivity extends AppCompatActivity {
    String plantName, plantType;
    Double Temp, Light, Humidity;
    Double latestTemp, latestLight, latestHumidity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_info);
        ButterKnife.bind(this);

        //여기서 아두이노 정보 받은 후에, 1시간 간격으로 푸시 알람(값 전후로 비교해서)
        NotificationSomethings();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        setTitle(intent.getStringExtra("plantName"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_plant, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent2 = getIntent();
        plantType = intent2.getStringExtra("plantType");
        plantName = intent2.getStringExtra("plantName");
        Temp = intent2.getDoubleExtra("Temp", 30);
        Light = intent2.getDoubleExtra("Light", 3);
        Humidity = intent2.getDoubleExtra("Humidity", 300);

        //여기까진 정보 잘 받아옴..테스팅
        Toast.makeText(getApplicationContext(), Temp.toString(), Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), Light.toString(), Toast.LENGTH_SHORT).show();
        Toast.makeText(getApplicationContext(), Humidity.toString(), Toast.LENGTH_SHORT).show();

        switch (item.getItemId()) {
            case R.id.menu_chat: //챗봇 누르기
                if(plantType.equals("스투키")) {
                    Intent intent = new Intent(PlantInfoActivity.this, Stuckyi.class);
                    intent.putExtra("plantName", plantName);
                    intent.putExtra("Temp", Temp);
                    intent.putExtra("Light", Light);
                    intent.putExtra("Humidity", Humidity);
                    startActivityForResult(intent, RESULT_OK);
                }else if(plantType.equals("칼라데아")){
                    Intent intent = new Intent(PlantInfoActivity.this, Calathea.class);
                    intent.putExtra("plantName", plantName);
                    intent.putExtra("Temp", Temp);
                    intent.putExtra("Light", Light);
                    intent.putExtra("Humidity", Humidity);
                    startActivityForResult(intent, RESULT_OK);
                }else if(plantType.equals("여염옥")){
                    Intent intent = new Intent(PlantInfoActivity.this, Oroya.class);
                    intent.putExtra("plantName", plantName);
                    intent.putExtra("Temp", Temp);
                    intent.putExtra("Light", Light);
                    intent.putExtra("Humidity", Humidity);
                    startActivityForResult(intent, RESULT_OK);
                }else if(plantType.equals("테이블야자")){
                    Intent intent = new Intent(PlantInfoActivity.this, Palm.class);
                    intent.putExtra("plantName", plantName);
                    intent.putExtra("Temp", Temp);
                    intent.putExtra("Light", Light);
                    intent.putExtra("Humidity", Humidity);
                    startActivityForResult(intent, RESULT_OK);
                }else if(plantType.equals("죽백")){
                    Intent intent = new Intent(PlantInfoActivity.this, Nagi.class);
                    intent.putExtra("plantName", plantName);
                    intent.putExtra("Temp", Temp);
                    intent.putExtra("Light", Light);
                    intent.putExtra("Humidity", Humidity);
                    startActivityForResult(intent, RESULT_OK);
                }
                break;
            case R.id.menu_setting:
                startActivity(new Intent(PlantInfoActivity.this, ModifyPlantActivity.class));
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //푸시 알람 함수
    public void NotificationSomethings() {
        //식물 정보 받아오기
        Intent intent3 = getIntent();
        String plantType = intent3.getStringExtra("plantType");
        String plantName = intent3.getStringExtra("plantName");

        Resources res = getResources();

        Intent notificationIntent = new Intent(this, Stuckyi.class);

        notificationIntent.putExtra("plantNickName", plantName); //전달할 값
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

        builder.setContentTitle(plantName+"이(가) 당신에게 할 말이 있는 것 같군요")
                .setContentText("배고파아아")
                .setTicker("스투키로부터")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setWhen(System.currentTimeMillis())
                .setDefaults(android.app.Notification.DEFAULT_ALL);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            builder.setCategory(android.app.Notification.CATEGORY_MESSAGE)
                    .setPriority(android.app.Notification.PRIORITY_HIGH)
                    .setVisibility(Notification.VISIBILITY_PUBLIC);
        }

        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(1234, builder.build());
    }
}
