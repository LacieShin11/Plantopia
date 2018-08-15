package plantopia.sungshin.plantopia.Music;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import plantopia.sungshin.plantopia.R;

public class MusicListAdapter extends BaseAdapter{
    private MusicListItem[] musicList = new MusicListItem[5];

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public MusicListItem getItem(int position) {
        return musicList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context mContext = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.music_list_item, parent, false);
        }

        final TextView songTitleText = convertView.findViewById(R.id.song_title_text);
        final TextView artistText = convertView.findViewById(R.id.artist_text);
        final TextView songLengthText = convertView.findViewById(R.id.song_length_text);

        MusicListItem item = musicList[position];

        songTitleText.setText(item.getSongTitle());
        artistText.setText(item.getArtist());
        songLengthText.setText(item.getSongLength());

        return convertView;
    }

    public void setItem (MusicListItem [] item) {
        musicList = item;

        notifyDataSetChanged();
    }
}
