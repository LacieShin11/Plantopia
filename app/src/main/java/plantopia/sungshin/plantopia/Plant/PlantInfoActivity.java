package plantopia.sungshin.plantopia.Plant;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import plantopia.sungshin.plantopia.ChatBot.Calathea;
import plantopia.sungshin.plantopia.ChatBot.Nagi;
import plantopia.sungshin.plantopia.ChatBot.Oroya;
import plantopia.sungshin.plantopia.ChatBot.Palm;
import plantopia.sungshin.plantopia.ChatBot.Stuckyi;
import plantopia.sungshin.plantopia.R;

public class PlantInfoActivity extends AppCompatActivity {
    String plantName, plantType, plantimg;
    Double Temp, Light, Humidity, MaxTemp, MinTemp, MaxLight, MinLight, MaxHumidity, MinHumidity;
    Double latestTemp, latestLight, latestHumidity;
    int isConnected; //식물 연결 여부
    static int count, status_count = 0;
    static final int DELETE_PLANT = 35;
    static final int MODIFY_PLANT = 36;

    @BindView(R.id.plant_img)
    ImageView plantImg;
    @BindView(R.id.plant_state_text)
    TextView plantStateText;

    @BindView(R.id.bright_img)
    ImageView brightImg;
    @BindView(R.id.bright_status_text)
    TextView brightStatusText;
    @BindView(R.id.bright_text)
    TextView brightText;

    @BindView(R.id.water_img)
    ImageView waterImg;
    @BindView(R.id.water_text)
    TextView waterText;
    @BindView(R.id.water_status_text)
    TextView waterStatusText;

    @BindView(R.id.temp_status_text)
    TextView tempStatusText;
    @BindView(R.id.temp_img)
    ImageView tempImg;
    @BindView(R.id.temp_text)
    TextView tempText;

    @BindView(R.id.feel_img)
    ImageView feelImg;
    @BindView(R.id.feel_status_text)
    TextView feelStatusText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_info);
        ButterKnife.bind(this);

        //ImageView imageView = new ImageView(this);
        //여기서 아두이노 정보 받은 후에, 1시간 간격으로 푸시 알람(값 전후로 비교해서)
        //NotificationSomethings();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        PlantItem plantItem = (PlantItem) intent.getSerializableExtra("plantItem");

        setStatus(plantItem);
        setTitle(plantItem.getPlant_name());

        //전 값과 비교하기
        // if(count > 0) {
           /* latestTemp = Temp;
            latestLight = Light;
            latestHumidity = Humidity;*/
        setStatus(plantItem);
    }

    public void setStatus(PlantItem plantItem) {

        Glide.with(this).load(plantItem.getPlant_img()).into(plantImg);

        Intent intent = getIntent();
        isConnected = intent.getIntExtra("isConnected", 0);//식물 연결 여부

        //아두이노에서 정보 가져오기
        Temp = intent.getDoubleExtra("plantTemp", 0);
        Light = intent.getDoubleExtra("plantLight", 0);
        Humidity = intent.getDoubleExtra("plantHumidity", 0);
        MaxTemp = intent.getDoubleExtra("plantMaxTemp", 0);
        MinTemp = intent.getDoubleExtra("plantMinTemp", 0);
        MaxLight = intent.getDoubleExtra("plantMaxLight", 0);
        MinLight = intent.getDoubleExtra("plantMinLight", 0);
        MaxHumidity = intent.getDoubleExtra("plantMaxHumidity", 0);
        MinHumidity = intent.getDoubleExtra("plantMinHumidity", 0);

        if (isConnected == 0) { //연결이 안 되었을 시에
            plantStateText.setText(R.string.plant_state3);
            tempText.setVisibility(View.GONE);
            waterText.setVisibility(View.GONE);
            brightText.setVisibility(View.GONE);

            tempStatusText.setText(R.string.state_none);
            waterStatusText.setText(R.string.state_none);
            brightStatusText.setText(R.string.state_none);
            feelStatusText.setText(R.string.state_none);
        } else {
            brightText.setText(Light + "");
            waterText.setText(Humidity + "");
            tempText.setText(Temp + "°C");

            if (Temp > MaxTemp) {
                status_count++;
                tempStatusText.setText(R.string.state_high);
                tempImg.setImageResource(R.drawable.monitor_notemp);
            } else if (Temp < MinTemp) {
                status_count++;
                tempStatusText.setText(R.string.state_low);
                tempImg.setImageResource(R.drawable.monitor_notemp);
            }//온도 정보 비교
            else {
                tempStatusText.setText(R.string.state_good);
            }

            if (Light > MaxLight) {
                status_count++;
                brightStatusText.setText(R.string.state_high);
                brightImg.setImageResource(R.drawable.monitor_nosunlight);
            } else if (Light < MinLight) {
                status_count++;
                brightStatusText.setText(R.string.state_low);
                brightImg.setImageResource(R.drawable.monitor_nosunlight);
            }//빛 정보 비교
            else {
                brightStatusText.setText(R.string.state_good);
            }

            if (Humidity > MaxHumidity) {
                status_count++;
                waterStatusText.setText(R.string.state_high);
                waterImg.setImageResource(R.drawable.monitor_nowater);
            } else if (Humidity < MinHumidity) {
                status_count++;
                waterStatusText.setText(R.string.state_low);
                waterImg.setImageResource(R.drawable.monitor_nowater);
            }//습도 정보 비교
            else {
                waterStatusText.setText(R.string.state_good);
            }

            if (status_count == 0) {
                plantStateText.setText(R.string.plant_state2);
            } else {
                plantStateText.setText(R.string.plant_state1); //기분 안 좋음
                feelImg.setImageResource(R.drawable.monitor_nolove);
                feelStatusText.setText(R.string.state_bad);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_plant, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent2 = getIntent();
        isConnected = intent2.getIntExtra("isConnected", 0); //식물 연결 여부
        plantType = intent2.getStringExtra("plantType");//식물 타입
        plantName = intent2.getStringExtra("plantName");//식물 애칭
        plantimg = intent2.getStringExtra("plantImg");
        Temp = intent2.getDoubleExtra("plantTemp", 0);
        Light = intent2.getDoubleExtra("plantLight", 0);
        Humidity = intent2.getDoubleExtra("plantHumidity", 0);

        MaxTemp = intent2.getDoubleExtra("plantMaxTemp", 0);
        MinTemp = intent2.getDoubleExtra("plantMinTemp", 0);
        MaxLight = intent2.getDoubleExtra("plantMaxLight", 0);
        MinLight = intent2.getDoubleExtra("plantMinLight", 0);
        MaxHumidity = intent2.getDoubleExtra("plantMaxHumidity", 0);
        MinHumidity = intent2.getDoubleExtra("plantMinHumidity", 0);

        switch (item.getItemId()) {
            case R.id.menu_chat: //챗봇 누르기
                Log.d("plantType", plantType);
                if (plantType.equals("스투키")) {
                    Intent intent = new Intent(PlantInfoActivity.this, Stuckyi.class);
                    intent.putExtra("isConnected", isConnected);
                    intent.putExtra("plantName", plantName);
                    intent.putExtra("Temp", Temp);
                    intent.putExtra("Light", Light);
                    intent.putExtra("Humidity", Humidity);
                    intent.putExtra("MaxTemp", MaxTemp);
                    intent.putExtra("MinTemp", MinTemp);
                    intent.putExtra("MaxLight", MaxLight);
                    intent.putExtra("MinLight", MinLight);
                    intent.putExtra("MaxHumidity", MaxHumidity);
                    intent.putExtra("MinHumidity", MinHumidity);
                    startActivityForResult(intent, RESULT_OK);
                } else if (plantType.equals("칼라데아")) {
                    Intent intent = new Intent(PlantInfoActivity.this, Calathea.class);
                    intent.putExtra("isConnected", isConnected);
                    intent.putExtra("plantName", plantName);
                    intent.putExtra("Temp", Temp);
                    intent.putExtra("Light", Light);
                    intent.putExtra("Humidity", Humidity);
                    intent.putExtra("MaxTemp", MaxTemp);
                    intent.putExtra("MinTemp", MinTemp);
                    intent.putExtra("MaxLight", MaxLight);
                    intent.putExtra("MinLight", MinLight);
                    intent.putExtra("MaxHumidity", MaxHumidity);
                    intent.putExtra("MinHumidity", MinHumidity);
                    startActivityForResult(intent, RESULT_OK);
                } else if (plantType.equals("여염옥")) {
                    Intent intent = new Intent(PlantInfoActivity.this, Oroya.class);
                    intent.putExtra("isConnected", isConnected);
                    intent.putExtra("plantName", plantName);
                    intent.putExtra("Temp", Temp);
                    intent.putExtra("Light", Light);
                    intent.putExtra("Humidity", Humidity);
                    intent.putExtra("MaxTemp", MaxTemp);
                    intent.putExtra("MinTemp", MinTemp);
                    intent.putExtra("MaxLight", MaxLight);
                    intent.putExtra("MinLight", MinLight);
                    intent.putExtra("MaxHumidity", MaxHumidity);
                    intent.putExtra("MinHumidity", MinHumidity);
                    startActivityForResult(intent, RESULT_OK);
                } else if (plantType.equals("테이블야자")) {
                    Intent intent = new Intent(PlantInfoActivity.this, Palm.class);
                    intent.putExtra("isConnected", isConnected);
                    intent.putExtra("plantName", plantName);
                    intent.putExtra("Temp", Temp);
                    intent.putExtra("Light", Light);
                    intent.putExtra("Humidity", Humidity);
                    intent.putExtra("MaxTemp", MaxTemp);
                    intent.putExtra("MinTemp", MinTemp);
                    intent.putExtra("MaxLight", MaxLight);
                    intent.putExtra("MinLight", MinLight);
                    intent.putExtra("MaxHumidity", MaxHumidity);
                    intent.putExtra("MinHumidity", MinHumidity);
                    startActivityForResult(intent, RESULT_OK);
                } else if (plantType.equals("죽백나무")) {
                    Intent intent = new Intent(PlantInfoActivity.this, Nagi.class);
                    intent.putExtra("isConnected", isConnected);
                    intent.putExtra("plantName", plantName);
                    intent.putExtra("Temp", Temp);
                    intent.putExtra("Light", Light);
                    intent.putExtra("Humidity", Humidity);
                    intent.putExtra("MaxTemp", MaxTemp);
                    intent.putExtra("MinTemp", MinTemp);
                    intent.putExtra("MaxLight", MaxLight);
                    intent.putExtra("MinLight", MinLight);
                    intent.putExtra("MaxHumidity", MaxHumidity);
                    intent.putExtra("MinHumidity", MinHumidity);
                    startActivityForResult(intent, RESULT_OK);
                } else {
                    Toast.makeText(getApplicationContext(), "아직 인간의 언어를 배우고 있는 중입니다. 조금만 더 기다려주세요", Toast.LENGTH_LONG).show();
                } //다섯 종류의 식물 이외는 아직 챗봇 준비 중
                break;
            case R.id.menu_setting:
                Intent intent = new Intent(PlantInfoActivity.this, ModifyPlantActivity.class);
                intent.putExtra("plantItem", getIntent().getSerializableExtra("plantItem"));
                startActivityForResult(intent, DELETE_PLANT);
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DELETE_PLANT && resultCode == RESULT_OK) {
            setResult(RESULT_OK);
            finish();
        } else if (requestCode == MODIFY_PLANT && resultCode == RESULT_OK) {
            setStatus((PlantItem)data.getSerializableExtra("plantItem")); //화면 갱신
        }
    }

    /* //푸시 알람 함수
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
    }*/
}