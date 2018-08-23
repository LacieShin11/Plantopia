package plantopia.sungshin.plantopia;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.net.http.HttpResponseCache;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.Toast;

import com.joooonho.SelectableRoundedImageView;
import com.soundcloud.android.crop.Crop;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import plantopia.sungshin.plantopia.Search.SearchListAdapter;
import plantopia.sungshin.plantopia.User.ApplicationController;
import plantopia.sungshin.plantopia.User.ServerURL;
import plantopia.sungshin.plantopia.User.ServiceApiForUser;

public class AddPlantActivity extends AppCompatActivity {
    private static final int TAKING_PIC = 10;
    private static final String BUCKET_NAME = "plantopiabucket";

    private ServiceApiForUser service;
    Uri plantUri = null;
    PlantItem newPlant = new PlantItem();
    @BindView(R.id.plant_img)
    SelectableRoundedImageView plantImg;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;
    @BindView(R.id.plant_edit_layout)
    TextInputLayout plantEditLayout;
    @BindView(R.id.plant_edit)
    TextInputEditText plantEdit;
    @BindView(R.id.search_plant_btn)
    ImageButton searchPlantBtn;
    @BindView(R.id.connect_switch)
    Switch connectSwitch;
    @BindView(R.id.push_switch)
    Switch pushSwitch;
    @BindView(R.id.plant_type_edit)
    AutoCompleteTextView plantTypeEdit;

    static final String KEY = "20180814WAQFXYCPVL972GCN79KFQ";
    static final String IMG_PATH1 = "http://www.nongsaro.go.kr/cms_contents/301/";
    static final String IMG_PATH2 = "_MF_REPR_ATTACH_01_TMB.jpg";

    ArrayList<PlantItem> plants = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_plant);
        ButterKnife.bind(this);
        setTitle(getString(R.string.add_plant));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //서버 연결
        ApplicationController applicationController = ApplicationController.getInstance();
        applicationController.buildService(ServerURL.URL, 3000);
        service = ApplicationController.getInstance().getService();

        plantEditLayout.setCounterEnabled(true);
        plantEditLayout.setCounterMaxLength(12);

        getPlantDataTask ayncTask = new getPlantDataTask();
        ayncTask.execute();

        plantEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (plantEdit.getText().toString().length() > 12)
                    showMessage(getString(R.string.check_name3));
                else hideMessage();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        plantTypeEdit.setAdapter(new SearchListAdapter(plants));
    }

    private void showMessage(String msg) {
        plantEditLayout.setErrorEnabled(true);
        plantEditLayout.setError(msg);
    }

    private void hideMessage() {
        plantEditLayout.setErrorEnabled(false);
        plantEditLayout.setError(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_diary, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_submit) {
            if (plantUri == null)
                Toast.makeText(this, getString(R.string.select_img), Toast.LENGTH_SHORT).show();
            else if (TextUtils.isEmpty(plantEdit.getText()))
                showMessage(getString(R.string.check_name));
            else if (plantEdit.getText().toString().length() > 12)
                showMessage(getString(R.string.check_name3));
            else {
                hideMessage();
            }
        } else if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return true;
    }

    public void cameraBtnOnClicked(View view) {
        CharSequence[] list = {"사진 촬영", "갤러리 사진 선택"};
        AlertDialog.Builder builder = new AlertDialog.Builder(AddPlantActivity.this);
        builder.setTitle("식물 사진 선택")
                .setItems(list, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                            if (takePictureIntent.resolveActivity(getPackageManager()) != null)
                                startActivityForResult(takePictureIntent, TAKING_PIC);

                        } else if (which == 1) {
                            Crop.pickImage(AddPlantActivity.this);
                        }
                    }
                });

        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("경로", "resultCode : " + resultCode + " requestCode : " + requestCode);

        if (requestCode == TAKING_PIC && resultCode == RESULT_OK) {
            beginCrop(data.getData());
        } else if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
            beginCrop(data.getData());
        } else if (requestCode == Crop.REQUEST_CROP) {
            handleCrop(resultCode, data);
        }
    }

    private void beginCrop(Uri source) {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        Uri destination = Uri.fromFile(new File(getCacheDir(), timestamp));
        Crop.of(source, destination).withAspect(1, 1).start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode == RESULT_OK) {
            plantUri = Crop.getOutput(result);
            plantImg.setImageURI(plantUri);
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private class getPlantDataTask extends AsyncTask<String, Void, HttpResponseCache> {
        @Override
        protected HttpResponseCache doInBackground(String... strings) {

            HttpResponseCache response = null;
            final String apiurl = "http://api.nongsaro.go.kr/service/garden/gardenList";
            HttpURLConnection conn = null;
            try {
                StringBuffer sb = new StringBuffer(3);
                sb.append(apiurl);
                sb.append("?apiKey=" + KEY);
                sb.append("&numOfRows=300");

                String query = sb.toString();
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
                Element el = (Element) doc.getElementsByTagName("items").item(0);

                for (int i = 0; i < ((Node) el).getChildNodes().getLength(); i++) {
                    Node node = ((Node) el).getChildNodes().item(i);
                    if (!node.getNodeName().equals("item")) {
                        continue;
                    }
                     String plantNum = node.getChildNodes().item(0).getFirstChild().getNodeValue();
                    String plantName = node.getChildNodes().item(1).getFirstChild().getNodeValue();
                     String plantImgPath = IMG_PATH1 + plantNum + IMG_PATH2;

                    plants.add(new PlantItem(plantName, plantNum, plantImgPath));
                }
                publishProgress();

            } catch (Exception e) {
                Log.i("식물추가", "doInBackground: 파싱 오류");
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
            /*progressBar.setVisibility(View.VISIBLE);

            try {
                plantTypeEdit.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
            } catch (Exception e) {
                e.printStackTrace();
            }*/
        }
    }
}