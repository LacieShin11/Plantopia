package plantopia.sungshin.plantopia;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SearchFragment extends android.support.v4.app.Fragment {
    private Unbinder unbinder;

    @BindView(R.id.search_list)
    ListView searchListView;

    SearchListAdapter adapter;
    ArrayList<String> plantList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);
        unbinder = ButterKnife.bind(this, view);

        plantList.add("아레카야자");
        plantList.add("관음죽");
        plantList.add("대나무야자");
        plantList.add("아레카야자");
        plantList.add("관음죽");
        plantList.add("대나무야자");
        plantList.add("아레카야자");
        plantList.add("관음죽");
        plantList.add("대나무야자");
        plantList.add("아레카야자");
        plantList.add("관음죽");
        plantList.add("대나무야자");
        plantList.add("아레카야자");
        plantList.add("관음죽");
        plantList.add("대나무야자");
        plantList.add("아레카야자");
        plantList.add("관음죽");
        plantList.add("대나무야자");

        adapter = new SearchListAdapter(plantList);

        searchListView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
