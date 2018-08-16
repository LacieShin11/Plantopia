package plantopia.sungshin.plantopia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchListAdapter extends BaseAdapter {
    ArrayList<String> arrayList;
    ArrayList<String> plantNumberList;
    // ArrayList<String> bitmapList;

    public SearchListAdapter(ArrayList<String> arrayList, ArrayList<String> plantNumberList) {
        this.arrayList = arrayList;
        this.plantNumberList = plantNumberList;
        // this.bitmapList = bitmapList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public String getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        Context context = parent.getContext();

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.item_search, parent, false);
        }

        TextView textView = v.findViewById(R.id.search_text);
        ImageView imageView = v.findViewById(R.id.search_img);

        textView.setText(arrayList.get(position));
        /*Bitmap bm = BitmapFactory.decodeFile(bitmapList.get(position));
        imageView.setImageBitmap(bm);*/

        return v;
    }

    public void clear() {
        arrayList.clear();
    }


}
