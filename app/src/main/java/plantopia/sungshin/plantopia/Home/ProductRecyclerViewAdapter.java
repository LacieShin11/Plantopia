package plantopia.sungshin.plantopia.Home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import plantopia.sungshin.plantopia.R;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerProductViewHolder> {
    private ArrayList<ProductItem> arrayList;
    private Context mContext;

    public ProductRecyclerViewAdapter(ArrayList<ProductItem> arrayList, Context mContext) {
        this.arrayList = arrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.item_product, parent, false);
        RecyclerProductViewHolder listHolder = new RecyclerProductViewHolder(viewGroup);

        return listHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerProductViewHolder holder, int position) {
        RecyclerProductViewHolder mainHolder = (RecyclerProductViewHolder) holder;
        ProductItem item = arrayList.get(position);

        mainHolder.titleText.setText(item.getName());
        mainHolder.priceText.setText(item.getPrice());

        if(item.getImage().isEmpty())
            Glide.with(mContext).load(R.drawable.test).into(mainHolder.postImg);
        else
            Glide.with(mContext).load(item.getImage()).into(mainHolder.postImg);
    }

    @Override
    public int getItemCount() {
        return (arrayList != null) ? arrayList.size() : 0;
    }

    public ProductItem getItem(int position) {
        return arrayList.get(position);
    }

    public void clear() {
        arrayList.clear();
    }

    public void addItem(ProductItem item) {
        arrayList.add(item);
    }
}
