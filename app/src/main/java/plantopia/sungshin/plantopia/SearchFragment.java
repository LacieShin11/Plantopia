package plantopia.sungshin.plantopia;

import android.content.Intent;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SearchFragment extends android.support.v4.app.Fragment {
    private Unbinder unbinder;

    @BindView(R.id.search_list)
    ListView searchListView;

    SearchListAdapter adapter;
    ArrayList<String> plantNameList = new ArrayList<>(); // 식물 이름
    ArrayList<String> plantNumberList = new ArrayList<>(); // 식물 번호
    // ArrayList<String> plantList = new ArrayList<>(); // 식물 사진
    String key = "20180814WAQFXYCPVL972GCN79KFQ";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GetPlantDataTask ayncTask = new GetPlantDataTask();
        ayncTask.execute();
        Log.d("ayncTask.execute() : ", "완료");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab2, container, false);
        unbinder = ButterKnife.bind(this, view);

        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = plantNameList.get(position);
                String number = plantNumberList.get(position);

                Intent intent = new Intent(getContext(), DetailActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("number", number);

                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private class GetPlantDataTask extends AsyncTask<String, Void, HttpResponseCache>
    {
        @Override
        protected HttpResponseCache doInBackground(String... strings) {
            HttpResponseCache response = null;
            final String apiurl = "http://api.nongsaro.go.kr/service/garden/gardenList";
            HttpURLConnection conn = null;
            try {
                StringBuffer sb = new StringBuffer(3);
                sb.append(apiurl);
                sb.append("?apiKey=" + key);
                sb.append("&numOfRows=100");

                String query = sb.toString();
                URL url = new URL(query);
                conn = (HttpURLConnection) url.openConnection();
                DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                byte[] bytes = new byte[4096];
                InputStream in = conn.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while(true)
                {
                    int red = in.read(bytes);
                    if(red < 0) break;
                    baos.write(bytes, 0, red);

                }
                String xmlData = baos.toString("utf-8");
                baos.close();
                in.close();
                conn.disconnect();
                Document doc = docBuilder.parse(new InputSource(new StringReader(xmlData)));
                Element el = (Element) doc.getElementsByTagName("items").item(0);
                for (int i = 0; i < ((Node) el).getChildNodes().getLength(); i++) {
                    Node node = ((Node) el).getChildNodes().item(i);
                    if (!node.getNodeName().equals("item")) {
                        continue;
                    }
                    String plantNum = node.getChildNodes().item(0).getFirstChild().getNodeValue();
                    String plantName = node.getChildNodes().item(1).getFirstChild().getNodeValue();
                    // String plant = node.getChildNodes().item(9).getFirstChild().getNodeValue();
                    Log.d("식물 번호 : ", plantNum);
                    Log.d("식물 이름 : ", plantName);
                    plantNumberList.add(plantNum);
                    plantNameList.add(plantName);
                    // plantList.add(plant);
                }
                publishProgress();

            } catch(Exception e)
            {
                Log.i("검색탭", "doInBackground: 파싱 오류");
                e.printStackTrace();
            } finally {
                try {
                    if (conn != null) conn.disconnect();
                } catch (Exception e) {
                }
            }
            return response;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
            adapter = new SearchListAdapter(plantNameList, plantNumberList);
            searchListView.setAdapter(adapter);
            adapter.notifyDataSetChanged();

        }
    }
}
