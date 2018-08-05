package plantopia.sungshin.plantopia;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RecyclerProductViewHolder extends RecyclerView.ViewHolder {
    TextView titleText, priceText;
    ImageView postImg;

    public RecyclerProductViewHolder(View itemView) {
        super(itemView);

        this.titleText = (TextView) itemView.findViewById(R.id.product_name);
        this.priceText = (TextView) itemView.findViewById(R.id.product_text);
        this.postImg = (ImageView) itemView.findViewById(R.id.product_img);
    }
}
