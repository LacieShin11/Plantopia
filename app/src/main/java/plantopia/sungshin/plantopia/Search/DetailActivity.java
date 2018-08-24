package plantopia.sungshin.plantopia.Search;

import android.content.Intent;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import plantopia.sungshin.plantopia.R;

public class DetailActivity extends AppCompatActivity {
    final String key = "20180814WAQFXYCPVL972GCN79KFQ";
    String plantName = "";
    String plantNum = "";
    String plantImage = "";

    TextView titleView2, textView1, textView2, textView3, textView4, textView5, textView6, textView7,
            textView8, textView9, textView10, textView11;
    ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        plantName = intent.getStringExtra("name");
        plantNum = intent.getStringExtra("number");
        plantImage = intent.getStringExtra("image");

        setTitle(plantName);

        titleView2 = findViewById(R.id.title2);
        imageView = findViewById(R.id.plant_image);
        textView1 = findViewById(R.id.text1);
        textView2 = findViewById(R.id.text2);
        textView3 = findViewById(R.id.text3);
        textView4 = findViewById(R.id.text4);
        textView5 = findViewById(R.id.text5);
        textView6 = findViewById(R.id.text6);
        textView7 = findViewById(R.id.text7);
        textView8 = findViewById(R.id.text8);
        textView9 = findViewById(R.id.text9);
        textView10 = findViewById(R.id.text10);
        textView11 = findViewById(R.id.text11);

        titleView2.setText(plantName);
        Glide.with(getApplicationContext()).load(plantImage).into(imageView);

        GetPlantDetailTask ayncTask = new GetPlantDetailTask();
        ayncTask.execute();
    }

    private class GetPlantDetailTask extends AsyncTask<String, Void, HttpResponseCache> {
        String[] detail;
        int length;

        @Override
        protected HttpResponseCache doInBackground(String... strings) {
            HttpResponseCache response = null;
            final String apiurl = "http://api.nongsaro.go.kr/service/garden/gardenDtl";
            HttpURLConnection conn = null;
            try {
                StringBuffer sb = new StringBuffer(3);
                sb.append(apiurl);
                sb.append("?apiKey=" + key);
                sb.append("&cntntsNo=" + plantNum);

                String query = sb.toString();
                Log.d("query", query);
                URL url = new URL(query);
                conn = (HttpURLConnection) url.openConnection();
                DocumentBuilder docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
                byte[] bytes = new byte[4096];
                InputStream in = conn.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                while (true) {
                    int red = in.read(bytes);
                    if (red < 0) break;
                    baos.write(bytes, 0, red);
                }

                String xmlData = baos.toString("utf-8");
                baos.close();
                in.close();
                conn.disconnect();
                Document doc = docBuilder.parse(new InputSource(new StringReader(xmlData)));
                Element el = (Element) doc.getElementsByTagName("item").item(0);

                length = ((Node) el).getChildNodes().getLength();
                detail = new String[length];

                for (int i = 0; i < length; i++) {
                    Node node = ((Node) el).getChildNodes().item(i);
                    if (node.hasChildNodes()) {
                        Log.d("getTextContent()", node.getFirstChild().getNodeValue());
                        detail[i] = (String) node.getFirstChild().getNodeValue();
                    } else {
                        detail[i] = "정보 없음";
                    }
                }
                publishProgress();

            } catch (Exception e) {
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

            for (int i = 0; i < length; i++) {
                if (detail[i].equals("079001"))
                    detail[i] = "강함";
                else if (detail[i].equals("079002"))
                    detail[i] = "중간";
                else if (detail[i].equals("079003"))
                    detail[i] = "약함";
                else if (detail[i].equals("079004"))
                    detail[i] = "거의 없음";
                else if (detail[i].equals("089001"))
                    detail[i] = "초보자";
                else if (detail[i].equals("089002"))
                    detail[i] = "경험자";
                else if (detail[i].equals("089003"))
                    detail[i] = "전문가";
                else if (detail[i].equals("090001"))
                    detail[i] = "빠름";
                else if (detail[i].equals("090002"))
                    detail[i] = "보통";
                else if (detail[i].equals("090003"))
                    detail[i] = "느림";
                else if (detail[i].equals("082001"))
                    detail[i] = "10~15도";
                else if (detail[i].equals("082002"))
                    detail[i] = "16~20도";
                else if (detail[i].equals("082002"))
                    detail[i] = "16~20도";
                else if (detail[i].equals("082003"))
                    detail[i] = "21~25도";
                else if (detail[i].equals("082004"))
                    detail[i] = "26~30도";
                else if (detail[i].equals("057001"))
                    detail[i] = "0도 이하";
                else if (detail[i].equals("057002"))
                    detail[i] = "5도";
                else if (detail[i].equals("057003"))
                    detail[i] = "7도";
                else if (detail[i].equals("057004"))
                    detail[i] = "10도";
                else if (detail[i].equals("057005"))
                    detail[i] = "13도 이상";
                else if (detail[i].equals("083001"))
                    detail[i] = "40% 미만";
                else if (detail[i].equals("083002"))
                    detail[i] = "40% ~ 70%";
                else if (detail[i].equals("083003"))
                    detail[i] = "70% 이상";
                else if (detail[i].equals("053001"))
                    detail[i] = "항상 흙을 축축하게 유지함(물에 잠김)";
                else if (detail[i].equals("053002"))
                    detail[i] = "흙을 촉촉하게 유지함(물에 잠기지 않도록 주의)";
                else if (detail[i].equals("053003"))
                    detail[i] = "토양 표면이 말랐을때 충분히 관수함";
                else if (detail[i].equals("053004"))
                    detail[i] = "화분 흙 대부분 말랐을때 충분히 관수함";
                else if (detail[i].equals("058001"))
                    detail[i] = "낮음 (잘 견딤)";
                else if (detail[i].equals("058002"))
                    detail[i] = "보통 (약간 잘 견딤)";
                else if (detail[i].equals("058003"))
                    detail[i] = "필요함";
                else if (detail[i].equals("058004"))
                    detail[i] = "특별 관리 요구";
                else if (detail[i].equals("058005"))
                    detail[i] = "기타";
            }

            textView1.setText("<조언 정보>\n" + detail[0]);
            textView2.setText("<비료 정보>\n" + detail[10]);
            textView3.setText("<생육 온도>\n" + detail[13]);
            textView4.setText("<겨울 최저 온도>\n" + detail[47]);
            textView5.setText("<생장속도>\n" + detail[14]);
            textView6.setText("<습도>\n" + detail[15]);
            textView7.setText("<관리요구도>\n" + detail[22]);
            textView8.setText("<물주기 봄>\n" + detail[40]);
            textView9.setText("<물주기 여름>\n" + detail[41]);
            textView10.setText("<물주기 가을>\n" + detail[42]);
            textView11.setText("<물주기 겨울>\n" + detail[43]);
        }
    }
}
