package plantopia.sungshin.plantopia.Home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import plantopia.sungshin.plantopia.R;

public class PostRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private ArrayList<PostItem> arrayList;
    private Context mContext;

    public PostRecyclerViewAdapter(ArrayList<PostItem> arrayList, Context mContext) {
        this.arrayList = arrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.item_post, parent, false);
        RecyclerViewHolder listHolder = new RecyclerViewHolder(viewGroup);

        return listHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        RecyclerViewHolder mainHolder = (RecyclerViewHolder) holder;
        PostItem item = arrayList.get(position);

        mainHolder.titleText.setText(item.getTitle());
        mainHolder.sourceText.setText(item.getSource());

        if(item.getImage().isEmpty())
            Glide.with(mContext).load(R.drawable.test).into(mainHolder.postImg);
        else
            Glide.with(mContext).load(item.getImage()).into(mainHolder.postImg);
    }

    @Override
    public int getItemCount() {
        return (arrayList != null) ? arrayList.size() : 0;
    }

    public PostItem getItem(int position) {
        return arrayList.get(position);
    }

    public void clear() {
        arrayList.clear();
    }

    public void addItem(PostItem item) {
        arrayList.add(item);
    }
}
