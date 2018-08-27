package plantopia.sungshin.plantopia.Home;

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

import plantopia.sungshin.plantopia.R;
import plantopia.sungshin.plantopia.User.AutoLoginManager;
import plantopia.sungshin.plantopia.customView.ScrapMenuItemClickListener;

public class PostRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    private ArrayList<PostItem> arrayList;
    private Context mContext;
    private String url, title, source, image;

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
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {
        final RecyclerViewHolder mainHolder = (RecyclerViewHolder) holder;
        PostItem item = arrayList.get(position);

        url = item.getUrl();
        title = item.getTitle();
        source = item.getSource();
        image = item.getImage();
        mainHolder.titleText.setText(item.getTitle());
        mainHolder.sourceText.setText(item.getSource());

        if (item.getImage().isEmpty())
            Glide.with(mContext).load(R.drawable.test).into(mainHolder.postImg);
        else
            Glide.with(mContext).load(item.getImage()).into(mainHolder.postImg);

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

    public PostItem getItem(int position) {
        return arrayList.get(position);
    }

    public void clear() {
        arrayList.clear();
    }

    public void addItem(PostItem item) {
        arrayList.add(item);
    }

    public void showPopupMenu(View view, int position) {
        PopupMenu popup = new PopupMenu(view.getContext(), view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_scrap, popup.getMenu());

        popup.setOnMenuItemClickListener(new ScrapMenuItemClickListener(
                AutoLoginManager.getInstance(mContext).getUser().getUser_id(),
                position, url, title, source, image, mContext));
        popup.show();
    }
}