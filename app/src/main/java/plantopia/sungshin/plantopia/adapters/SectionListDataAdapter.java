package plantopia.sungshin.plantopia.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import plantopia.sungshin.plantopia.PostItem;
import plantopia.sungshin.plantopia.R;

public class SectionListDataAdapter extends RecyclerView.Adapter<SectionListDataAdapter.PostItemRowHolder>{
    private List<PostItem> itemsList;
    private Context mContext;

    public SectionListDataAdapter(List<PostItem> itemsList, Context mContext) {
        this.itemsList = itemsList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public PostItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, null);
        return new PostItemRowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostItemRowHolder holder, int position) {
        PostItem item = itemsList.get(position);

        holder.titleText.setText(item.getTitle());
        holder.sourceText.setText(item.getSource());

//        holder.postImg.setImageResource(item.getImg());
        /*Glide.with(mContext)
                .load(item.getImage())
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .error(R.drawable.android)
                .into(holder.postImg);*/
    }

    @Override
    public int getItemCount() {
        return (null != itemsList ? itemsList.size() : 0);
    }

    public class PostItemRowHolder extends RecyclerView.ViewHolder {
        protected TextView titleText, sourceText;
        protected ImageView postImg;

        public PostItemRowHolder(View view) {
            super(view);
//
            this.titleText = (TextView) view.findViewById(R.id.post_title);
            this.sourceText = (TextView) view.findViewById(R.id.post_source);
            this.postImg = (ImageView) view.findViewById(R.id.post_img);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), titleText.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
