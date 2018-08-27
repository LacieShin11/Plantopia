package plantopia.sungshin.plantopia;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import plantopia.sungshin.plantopia.Diray.ScrapItem;
import plantopia.sungshin.plantopia.Diray.ScrapRecyclerViewAdapter;
import plantopia.sungshin.plantopia.User.ApplicationController;
import plantopia.sungshin.plantopia.User.AutoLoginManager;
import plantopia.sungshin.plantopia.User.ServerURL;
import plantopia.sungshin.plantopia.User.ServiceApiForUser;
import plantopia.sungshin.plantopia.User.UserData;
import plantopia.sungshin.plantopia.customView.RecyclerItemClickListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoTab3Activity extends AppCompatActivity {
    final static int SHOW_DIARY = 8;
    ScrapRecyclerViewAdapter adapter;
    ServiceApiForUser service;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.scrap_list)
    RecyclerView scrapList;
    @BindView(R.id.none_scrap_text)
    TextView noneScrapText;
    List<ScrapItem> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_tab3);
        ButterKnife.bind(this);

        adapter = new ScrapRecyclerViewAdapter(arrayList, InfoTab3Activity.this);
        getSupportActionBar().hide();

        //서버 연결
        ApplicationController applicationController = ApplicationController.getInstance();
        applicationController.buildService(ServerURL.URL, 3000);
        service = ApplicationController.getInstance().getService();

        ScaleInAnimationAdapter alphaAdapter = new ScaleInAnimationAdapter(adapter);
        alphaAdapter.setDuration(200);

        //다이어리 아이템 채우기
        getScrapItems(AutoLoginManager.getInstance(getApplicationContext()).getUser());

        scrapList.setAdapter(new AlphaInAnimationAdapter(alphaAdapter));
        scrapList.setHasFixedSize(true);
        scrapList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        scrapList.addOnItemTouchListener(new RecyclerItemClickListener(InfoTab3Activity.this, scrapList, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(arrayList.get(position).getScrap_url()));
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setEnabled(true);
                swipeRefreshLayout.setRefreshing(true);
                getScrapItems(AutoLoginManager.getInstance(getApplicationContext()).getUser());

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getScrapItems(AutoLoginManager.getInstance(getApplicationContext()).getUser());
                    }
                }, 800);
            }
        });
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        getDiaryItems(AutoLoginManager.getInstance(getApplicationContext()).getUser());
    }*/

    private void getScrapItems(UserData userData) {

        Call<List<ScrapItem>> diaryCall = service.getScrap(userData);
        diaryCall.enqueue(new Callback<List<ScrapItem>>() {
            @Override
            public void onResponse(Call<List<ScrapItem>> call, Response<List<ScrapItem>> response) {
                if (response.isSuccessful()) {
                    arrayList = response.body();
                    setScrapItems();
                    setLayout();
                }
            }

            @Override
            public void onFailure(Call<List<ScrapItem>> call, Throwable t) {
                t.printStackTrace();
                Log.d("실패", t.getMessage());
                Toast.makeText(InfoTab3Activity.this, getString(R.string.name_error), Toast.LENGTH_SHORT).show();
                setLayout();
            }
        });
    }

    private void setScrapItems() {
        adapter.clear();

        for (int i = 0; i < arrayList.size(); i++) {
            adapter.addItem(arrayList.get(i));
        }

        adapter.notifyDataSetChanged();
        setLayout();
    }

    private void setLayout() {
        if (adapter.getItemCount() == 0) {
            noneScrapText.setVisibility(View.VISIBLE);
            scrapList.setVisibility(View.INVISIBLE);
        } else {
            noneScrapText.setVisibility(View.INVISIBLE);
            scrapList.setVisibility(View.VISIBLE);
        }
        swipeRefreshLayout.setRefreshing(false);
    }
}