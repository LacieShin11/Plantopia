package plantopia.sungshin.plantopia;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class InfoTab1Activity extends AppCompatActivity {
    @BindView(R.id.tab1_plants_list)
    RecyclerView plantListView;
    @BindView(R.id.tab1_plantopia_list)
    RecyclerView plantopiaListView;

    PlantRecyclerViewAdapter plantListAdapter;
    ArrayList<PlantItem> plantItems = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_tab1);

        plantItems.add(new PlantItem("코코", "여명옥", 1));
        plantItems.add(new PlantItem("아이비", "스투키", 2));
        plantItems.add(new PlantItem("산세", "칼라데아", 3));
        plantItems.add(new PlantItem("베리", "죽백", 4));

        getSupportActionBar().hide();
        ButterKnife.bind(this);

        plantListAdapter = new PlantRecyclerViewAdapter(plantItems, getApplicationContext());
        plantListView.setHasFixedSize(true);
        plantListView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));

        plantListView.setAdapter(plantListAdapter);
        plantListAdapter.notifyDataSetChanged();
    }
}
