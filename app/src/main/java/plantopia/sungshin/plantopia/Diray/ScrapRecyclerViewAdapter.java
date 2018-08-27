package plantopia.sungshin.plantopia.Diray;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;

import java.util.List;

import plantopia.sungshin.plantopia.R;
import plantopia.sungshin.plantopia.customView.ScrapDeleteMenuItemClickListener;

public class ScrapRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerScrapViewHolder> {
    private List<ScrapItem> arrayList;
    private int scrapId;
    private Context mContext;

    public ScrapRecyclerViewAdapter() {
    }

    public ScrapRecyclerViewAdapter(List<ScrapItem> arrayList, Context mContext) {
        this.arrayList = arrayList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerScrapViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.scrap_item, parent, false);
        RecyclerScrapViewHolder listHolder = new RecyclerScrapViewHolder(viewGroup);

        return listHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerScrapViewHolder holder, final int position) {
        final RecyclerScrapViewHolder mainHolder = (RecyclerScrapViewHolder) holder;
        ScrapItem item = arrayList.get(position);
        scrapId = item.getScrap_id();

        mainHolder.scrapTitle.setText(item.getScrap_title());
        mainHolder.scrapSource.setText(item.getScrap_source());
        Glide.with(mContext).load(item.getScrap_img()).into(mainHolder.scrapImg);

        //다이어리 옵션메뉴 띄우기
        mainHolder.scrapMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(mainHolder.scrapMenu, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return (arrayList != null) ? arrayList.size() : 0;
    }

    public ScrapItem getItem(int position) {
        return arrayList.get(position);
    }

    public void clear() {
        arrayList.clear();
    }

    public void addItem(ScrapItem item) {
        arrayList.add(item);
    }

    public void showPopupMenu(View view, int position) {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_scrap_show, popup.getMenu());

        popup.setOnMenuItemClickListener(new ScrapDeleteMenuItemClickListener(arrayList.get(position).getScrap_id(), mContext));
        popup.show();
    }
}
