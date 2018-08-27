package plantopia.sungshin.plantopia.Diray;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import plantopia.sungshin.plantopia.R;

public class RecyclerScrapViewHolder extends RecyclerView.ViewHolder {
    TextView scrapUrl, scrapTitle, scrapSource;
    ImageView scrapImg, scrapMenu;

    public RecyclerScrapViewHolder(View itemView) {
        super(itemView);

        this.scrapTitle = (TextView) itemView.findViewById(R.id.scrap_title);
        this.scrapSource = (TextView) itemView.findViewById(R.id.scrap_source);
        this.scrapImg = (ImageView) itemView.findViewById(R.id.scrap_img);
        this.scrapMenu = (ImageView) itemView.findViewById(R.id.scrap_menu);
    }

}