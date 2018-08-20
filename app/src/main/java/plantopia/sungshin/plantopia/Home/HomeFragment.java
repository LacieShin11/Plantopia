package plantopia.sungshin.plantopia.Home;

import android.app.Activity;
import android.content.Context;
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
import plantopia.sungshin.plantopia.R;
import plantopia.sungshin.plantopia.RecyclerItemClickListener;

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
    String[] titles = new String[]{"1 키우기", "2 키우기", "3 키우기", "4 키우기", "5 키우기"};
    String[] sources = new String[]{"네이버 포스트", "네이버 포스트", "네이버 포스트", "네이버 포스트", "네이버 포스트"};
    String[] url = new String[]{"연결링크 1", "연결링크 2", "연결링크 3", "연결링크 4", "연결링크 5"};
    int[] images = new int[]{R.drawable.test, R.drawable.test, R.drawable.test};

    String postUrl = "https://brunch.co.kr/search?q=%EC%8B%9D%EB%AC%BC%20%ED%82%A4%EC%9A%B0%EA%B8%B0&type=article";
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

        //가로 리스트뷰 설정
        postGallery.setHasFixedSize(true);
        postGallery.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        diyGallery.setHasFixedSize(true);
        diyGallery.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        productGallery.setHasFixedSize(true);
        productGallery.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        postGallery.setAdapter(postAdapter);
        diyGallery.setAdapter(diyAdapter);
        productGallery.setAdapter(productAdapter);

        postAdapter.notifyDataSetChanged();
        diyAdapter.notifyDataSetChanged();
        productAdapter.notifyDataSetChanged();

        getWebsite();

        postGallery.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), postGallery, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), postAdapter.getItem(position).getUrl(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongItemClick(View view, int position) {
            }
        }));

        diyGallery.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), diyGallery, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), diyAdapter.getItem(position).getUrl(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongItemClick(View view, int position) {
            }
        }));

        productGallery.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), productGallery, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getContext(), productAdapter.getItem(position).getUrl(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongItemClick(View view, int position) {
            }
        }));
        return view;
    }

    //웹 크롤링한 내용 가져오는 함수
    private void getWebsite() {
        MainPageTask.execute(new Runnable() {
            @Override
            public void run() {

            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();
/*
                try {
                    postDoc = Jsoup.connect(postUrl)
                            .header("Accept-Encoding", "gzip, deflate")
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Safari/537.36")
                            .maxBodySize(0)
                            .timeout(0)
                            .get();

                    Log.d("아이템", postDoc.toString());
                    Elements links = postDoc.select("tit_subject");

                    for (int i = 0; i < links.size(); i++) {
                        builder.append(links.get(i).text());
                        titles[i] = builder.toString();
                        builder.setLength(0);
                        Log.d("아이템", titles[i]);
                    }

                } catch (Exception e) {
                    builder.append("Error : ").append(e.getMessage()).append("\n");
                }*/

                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < titles.length; i++) {
                            PostItem item = new PostItem(url[i], titles[i], sources[i], 0);
                            DIYItem item2 = new DIYItem(url[i], titles[i], sources[i]);
                            ProductItem item3 = new ProductItem("화분", "2000원", "www.naver.com");

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

    private class MainPageTask extends AsyncTask<Void, Void, Void> {
        private Elements element;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            for (Element e : element) {
                Log.d("elements", e.text());
            }
        }

        @Override
        protected Void doInBackground(Void... voids) {
            String postUrl = "https://brunch.co.kr/search?q=%EC%8B%9D%EB%AC%BC%20%ED%82%A4%EC%9A%B0%EA%B8%B0&type=article";

            try {
                Document doc = Jsoup.connect(postUrl).get();
                Log.d("Doc", doc.toString());
                element = doc.select("tit_subject");
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}