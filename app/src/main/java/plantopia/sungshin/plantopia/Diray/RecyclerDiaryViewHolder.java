package plantopia.sungshin.plantopia.Diray;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import plantopia.sungshin.plantopia.R;

class RecyclerDiaryViewHolder extends RecyclerView.ViewHolder {
    TextView diaryContent, diaryDate;
    ImageView diaryImg, diaryMenu;

    public RecyclerDiaryViewHolder(View itemView) {
        super(itemView);

        this.diaryContent = (TextView) itemView.findViewById(R.id.diary_content);
        this.diaryDate = (TextView) itemView.findViewById(R.id.diary_date);
        this.diaryImg = (ImageView) itemView.findViewById(R.id.diary_img);
        this.diaryMenu = (ImageView) itemView.findViewById(R.id.diary_menu);
    }
}