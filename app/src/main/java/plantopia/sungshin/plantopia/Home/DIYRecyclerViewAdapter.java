package plantopia.sungshin.plantopia.Home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import plantopia.sungshin.plantopia.R;

public class DIYRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerDiyViewHolder> {
    private ArrayList<DIYItem> arrayList;
    private Context mContext;

    public DIYRecyclerViewAdapter(ArrayList<DIYItem> arrayList, Context mContext) {
        this.arrayList = arrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerDiyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.item_diy, parent, false);
        RecyclerDiyViewHolder listHolder = new RecyclerDiyViewHolder(viewGroup);

        return listHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerDiyViewHolder holder, int position) {
        RecyclerDiyViewHolder mainHolder = (RecyclerDiyViewHolder) holder;
        DIYItem item = arrayList.get(position);

        mainHolder.titleText.setText(item.getTitle());
        mainHolder.sourceText.setText(item.getSource());
        Glide.with(mContext).load(R.drawable.test).into(mainHolder.postImg);
    }

    @Override
    public int getItemCount() {
        return (arrayList != null) ? arrayList.size() : 0;
    }

    public DIYItem getItem(int position) {
        return arrayList.get(position);
    }

    public void clear() {
        arrayList.clear();
    }

    public void addItem(DIYItem item) {
        arrayList.add(item);
        notifyDataSetChanged();
    }
}
