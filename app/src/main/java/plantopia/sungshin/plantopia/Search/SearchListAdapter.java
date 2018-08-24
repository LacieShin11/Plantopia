package plantopia.sungshin.plantopia.Search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import plantopia.sungshin.plantopia.PlantItem;
import plantopia.sungshin.plantopia.R;

public class SearchListAdapter extends BaseAdapter implements Filterable {
    ArrayList<PlantItem> arrayList = new ArrayList<PlantItem>();
    ArrayList<PlantItem> filteredArrayList = arrayList;
    Filter listFilter;

    public SearchListAdapter() {
    }

    public SearchListAdapter(ArrayList<PlantItem> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return filteredArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredArrayList.get(position);
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
        PlantItem plant = filteredArrayList.get(position);

        textView.setText(plant.getPlant_name());
        Glide.with(context).load(plant.getPlant_img()).into(imageView);

        return v;
    }

    public void clear() {
        arrayList.clear();
    }

    public void addPlant(String name, String number, String imgPath) {
        PlantItem plant = new PlantItem(name, number, imgPath);

        arrayList.add(plant);
    }

    public void addPlant(PlantItem item) {
        arrayList.add(item);
    }

    @Override
    public Filter getFilter() {
        if (listFilter == null) {
            listFilter = new ListFilter();
        }

        return listFilter;
    }

    private class ListFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0) {
                results.values = arrayList;
                results.count = arrayList.size();
            } else {
                ArrayList<PlantItem> plantList = new ArrayList<PlantItem>();

                for (PlantItem plant : arrayList) {
                    if (plant.getPlant_name().toUpperCase().contains(constraint.toString().toUpperCase())) {
                        plantList.add(plant);
                    }
                }

                results.values = plantList;
                results.count = plantList.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredArrayList = (ArrayList<PlantItem>) results.values;

            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }

        }

    }
}
