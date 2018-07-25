package plantopia.sungshin.plantopia;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ProductRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private ArrayList<ProductItem> arrayList;
    private Context mContext;

    public ProductRecyclerViewAdapter(ArrayList<ProductItem> arrayList, Context mContext) {
        this.arrayList = arrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.item_product, parent, false);
        RecyclerViewHolder listHolder = new RecyclerViewHolder(viewGroup);

        return listHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        RecyclerViewHolder mainHolder = (RecyclerViewHolder) holder;
        ProductItem item = arrayList.get(position);

//        Bitmap img = BitmapFactory.decodeResource(mContext.getResources(), item.getImg());

        mainHolder.titleText.setText(item.getName());
        mainHolder.sourceText.setText(item.getPrice());
        mainHolder.postImg.setImageResource(R.drawable.test2);
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
