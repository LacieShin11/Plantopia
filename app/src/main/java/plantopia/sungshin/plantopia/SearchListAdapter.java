package plantopia.sungshin.plantopia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchListAdapter extends BaseAdapter implements Filterable {
    ArrayList<Plant> arrayList = new ArrayList<Plant>();
    ArrayList<Plant> filteredArrayList = arrayList;
    Filter listFilter;

    public SearchListAdapter() {

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

        Plant plant = filteredArrayList.get(position);

        textView.setText(plant.getPlantName());

        return v;
    }

    public void clear() {
        arrayList.clear();
    }

    public void addPlant(String name, String number)
    {
        Plant plant = new Plant(name, number);

        arrayList.add(plant);
    }

    @Override
    public Filter getFilter() {
        if(listFilter == null)
        {
            listFilter = new ListFilter();
        }

        return listFilter;
    }

    private class ListFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if(constraint == null || constraint.length() == 0)
            {
                results.values = arrayList;
                results.count = arrayList.size();
            }
            else
            {
                ArrayList<Plant> plantList = new ArrayList<Plant>();

                for(Plant plant : arrayList)
                {
                    if(plant.getPlantName().toUpperCase().contains(constraint.toString().toUpperCase()))
                    {
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
            filteredArrayList = (ArrayList<Plant>) results.values;

            if(results.count > 0)
            {
                notifyDataSetChanged();
            }
            else
            {
                notifyDataSetInvalidated();
            }

        }
    }
}
