package plantopia.sungshin.plantopia.Diray;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import plantopia.sungshin.plantopia.R;
import plantopia.sungshin.plantopia.customView.MyMenuItemClickListener;

public class DiaryRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerDiaryViewHolder> {
    private List<DiaryItem> arrayList;
    private Context mContext;

    public DiaryRecyclerViewAdapter() {
    }

    public DiaryRecyclerViewAdapter(List<DiaryItem> arrayList, Context mContext) {
        this.arrayList = arrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerDiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.diary_item, parent, false);
        RecyclerDiaryViewHolder listHolder = new RecyclerDiaryViewHolder(viewGroup);

        return listHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerDiaryViewHolder holder, final int position) {
        final RecyclerDiaryViewHolder mainHolder = (RecyclerDiaryViewHolder) holder;
        DiaryItem item = arrayList.get(position);

        mainHolder.diaryContent.setText(item.getDiary_content());
        mainHolder.diaryDate.setText(item.getDiary_date());
        Glide.with(mContext).load(item.getDiary_img()).into(mainHolder.diaryImg);

        //다이어리 옵션메뉴 띄우기
        mainHolder.diaryMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(mainHolder.diaryMenu, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (arrayList != null) ? arrayList.size() : 0;
    }

    public DiaryItem getItem(int position) {
        return arrayList.get(position);
    }

    public void clear() {
        arrayList.clear();
    }

    public void addItem(DiaryItem item) {
        arrayList.add(item);
    }

    public void showPopupMenu(View view, int position) {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_diary_item, popup.getMenu());

        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
        popup.show();
    }
}
