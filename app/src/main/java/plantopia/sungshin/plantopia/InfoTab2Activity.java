package plantopia.sungshin.plantopia;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import plantopia.sungshin.plantopia.Diray.DiaryItem;
import plantopia.sungshin.plantopia.Diray.DiaryRecyclerViewAdapter;
import plantopia.sungshin.plantopia.Diray.ShowDiaryActivity;
import plantopia.sungshin.plantopia.User.ApplicationController;
import plantopia.sungshin.plantopia.User.AutoLoginManager;
import plantopia.sungshin.plantopia.User.ServerURL;
import plantopia.sungshin.plantopia.User.ServiceApiForUser;
import plantopia.sungshin.plantopia.User.UserData;
import plantopia.sungshin.plantopia.customView.RecyclerItemClickListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoTab2Activity extends AppCompatActivity {
    final static int SHOW_DIARY = 8;
    DiaryRecyclerViewAdapter adapter;
    ServiceApiForUser service;

    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.diary_list)
    RecyclerView diaryList;
    @BindView(R.id.none_diary_text)
    TextView noneDiaryText;
    List<DiaryItem> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_tab2);
        ButterKnife.bind(this);

        adapter = new DiaryRecyclerViewAdapter(arrayList, InfoTab2Activity.this);
        getSupportActionBar().hide();

        //서버 연결
        ApplicationController applicationController = ApplicationController.getInstance();
        applicationController.buildService(ServerURL.URL, 3000);
        service = ApplicationController.getInstance().getService();

        ScaleInAnimationAdapter alphaAdapter = new ScaleInAnimationAdapter(adapter);
        alphaAdapter.setDuration(200);

        //다이어리 아이템 채우기
        getDiaryItems(AutoLoginManager.getInstance(getApplicationContext()).getUser());

//        swipeRefreshLayout.setRefreshing(true);
//        swipeRefreshLayout.setVerticalScrollBarEnabled(true);
        diaryList.setAdapter(new AlphaInAnimationAdapter(alphaAdapter));
        diaryList.setHasFixedSize(true);
        diaryList.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        diaryList.addOnItemTouchListener(new RecyclerItemClickListener(InfoTab2Activity.this, diaryList, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(InfoTab2Activity.this, ShowDiaryActivity.class);
                intent.putExtra("content", arrayList.get(position).getDiary_content());
                intent.putExtra("imgPath", arrayList.get(position).getDiary_img());
                intent.putExtra("id", arrayList.get(position).getOwner_id());
                intent.putExtra("diaryId", arrayList.get(position).getDiary_id());
                startActivityForResult(intent, SHOW_DIARY);
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
                getDiaryItems(AutoLoginManager.getInstance(getApplicationContext()).getUser());

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getDiaryItems(AutoLoginManager.getInstance(getApplicationContext()).getUser());
                    }
                }, 800);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        getDiaryItems(AutoLoginManager.getInstance(getApplicationContext()).getUser());
    }

    private void getDiaryItems(UserData userData) {
        Call<List<DiaryItem>> diaryCall = service.getDiary(userData.getUser_id());
        diaryCall.enqueue(new Callback<List<DiaryItem>>() {
            @Override
            public void onResponse(Call<List<DiaryItem>> call, Response<List<DiaryItem>> response) {
                if (response.isSuccessful()) {
                    arrayList = response.body();
                    setDiaryItems();
                    setLayout();
                }
            }

            @Override
            public void onFailure(Call<List<DiaryItem>> call, Throwable t) {
                t.printStackTrace();
                Log.d("실패", t.getMessage());
                Toast.makeText(InfoTab2Activity.this, getString(R.string.name_error), Toast.LENGTH_SHORT).show();
                setLayout();
            }
        });
    }

    private void setDiaryItems() {
        adapter.clear();

        for (int i = 0; i < arrayList.size(); i++) {
            adapter.addItem(arrayList.get(i));
        }

        adapter.notifyDataSetChanged();
        setLayout();
    }

    private void setLayout() {
        if (adapter.getItemCount() == 0) {
            noneDiaryText.setVisibility(View.VISIBLE);
            diaryList.setVisibility(View.INVISIBLE);
        } else {
            noneDiaryText.setVisibility(View.INVISIBLE);
            diaryList.setVisibility(View.VISIBLE);
        }

        swipeRefreshLayout.setRefreshing(false);
    }
}