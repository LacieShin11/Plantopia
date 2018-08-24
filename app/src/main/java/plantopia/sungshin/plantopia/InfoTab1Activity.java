package plantopia.sungshin.plantopia;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

import butterknife.BindView;
import butterknife.ButterKnife;
import plantopia.sungshin.plantopia.User.ApplicationController;
import plantopia.sungshin.plantopia.User.AutoLoginManager;
import plantopia.sungshin.plantopia.User.ServerURL;
import plantopia.sungshin.plantopia.User.ServiceApiForUser;
import plantopia.sungshin.plantopia.User.UserData;
import plantopia.sungshin.plantopia.customView.RecyclerItemClickListener;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoTab1Activity extends AppCompatActivity {
    static final int DEVICE_CONNECTION = 33;
    static final int NONE_CONNECTION = 34;
    ServiceApiForUser service;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.tab1_plants_list)
    RecyclerView plantListView;
    @BindView(R.id.tab1_plantopia_list)
    RecyclerView plantopiaListView;
    @BindView(R.id.none_plant_text)
    TextView nonePlantText;
    @BindView(R.id.plant_layout)
    LinearLayout plantLayout;
    UserData user;

    PlantRecyclerViewAdapter plantAdapter;
    PlantRecyclerViewAdapter plantopiaAdapter;

    ArrayList<PlantItem> plantItems = new ArrayList<>();
    ArrayList<PlantItem> plantopiaItems = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_tab1);
        ButterKnife.bind(this);
        getSupportActionBar().hide();

        user = AutoLoginManager.getInstance(getApplicationContext()).getUser();

        //서버 연결
        ApplicationController applicationController = ApplicationController.getInstance();
        applicationController.buildService(ServerURL.URL, 3000);
        service = ApplicationController.getInstance().getService();

        plantAdapter = new PlantRecyclerViewAdapter(plantItems, getApplicationContext());
        plantListView.setHasFixedSize(true);
        plantListView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        plantListView.setAdapter(plantAdapter);
        plantopiaListView.setNestedScrollingEnabled(false);
        plantAdapter.notifyDataSetChanged();

        plantopiaAdapter = new PlantRecyclerViewAdapter(plantopiaItems, getApplicationContext());
        plantopiaListView.setHasFixedSize(true);
        plantopiaListView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        plantopiaListView.setAdapter(plantopiaAdapter);
        plantopiaListView.setNestedScrollingEnabled(false);
        plantopiaAdapter.notifyDataSetChanged();

        //장치 연결된 식물
        plantopiaListView.addOnItemTouchListener(new RecyclerItemClickListener(InfoTab1Activity.this, plantListView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(InfoTab1Activity.this, PlantInfoActivity.class);
                        intent.putExtra("plantName", plantAdapter.getItem(position).getPlantName());
                        intent.putExtra("plantType", plantAdapter.getItem(position).getPlantType());
                        startActivityForResult(intent, DEVICE_CONNECTION);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));

        //장치 연결 X 식물
        plantListView.addOnItemTouchListener(new RecyclerItemClickListener(InfoTab1Activity.this, plantListView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(InfoTab1Activity.this, PlantInfoActivity.class);
                        intent.putExtra("plantName", plantAdapter.getItem(position).getPlantName());
                        intent.putExtra("plantType", plantAdapter.getItem(position).getPlantType());

                        startActivityForResult(intent, NONE_CONNECTION);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setEnabled(true);
                //갱신 코드
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setEnabled(false);
                    }
                }, 1000);
            }
        });

        //화분의 개수에 따라 화면 변경
        if (AutoLoginManager.getInstance(getApplicationContext()).isLoggedIn()) {
            if (user.getCount_pot() == 0) {
                nonePlantText.setVisibility(View.VISIBLE);
                plantLayout.setVisibility(View.INVISIBLE);
            } else {
                nonePlantText.setVisibility(View.VISIBLE);
                plantLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    public void getPlants() {
        Call<List<PlantItem>> plantCall = service.getPlant(user.getUser_id());
        plantCall.enqueue(new Callback<List<PlantItem>>() {
            @Override
            public void onResponse(Call<List<PlantItem>> call, Response<List<PlantItem>> response) {

            }

            @Override
            public void onFailure(Call<List<PlantItem>> call, Throwable t) {
                Toast.makeText(InfoTab1Activity.this, R.string.name_error, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
