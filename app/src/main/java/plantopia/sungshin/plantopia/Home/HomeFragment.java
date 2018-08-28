package plantopia.sungshin.plantopia.Home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import plantopia.sungshin.plantopia.R;
import plantopia.sungshin.plantopia.customView.RecyclerItemClickListener;

public class HomeFragment extends android.support.v4.app.Fragment implements RecyclerView.OnItemTouchListener {
    @BindView(R.id.post_gallery)
    RecyclerView postGallery;
    @BindView(R.id.diy_gallery)
    RecyclerView diyGallery;
    @BindView(R.id.product_gallery)
    RecyclerView productGallery;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    ArrayList<PostItem> postList = new ArrayList<>();
    ArrayList<DIYItem> diyList = new ArrayList<>();
    ArrayList<ProductItem> productList = new ArrayList<>();

    Context context;
    Activity activity;

    PostRecyclerViewAdapter postAdapter;
    DIYRecyclerViewAdapter diyAdapter;
    ProductRecyclerViewAdapter productAdapter;
    String[] titles = new String[10];
    String[] sources = new String[10];

    private Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
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

        postAdapter = new PostRecyclerViewAdapter(postList, context);
        diyAdapter = new DIYRecyclerViewAdapter(diyList, context);
        productAdapter = new ProductRecyclerViewAdapter(productList, context);

        postGallery.setAdapter(postAdapter);
        diyGallery.setAdapter(diyAdapter);
        productGallery.setAdapter(productAdapter);

        postAdapter.notifyDataSetChanged();
        diyAdapter.notifyDataSetChanged();
        productAdapter.notifyDataSetChanged();

        NetworkThread networkThread = new NetworkThread("실내 식물");
        networkThread.start();

        NetworkThread2 networkThread2 = new NetworkThread2("식물 DIY");
        networkThread2.start();

        NetworkCommercialThread networkCommercialThread = new NetworkCommercialThread("실내 식물");
        networkCommercialThread.start();

        postGallery.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), postGallery, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // Toast.makeText(getContext(), postAdapter.getItem(position).getUrl(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(postAdapter.getItem(position).getUrl()));
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {
            }
        }));

        diyGallery.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), diyGallery, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // Toast.makeText(getContext(), diyAdapter.getItem(position).getUrl(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(diyAdapter.getItem(position).getUrl()));
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {
            }
        }));

        productGallery.addOnItemTouchListener(new RecyclerItemClickListener(getContext(), productGallery, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                // Toast.makeText(getContext(), productAdapter.getItem(position).getUrl(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(productAdapter.getItem(position).getUrl()));
                startActivity(intent);
            }

            @Override
            public void onLongItemClick(View view, int position) {
            }
        }));

        return view;
    }


    class NetworkThread extends Thread {
        String keyWord;
        ArrayList<String> result_title_list;
        ArrayList<String> result_link_list;
        ArrayList<String> result_image_list;
        String restKey = "9f6dea22ddfd77308c3a0a224de2ec9a";

        public NetworkThread(String keyWord) {
            this.keyWord = keyWord;
        }

        @Override
        public void run() {
            try {
                result_title_list = new ArrayList<String>();
                result_link_list = new ArrayList<String>();
                result_image_list = new ArrayList<String>();
                String query = "";
                query = URLEncoder.encode(keyWord, "utf-8");

                String address = "https://dapi.kakao.com/v2/search/blog?query=" + query;

                BufferedReader br;
                URL url;
                HttpURLConnection conn;

                String protocol = "GET";

                url = new URL(address);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod(protocol);
                conn.setRequestProperty("Authorization", "KakaoAK " + restKey);
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("charset", "UTF-8");
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

                StringBuffer result = new StringBuffer();

                String line = "";
                while ((line = br.readLine()) != null) {
                    result.append(new String(URLDecoder.decode(line, "UTF-8")));
                }

                Log.d("json : ", result.toString());

                conn.disconnect();

                String json = result.toString();

                JsonParser jsonParser = new JsonParser();
                JsonObject jsonObject = (JsonObject) jsonParser.parse(json);
                JsonArray jsonArray = (JsonArray) jsonObject.get("documents");
                int length = jsonArray.size();

                for (int i = 0; i < length; i++) {
                    JsonObject jsonObject2 = (JsonObject) jsonArray.get(i);
                    result_title_list.add(jsonObject2.get("title").toString());
                    result_link_list.add(jsonObject2.get("url").toString());
                    result_image_list.add(jsonObject2.get("thumbnail").toString());
                }

                for (int j = 0; j < result_title_list.size(); j++) {
                    String title = result_title_list.get(j).replace("<b>", "");
                    title = title.replace("</b>", "");
                    title = title.substring(1, title.length() - 1);
                    result_title_list.set(j, title);
                }

                for (int j = 0; j < result_link_list.size(); j++) {
                    String title = result_link_list.get(j).substring(1, result_link_list.get(j).length() - 1);
                    result_link_list.set(j, title);
                }

                for (int j = 0; j < result_image_list.size(); j++) {
                    String title = result_image_list.get(j).substring(1, result_image_list.get(j).length() - 1);
                    result_image_list.set(j, title);
                }

                for (int i = 0; i < result_title_list.size(); i++) {
                    Log.d("title", result_title_list.get(i));
                    Log.d("url", result_link_list.get(i));
                    Log.d("thumbnail", result_image_list.get(i));
                }
            } catch (Exception e) {
                System.out.println(e);
            }

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = 0; i < result_title_list.size(); i++) {
                            PostItem item = new PostItem(result_link_list.get(i).toString(), result_title_list.get(i).toString(),
                                    (result_link_list.get(i).toString().contains("youtube")) ? "유튜부" : "블로그", result_image_list.get(i).toString());
                            postAdapter.addItem(item);
                        }
                        postAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    class NetworkThread2 extends Thread {
        String keyWord;
        ArrayList<String> resultTitleList;
        ArrayList<String> resultLinkList;
        ArrayList<String> resultImageList;
        String restKey = "9f6dea22ddfd77308c3a0a224de2ec9a";

        public NetworkThread2(String keyWord) {
            this.keyWord = keyWord;
        }

        @Override
        public void run() {
            try {
                resultTitleList = new ArrayList<String>();
                resultLinkList = new ArrayList<String>();
                resultImageList = new ArrayList<String>();

                String query = "";
                query = URLEncoder.encode(keyWord, "utf-8");

                String address = "https://dapi.kakao.com/v2/search/vclip?query=" + query;

                BufferedReader br;
                URL url;
                HttpURLConnection conn;

                String protocol = "GET";

                url = new URL(address);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod(protocol);
                conn.setRequestProperty("Authorization", "KakaoAK " + restKey);
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("charset", "UTF-8");
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

                StringBuffer result = new StringBuffer();

                String line = "";
                while ((line = br.readLine()) != null) {
                    result.append(new String(URLDecoder.decode(line, "UTF-8")));
                }

                Log.d("json : ", result.toString());

                conn.disconnect();

                String json = result.toString();

                JsonParser jsonParser = new JsonParser();
                JsonObject jsonObject = (JsonObject) jsonParser.parse(json);
                JsonArray jsonArray = (JsonArray) jsonObject.get("documents");
                int length = jsonArray.size();

                for (int i = 0; i < length; i++) {
                    JsonObject jsonObject2 = (JsonObject) jsonArray.get(i);
                    resultTitleList.add(jsonObject2.get("title").toString());
                    resultLinkList.add(jsonObject2.get("url").toString());
                    resultImageList.add(jsonObject2.get("thumbnail").toString());
                }

                for (int j = 0; j < resultTitleList.size(); j++) {
                    String title = resultTitleList.get(j).replace("<b>", "");
                    title = title.replace("</b>", "");
                    title = title.substring(1, title.length() - 1);
                    resultTitleList.set(j, title);
                }

                for (int j = 0; j < resultImageList.size(); j++) {
                    String title = resultImageList.get(j).substring(1, resultImageList.get(j).length() - 1);
                    resultImageList.set(j, title);
                }

                for (int j = 0; j < resultLinkList.size(); j++) {
                    String title = resultLinkList.get(j).substring(1, resultLinkList.get(j).length() - 1);
                    resultLinkList.set(j, title);
                }

                for (int i = 0; i < resultTitleList.size(); i++) {
                    Log.d("title", resultTitleList.get(i).toString());
                    Log.d("url", resultLinkList.get(i).toString());
                    Log.d("thumbnail", resultImageList.get(i).toString());
                }


            } catch (Exception e) {
                System.out.println(e);
            }

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < resultTitleList.size(); i++) {
                        DIYItem item2 = new DIYItem(resultLinkList.get(i).toString(), resultTitleList.get(i).toString(),
                                "유튜브", resultImageList.get(i).toString());

                        diyAdapter.addItem(item2);
                    }
                    diyAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    class NetworkCommercialThread extends Thread {
        String keyWord;
        ArrayList<String> resultTitleList;
        ArrayList<String> resultLinkList;
        ArrayList<String> resultImageList;
        ArrayList<String> resultLpriceList;
        String restKey = "9f6dea22ddfd77308c3a0a224de2ec9a";

        public NetworkCommercialThread(String keyWord) {
            this.keyWord = keyWord;
        }

        @Override
        public void run() {
            try {
                resultTitleList = new ArrayList<String>();
                resultLinkList = new ArrayList<String>();
                resultImageList = new ArrayList<String>();
                resultLpriceList = new ArrayList<String>();

                String query;
                query = URLEncoder.encode(keyWord, "utf-8");

                String address = "https://dapi.kakao.com/v2/search/book?query=" + query;

                BufferedReader br;
                URL url;
                HttpURLConnection conn;

                String protocol = "GET";

                url = new URL(address);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod(protocol);
                conn.setRequestProperty("Authorization", "KakaoAK " + restKey);
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("charset", "UTF-8");
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

                StringBuffer result = new StringBuffer();

                String line = "";
                while ((line = br.readLine()) != null) {
                    result.append(new String(URLDecoder.decode(line, "UTF-8")));
                }

                Log.d("json : ", result.toString());

                conn.disconnect();

                String json = result.toString();

                JsonParser jsonParser = new JsonParser();
                JsonObject jsonObject = (JsonObject) jsonParser.parse(json);
                JsonArray jsonArray = (JsonArray) jsonObject.get("documents");
                int length = jsonArray.size();

                for (int i = 0; i < length; i++) {
                    JsonObject jsonObject2 = (JsonObject) jsonArray.get(i);
                    resultTitleList.add(jsonObject2.get("title").toString());
                    resultLinkList.add(jsonObject2.get("url").toString());
                    resultImageList.add(jsonObject2.get("thumbnail").toString());
                    resultLpriceList.add(jsonObject2.get("price").toString());
                }

                for (int j = 0; j < resultTitleList.size(); j++) {
                    String title = resultTitleList.get(j).replace("<b>", "");
                    title = title.replace("</b>", "");
                    title = title.substring(1, title.length() - 1);
                    resultTitleList.set(j, title);
                }

                for (int j = 0; j < resultLinkList.size(); j++) {
                    String title = resultLinkList.get(j).substring(1, resultLinkList.get(j).length() - 1);
                    resultLinkList.set(j, title);
                }

                for (int j = 0; j < resultImageList.size(); j++) {
                    String title = resultImageList.get(j).substring(1, resultImageList.get(j).length() - 1);
                    resultImageList.set(j, title);
                }

                for (int i = 0; i < resultTitleList.size(); i++) {
                    Log.d("title", resultTitleList.get(i).toString());
                    Log.d("url", resultLinkList.get(i).toString());
                    Log.d("thumbnail", resultImageList.get(i).toString());
                    Log.d("price", resultLpriceList.get(i).toString());
                }

            } catch (Exception e) {
                System.out.println(e);
            }

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < resultTitleList.size(); i++) {
                        ProductItem item = new ProductItem(resultTitleList.get(i).toString(), resultLpriceList.get(i).toString() + "원",
                                resultLinkList.get(i).toString(), resultImageList.get(i).toString());

                        productAdapter.addItem(item);
                    }
                    productAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });
        }
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