package plantopia.sungshin.plantopia.Home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

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
                Toast.makeText(getContext(), postAdapter.getItem(position).getUrl(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), diyAdapter.getItem(position).getUrl(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(getContext(), productAdapter.getItem(position).getUrl(), Toast.LENGTH_SHORT).show();
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
        String clientId = "DRfB9SU8IQ1WNR4K3IIG";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "_DS6qAhAc3";//애플리케이션 클라이언트 시크릿값";
        ArrayList<String> result_title_list;
        ArrayList<String> result_link_list;

        public NetworkThread(String keyWord) {
            this.keyWord = keyWord;
        }

        @Override
        public void run() {
            try {
                result_title_list = new ArrayList<String>();
                result_link_list = new ArrayList<String>();

                String text = URLEncoder.encode(keyWord, "UTF-8");
                // String apiURL = "https://openapi.naver.com/v1/search/blog?query="+ text; // json 결과
                String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query=" + text; // xml 결과
                StringBuffer sb = new StringBuffer();

                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("X-Naver-Client-Id", clientId);
                con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

                InputStream is = con.getInputStream();

                // DOM  파서 생성
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();

                byte[] bytes = new byte[4096];
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while (true) {
                    int red = is.read(bytes);
                    if (red < 0) break;
                    baos.write(bytes, 0, red);

                }
                String xmlData = baos.toString("utf-8");
                baos.close();
                is.close();
                con.disconnect();
                org.w3c.dom.Document doc = builder.parse(new InputSource(new StringReader(xmlData)));

                for (int i = 0; i < doc.getElementsByTagName("item").getLength(); i++) {
                    org.w3c.dom.Element el = (org.w3c.dom.Element) doc.getElementsByTagName("item").item(i);
                    for (int j = 0; j < ((Node) el).getChildNodes().getLength(); j++) {
                        Node node = ((Node) el).getChildNodes().item(j);
                        if (node.getNodeName().equals("title")) {
                            String title = (String) node.getFirstChild().getTextContent();
                            result_title_list.add(title);
                        } else if (node.getNodeName().equals("link")) {
                            String link = (String) node.getFirstChild().getTextContent();
                            result_link_list.add(link);
                        }
                    }
                }

                for (int j = 0; j < result_title_list.size(); j++) {
                    String title = result_title_list.get(j).replace("<b>", "");
                    title = title.replace("</b>", "");
                    result_title_list.set(j, title);
                }


            } catch (Exception e) {
                System.out.println(e);
            }

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = 0; i < titles.length; i++) {
                            PostItem item = new PostItem(result_link_list.get(i).toString(), result_title_list.get(i).toString(), "네이버 블로그", 0);
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
        String clientId = "DRfB9SU8IQ1WNR4K3IIG";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "_DS6qAhAc3";//애플리케이션 클라이언트 시크릿값";
        ArrayList<String> resultTitleList;
        ArrayList<String> resultLinkList;

        public NetworkThread2(String keyWord) {
            this.keyWord = keyWord;
        }

        @Override
        public void run() {
            try {
                resultTitleList = new ArrayList<String>();
                resultLinkList = new ArrayList<String>();

                String text = URLEncoder.encode(keyWord, "UTF-8");
                // String apiURL = "https://openapi.naver.com/v1/search/blog?query="+ text; // json 결과
                String apiURL = "https://openapi.naver.com/v1/search/blog.xml?query=" + text; // xml 결과
                StringBuffer sb = new StringBuffer();

                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("X-Naver-Client-Id", clientId);
                con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

                InputStream is = con.getInputStream();

                // DOM  파서 생성
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();

                byte[] bytes = new byte[4096];
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while (true) {
                    int red = is.read(bytes);
                    if (red < 0) break;
                    baos.write(bytes, 0, red);

                }
                String xmlData = baos.toString("utf-8");
                baos.close();
                is.close();
                con.disconnect();
                org.w3c.dom.Document doc = builder.parse(new InputSource(new StringReader(xmlData)));

                for (int i = 0; i < doc.getElementsByTagName("item").getLength(); i++) {
                    org.w3c.dom.Element el = (org.w3c.dom.Element) doc.getElementsByTagName("item").item(i);
                    for (int j = 0; j < ((Node) el).getChildNodes().getLength(); j++) {
                        Node node = ((Node) el).getChildNodes().item(j);
                        if (node.getNodeName().equals("title")) {
                            String title = (String) node.getFirstChild().getTextContent();
                            resultTitleList.add(title);
                        } else if (node.getNodeName().equals("link")) {
                            String link = (String) node.getFirstChild().getTextContent();
                            resultLinkList.add(link);
                        }
                    }
                }

                for (int j = 0; j < resultTitleList.size(); j++) {
                    String title = resultTitleList.get(j).replace("<b>", "");
                    title = title.replace("</b>", "");
                    resultTitleList.set(j, title);
                }


            } catch (Exception e) {
                System.out.println(e);
            }

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < titles.length; i++) {
                        DIYItem item2 = new DIYItem(resultLinkList.get(i).toString(), resultTitleList.get(i).toString(), "네이버 블로그");

                        diyAdapter.addItem(item2);
                    }
                    diyAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    class NetworkCommercialThread extends Thread {
        String keyWord;
        String clientId = "DRfB9SU8IQ1WNR4K3IIG";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "_DS6qAhAc3";//애플리케이션 클라이언트 시크릿값";
        ArrayList<String> resultTitleList;
        ArrayList<String> resultLinkList;
        ArrayList<String> resultImageList;
        ArrayList<String> resultLpriceList;

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

                String text = URLEncoder.encode(keyWord, "UTF-8");
                String apiURL = "https://openapi.naver.com/v1/search/shop.xml?query=" + text; // xml 결과
                StringBuffer sb = new StringBuffer();

                URL url = new URL(apiURL);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("X-Naver-Client-Id", clientId);
                con.setRequestProperty("X-Naver-Client-Secret", clientSecret);

                InputStream is = con.getInputStream();

                // DOM  파서 생성
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();

                byte[] bytes = new byte[4096];
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while (true) {
                    int red = is.read(bytes);
                    if (red < 0) break;
                    baos.write(bytes, 0, red);

                }
                String xmlData = baos.toString("utf-8");
                baos.close();
                is.close();
                con.disconnect();
                org.w3c.dom.Document doc = builder.parse(new InputSource(new StringReader(xmlData)));

                for (int i = 0; i < doc.getElementsByTagName("item").getLength(); i++) {
                    org.w3c.dom.Element el = (org.w3c.dom.Element) doc.getElementsByTagName("item").item(i);
                    for (int j = 0; j < ((Node) el).getChildNodes().getLength(); j++) {
                        Node node = ((Node) el).getChildNodes().item(j);
                        if (node.getNodeName().equals("title")) {
                            String title = (String) node.getFirstChild().getTextContent();
                            resultTitleList.add(title);
                        } else if (node.getNodeName().equals("link")) {
                            String link = (String) node.getFirstChild().getTextContent();
                            resultLinkList.add(link);
                        } else if (node.getNodeName().equals("image")) {
                            String image = (String) node.getFirstChild().getTextContent();
                            resultImageList.add(image);
                        } else if (node.getNodeName().equals("lprice")) {
                            String image = (String) node.getFirstChild().getTextContent();
                            resultLpriceList.add(image + "원");
                        }
                    }
                }

                for (int j = 0; j < resultTitleList.size(); j++) {
                    String title = resultTitleList.get(j).replace("<b>", "");
                    title = title.replace("</b>", "");
                    resultTitleList.set(j, title);
                }

            } catch (Exception e) {
                System.out.println(e);
            }

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < titles.length; i++) {
                        ProductItem item = new ProductItem(resultTitleList.get(i).toString(), resultLpriceList.get(i).toString(),
                                resultLinkList.get(i).toString(), resultImageList.get(i).toString());

                        productAdapter.addItem(item);
                    }
                    productAdapter.notifyDataSetChanged();
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