package plantopia.sungshin.plantopia;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

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

//        Bitmap img = BitmapFactory.decodeResource(mContext.getResources(), item.getImg());

        mainHolder.titleText.setText(item.getTitle());
        mainHolder.sourceText.setText(item.getSource());
        mainHolder.postImg.setImageResource(R.drawable.test);
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
