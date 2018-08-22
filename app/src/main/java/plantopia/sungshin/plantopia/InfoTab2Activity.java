package plantopia.sungshin.plantopia;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import plantopia.sungshin.plantopia.Diray.DiaryItem;
import plantopia.sungshin.plantopia.Diray.DiaryRecyclerViewAdapter;

public class InfoTab2Activity extends AppCompatActivity {
    DiaryRecyclerViewAdapter adapter;

    @BindView(R.id.diary_list)
    RecyclerView diaryList;
    @BindView(R.id.none_diary_text)
    TextView noneDiaryText;
    ArrayList<DiaryItem> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_tab2);
        ButterKnife.bind(this);

        adapter = new DiaryRecyclerViewAdapter(arrayList, InfoTab2Activity.this);
        diaryList.setAdapter(adapter);
        diaryList.setHasFixedSize(true);

        adapter.addItem(new DiaryItem(10, "https://s3.ap-northeast-2.amazonaws.com/plantopiabucket/plant_cropped.png",
                "할말이 없다...",
                "2018년 8월 23일"));
        adapter.notifyDataSetChanged();

        if (adapter.getItemCount() < 1) {
            noneDiaryText.setVisibility(View.VISIBLE);
            diaryList.setVisibility(View.INVISIBLE);
        } else {
            noneDiaryText.setVisibility(View.INVISIBLE);
            diaryList.setVisibility(View.VISIBLE);
        }

        getSupportActionBar().hide();
    }
}
