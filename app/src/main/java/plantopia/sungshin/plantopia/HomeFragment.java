package plantopia.sungshin.plantopia;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends android.support.v4.app.Fragment implements RecyclerView.OnItemTouchListener {
    @BindView(R.id.post_gallery) RecyclerView postGallery;
    @BindView(R.id.diy_gallery) RecyclerView diyGallery;
    @BindView(R.id.product_gallery) RecyclerView productGallery;

    ArrayList<PostItem> arrayList = new ArrayList<>();
    PostRecyclerViewAdapter postAdapter = new PostRecyclerViewAdapter(arrayList, getContext());
    String [] titles = new String[3];
    String [] sources =  new String[]{"네이버 블로그" ,"브런치", "브런치"};
    String [] url =  new String[]{"http://www.androhub.com/android-staggered-and-horizontal-recyclerview/" ,"두번째", "세번째"};
    int [] images = new int[]{R.drawable.test, R.drawable.test, R.drawable.test};


    String naverPostUrl = "https://post.naver.com/search/post.nhn?keyword=식물키우기&sortType=createDate.dsc&range=&term=all&navigationType=current";
    Document postDoc;

    private Unbinder unbinder;
    private ArrayList<PostItem> postItems = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        unbinder = ButterKnife.bind(this, view);

        postGallery.setHasFixedSize(true);
        postGallery.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        for (int i = 0; i < titles.length; i++) {
            arrayList.add(new PostItem(url[i], titles[i], sources[i], images[i]));
        }

        postGallery.setAdapter(postAdapter);
        postAdapter.notifyDataSetChanged();

        try {
            postDoc = Jsoup.connect(naverPostUrl).get();
            Elements elements = postDoc.select("tit_feed ell");
            Log.d("elements", elements.get(0).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        postGallery.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), postGallery, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), postAdapter.getItem(position).getTitle(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        return view;
    }

   /*public void getTitles(String[] titles) {
        final Element postElement;

        new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                try {
                    doc = Jsoup.connect(naverPostUrl).get();
                    postElement = doc.select("tit_feed ell");

                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
            }
        };
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
