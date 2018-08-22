package plantopia.sungshin.plantopia.Diray;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import plantopia.sungshin.plantopia.R;

public class DiaryRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerDiaryViewHolder> {
    private ArrayList<DiaryItem> arrayList;
    private Context mContext;

    public DiaryRecyclerViewAdapter() {
    }

    public DiaryRecyclerViewAdapter(ArrayList<DiaryItem> arrayList, Context mContext) {
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
    public void onBindViewHolder(@NonNull RecyclerDiaryViewHolder holder, int position) {
        RecyclerDiaryViewHolder mainHolder = (RecyclerDiaryViewHolder) holder;
        DiaryItem item = arrayList.get(position);

        mainHolder.diaryContent.setText(item.getDiaryContent());
        mainHolder.diaryDate.setText(item.getDiaryDate());
        Glide.with(mContext).load(item.getDiaryImg()).into(mainHolder.diaryImg);
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

}
