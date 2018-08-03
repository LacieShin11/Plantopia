package plantopia.sungshin.plantopia;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class HomeFragment extends android.support.v4.app.Fragment implements RecyclerView.OnItemTouchListener {
    @BindView(R.id.post_gallery)
    RecyclerView postGallery;
    @BindView(R.id.diy_gallery)
    RecyclerView diyGallery;
    @BindView(R.id.product_gallery)
    RecyclerView productGallery;

    ArrayList<PostItem> postList = new ArrayList<>();
    ArrayList<DIYItem> diyList = new ArrayList<>();
    ArrayList<ProductItem> productList = new ArrayList<>();

    Activity activity;
    PostRecyclerViewAdapter postAdapter = new PostRecyclerViewAdapter(postList, getContext());
    DIYRecyclerViewAdapter diyAdapter = new DIYRecyclerViewAdapter(diyList, getContext());
    ProductRecyclerViewAdapter productAdapter = new ProductRecyclerViewAdapter(productList, getContext());
    String[] titles = new String[]{"식물 키우기", "식물 키우기", "식물 키우기", "식물 키우기", "식물 키우기"};
    String[] sources = new String[]{"네이버 포스트", "네이버 포스트", "네이버 포스트", "네이버 포스트", "네이버 포스트"};
    String[] url = new String[]{"http://www.androhub.com/android-staggered-and-horizontal-recyclerview/", "두번째", "세번째", "다섯번째", "네번째"};
    int[] images = new int[]{R.drawable.test, R.drawable.test, R.drawable.test};

    String naverPostUrl = "http://petplant.co.kr/board/board.html?code=needsad_image3";
    Document postDoc;
    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) activity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab1, container, false);
        unbinder = ButterKnife.bind(this, view);

        postGallery.setHasFixedSize(true);
        postGallery.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        postGallery.setAdapter(postAdapter);
        postAdapter.notifyDataSetChanged();

        diyGallery.setAdapter(diyAdapter);
        postAdapter.notifyDataSetChanged();

        productGallery.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();

        getWebsite();

        postGallery.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), postGallery, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));

        return view;
    }

    private void getWebsite() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();

                try {
                    Document doc = Jsoup.connect(naverPostUrl).get();
                    Elements links = doc.select("list_title");

                    /*for (int i = 0; i < 5; i++) {
                        builder.append(links.get(i).text());
                        titles[i] = builder.toString();
                        builder.setLength(0);
                    }*/
                } catch (IOException e) {
                    builder.append("Error : ").append(e.getMessage()).append("\n");
                }

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < titles.length; i++) {
                            PostItem item = new PostItem(url[i], titles[i], sources[i], 0);
                            DIYItem item2 = new DIYItem(url[i], titles[i], sources[i]);
                            ProductItem item3 = new ProductItem("화분", "2000원", "www.maver.com");

                            postAdapter.addItem(item);
                            diyAdapter.addItem(item2);
                            productAdapter.addItem(item3);
                        }

                        postAdapter.notifyDataSetChanged();
                        diyAdapter.notifyDataSetChanged();
                        productAdapter.notifyDataSetChanged();
                }
                });
            }
        }).start();
    }


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
