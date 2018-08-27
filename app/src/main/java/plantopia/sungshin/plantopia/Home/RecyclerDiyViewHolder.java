package plantopia.sungshin.plantopia.Home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import plantopia.sungshin.plantopia.R;

public class RecyclerDiyViewHolder extends RecyclerView.ViewHolder {
    TextView titleText, sourceText;
    ImageView postImg, scrapMenu;

    public RecyclerDiyViewHolder(View itemView) {
        super(itemView);

        this.titleText = (TextView) itemView.findViewById(R.id.diy_title);
        this.sourceText = (TextView) itemView.findViewById(R.id.diy_source);
        this.postImg = (ImageView) itemView.findViewById(R.id.diy_img);
        this.scrapMenu = (ImageView) itemView.findViewById(R.id.diy_scrap);

    }
}