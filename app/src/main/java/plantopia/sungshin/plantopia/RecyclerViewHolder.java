package plantopia.sungshin.plantopia;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {
    TextView titleText, sourceText;
    ImageView postImg;

    public RecyclerViewHolder(View itemView) {
        super(itemView);

        this.titleText = (TextView) itemView.findViewById(R.id.post_title);
        this.sourceText = (TextView) itemView.findViewById(R.id.post_source);
        this.postImg = (ImageView) itemView.findViewById(R.id.post_img);

    }
}
