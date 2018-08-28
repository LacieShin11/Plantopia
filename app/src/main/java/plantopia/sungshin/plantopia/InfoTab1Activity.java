package plantopia.sungshin.plantopia;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import plantopia.sungshin.plantopia.Plant.PlantInfoActivity;
import plantopia.sungshin.plantopia.Plant.PlantItem;
import plantopia.sungshin.plantopia.Plant.PlantRecyclerViewAdapter;
import plantopia.sungshin.plantopia.User.ApplicationController;
import plantopia.sungshin.plantopia.User.AutoLoginManager;
import plantopia.sungshin.plantopia.User.ServerURL;
import plantopia.sungshin.plantopia.User.ServiceApiForUser;
import plantopia.sungshin.plantopia.User.UserData;
import plantopia.sungshin.plantopia.customView.RecyclerItemClickListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoTab1Activity extends AppCompatActivity {
    static final int DEVICE_CONNECTION = 33;
    static final int NONE_CONNECTION = 34;
    static final int DELETE_PLANT = 34;

    UserData user;
    ServiceApiForUser service;
    @BindView(R.id.swipe_container)
    PullToRefreshScrollView swipeRefreshLayout;
    @BindView(R.id.tab1_plants_list)
    RecyclerView plantListView;
    @BindView(R.id.tab1_plantopia_list)
    RecyclerView plantopiaListView;
    @BindView(R.id.none_plant_text)
    TextView nonePlantText;
    @BindView(R.id.plant_layout)
    LinearLayout plantLayout;
    @BindView(R.id.tab1_text1)
    TextView text1;
    @BindView(R.id.tab1_text2)
    TextView text2;

    PlantRecyclerViewAdapter plantAdapter;
    PlantRecyclerViewAdapter plantopiaAdapter;

    List<PlantItem> items = new ArrayList<>();
    ArrayList<PlantItem> plantItems = new ArrayList<>();
    ArrayList<PlantItem> plantopiaItems = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_tab1);
        ButterKnife.bind(this);

        ApplicationController applicationController = ApplicationController.getInstance();
        applicationController.buildService(ServerURL.URL, 3000);
        service = ApplicationController.getInstance().getService();

        user = AutoLoginManager.getInstance(getApplicationContext()).getUser();

        getSupportActionBar().hide();

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
        plantopiaListView.addOnItemTouchListener(new RecyclerItemClickListener(InfoTab1Activity.this, plantopiaListView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent intent = new Intent(InfoTab1Activity.this, PlantInfoActivity.class);
                        intent.putExtra("plantItem", plantopiaAdapter.getItem(position));
                        intent.putExtra("isConnected", plantopiaAdapter.getItem(position).isPlant_connect());
                        intent.putExtra("plantName", plantopiaAdapter.getItem(position).getPlant_name());
                        intent.putExtra("plantType", plantopiaAdapter.getItem(position).getPlant_type());
                        intent.putExtra("plantImg", plantopiaAdapter.getItem(position).getPlant_img());
                        //아두이노에서 온도, 빛, 습도 정보 가져오기
                        intent.putExtra("plantTemp", plantopiaAdapter.getItem(position).getTemp());
                        intent.putExtra("plantLight", plantopiaAdapter.getItem(position).getLight());
                        intent.putExtra("plantHumidity", plantopiaAdapter.getItem(position).getHumidity());

                        intent.putExtra("plantMaxTemp", plantopiaAdapter.getItem(position).getTemp_max());
                        intent.putExtra("plantMinTemp", plantopiaAdapter.getItem(position).getTemp_min());
                        intent.putExtra("plantMaxLight", plantopiaAdapter.getItem(position).getLight_max());
                        intent.putExtra("plantMinLight", plantopiaAdapter.getItem(position).getLight_min());
                        intent.putExtra("plantMaxHumidity", plantopiaAdapter.getItem(position).getHumidity_max());
                        intent.putExtra("plantMinHumidity", plantopiaAdapter.getItem(position).getHumidity_min());
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
                        intent.putExtra("isConnected", plantAdapter.getItem(position).isPlant_connect());
                        intent.putExtra("plantName", plantAdapter.getItem(position).getPlant_name());
                        intent.putExtra("plantType", plantAdapter.getItem(position).getPlant_type());
                        intent.putExtra("plantImg", plantAdapter.getItem(position).getPlant_img());
                        intent.putExtra("plantId", plantAdapter.getItem(position).getPlant_id());
                        intent.putExtra("plantItem", plantAdapter.getItem(position));
                        startActivityForResult(intent, NONE_CONNECTION);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }
                }));

        getPlantItems();

        swipeRefreshLayout.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                getPlantItems();
            }
        });
    }

    private void getPlantItems() {

        Call<List<PlantItem>> plantCall = service.getPlant(user.getUser_id());
        plantCall.enqueue(new Callback<List<PlantItem>>() {
            @Override
            public void onResponse(Call<List<PlantItem>> call, Response<List<PlantItem>> response) {
                items = response.body();
                swipeRefreshLayout.onRefreshComplete();
                setLayout();
            }

            @Override
            public void onFailure(Call<List<PlantItem>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(InfoTab1Activity.this, R.string.name_error, Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setEnabled(false);
                swipeRefreshLayout.onRefreshComplete();
            }
        });
    }

    //화분의 개수에 따라 화면 변경
    private void setLayout() {
        plantopiaAdapter.clear();
        plantAdapter.clear();

        if (AutoLoginManager.getInstance(getApplicationContext()).isLoggedIn()) {
            for (int i = 0; i < items.size(); i++) {
                Log.i("확인", items.get(i).getPlant_name());
                Log.i("확인", items.get(i).getLight() + " " + items.get(i).getHumidity());
                if (items.get(i).isPlant_connect() == 1) {
                    plantopiaAdapter.addItem(items.get(i));
                } else {
                    plantAdapter.addItem(items.get(i));
                }
            }

            plantAdapter.notifyDataSetChanged();
            plantopiaAdapter.notifyDataSetChanged();

            swipeRefreshLayout.setEnabled(false);

            if (plantopiaAdapter.getItemCount() == 0 && plantAdapter.getItemCount() == 0) {
                nonePlantText.setVisibility(View.VISIBLE);
                plantListView.setVisibility(View.INVISIBLE);
                text1.setVisibility(View.INVISIBLE);
                text2.setVisibility(View.INVISIBLE);
            } else {
                nonePlantText.setVisibility(View.INVISIBLE);
                plantLayout.setVisibility(View.VISIBLE);
                text1.setVisibility(View.VISIBLE);
                text2.setVisibility(View.VISIBLE);
            }
        }
    }
}