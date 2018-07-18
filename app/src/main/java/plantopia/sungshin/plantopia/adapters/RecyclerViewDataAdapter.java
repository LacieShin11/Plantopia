package plantopia.sungshin.plantopia.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import plantopia.sungshin.plantopia.PostData;

public class RecyclerViewDataAdapter extends RecyclerView.Adapter<RecyclerViewDataAdapter.ItemRowHolder> {
    private ArrayList<PostData> dataList;
    private Context mContext;

    public RecyclerViewDataAdapter(Context context, ArrayList<PostData> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerViewDataAdapter.ItemRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewDataAdapter.ItemRowHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }


    public class ItemRowHolder extends RecyclerView.ViewHolder {
        protected RecyclerView recyclerView;

        public ItemRowHolder(View view) {
            super(view);

//            this.itemTitle = (TextView) view.findViewById(R.id.itemTitle);
//            this.recycler_view_list = (RecyclerView) view.findViewById(R.id.recycler_view_list);
//            this.btnMore = (Button) view.findViewById(R.id.btnMore);
        }

    }
}
