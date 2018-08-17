package plantopia.sungshin.plantopia;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InfoTab1Activity extends AppCompatActivity {
    static final int DEVICE_CONNECTION = 33;
    static final int NONE_CONNECTION = 34;

    @BindView(R.id.tab1_plants_list)
    RecyclerView plantListView;
    @BindView(R.id.tab1_plantopia_list)
    RecyclerView plantopiaListView;

    PlantRecyclerViewAdapter plantAdapter;
    PlantRecyclerViewAdapter plantopiaAdapter;

    ArrayList<PlantItem> plantItems = new ArrayList<>();
    ArrayList<PlantItem> plantopiaItems = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_tab1);

        plantItems.add(new PlantItem("코코", "여명옥", 1));
        plantItems.add(new PlantItem("아이비", "스투키", 2));
        plantItems.add(new PlantItem("산세", "칼라데아", 3));
        plantItems.add(new PlantItem("베리", "죽백", 4));

        plantopiaItems.add(new PlantItem("코코", "여명옥", 1));
        plantopiaItems.add(new PlantItem("아이비", "스투키", 2));
        plantopiaItems.add(new PlantItem("산세", "칼라데아", 3));
        plantopiaItems.add(new PlantItem("베리", "죽백", 4));

        getSupportActionBar().hide();
        ButterKnife.bind(this);

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
    }
}
