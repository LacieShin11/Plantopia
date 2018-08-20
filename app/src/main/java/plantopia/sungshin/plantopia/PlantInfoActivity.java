package plantopia.sungshin.plantopia;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.ButterKnife;
import plantopia.sungshin.plantopia.ChatBot.Calathea;
import plantopia.sungshin.plantopia.ChatBot.Nagi;
import plantopia.sungshin.plantopia.ChatBot.Oroya;
import plantopia.sungshin.plantopia.ChatBot.Palm;
import plantopia.sungshin.plantopia.ChatBot.Stuckyi;

public class PlantInfoActivity extends AppCompatActivity {
    String plantName, plantType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_info);
        ButterKnife.bind(this);
        
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

        switch (item.getItemId()) {
            case R.id.menu_chat: //챗봇 누르기
                if(plantType.equals("스투키")) {
                    intent2.putExtra("plantName", plantName);
                    startActivity(new Intent(PlantInfoActivity.this, Stuckyi.class));
                }else if(plantType.equals("칼라데아")){
                    intent2.putExtra("plantName", plantName);
                    startActivity(new Intent(PlantInfoActivity.this, Calathea.class));
                }else if(plantType.equals("여염옥")){
                    intent2.putExtra("plantName", plantName);
                    startActivity(new Intent(PlantInfoActivity.this, Oroya.class));
                }else if(plantType.equals("테이블야자")){
                    intent2.putExtra("plantName", plantName);
                    startActivity(new Intent(PlantInfoActivity.this, Palm.class));
                }else if(plantType.equals("죽백")){
                    intent2.putExtra("plantName", plantName);
                    startActivity(new Intent(PlantInfoActivity.this, Nagi.class));
                }
                break;
            case R.id.menu_setting:
                startActivity(new Intent(PlantInfoActivity.this, ModifyPlantActivity.class));
                break;
            case R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
