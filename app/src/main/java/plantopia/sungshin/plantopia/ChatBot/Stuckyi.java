package plantopia.sungshin.plantopia.ChatBot;

import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.http.ServiceCallback;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import plantopia.sungshin.plantopia.R;

public class Stuckyi extends AppCompatActivity {

    private static final String TAG = "CHATBOTTUTORIAL";
    private ChatBotDBAdapter chatBotDbHelper; // db 관련 객체
    public static final String PLANT_NAME = "STUCKYI";
    public static final String PLANT_NICKNAME = "스투키"; //임의로 지정한 유저 이름
    ListView m_ListView;
    ChatbotAdapter m_Adapter;
    String formatTime;
    String formatDate;
    Map context;
    Double Temp, Light, Humidity; //아두이노로부터 받아온 현재 식물 정보

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_chat);

        //네트워크 연결 유무 확인
        NetworkInfo mNetworkState = getNetworkInfo();

        if(mNetworkState == null || !mNetworkState.isConnected()){
            Toast.makeText(getApplicationContext(), "네트워크 연결 상태를 확인해주세요", Toast.LENGTH_LONG).show();
        }//네트워크 끊김-Toast 메시지

        //액션바에 식물 애칭 넣기
        Intent intent = getIntent();
        Temp = intent.getDoubleExtra("Temp", 30);
        Light = intent.getDoubleExtra("Light", 3);
        Humidity = intent.getDoubleExtra("Humidity", 300);
        //아두이노에서 현재 값 받아오기

        // 툴바 생성
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // 툴바에 별명 작성
        getSupportActionBar().setTitle(intent.getStringExtra("plantName"));
        toolbar.setTitleTextColor(Color.WHITE);
        //toolbar.setTitleText
        // 툴바에 홈버튼 활성화
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // 툴바의 홈버튼 이미지 변경
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.arrow_back);
        //액션바에 식물 애칭 넣기
        //Intent intent = getIntent();
        setTitle(intent.getStringExtra("plantName"));

        // 어댑터 생성
        m_Adapter = new ChatbotAdapter();

        // xml에서 추가한 ListView 연결
        m_ListView = (ListView) findViewById(R.id.listView1);

        // ListView에 어댑터 연결
        m_ListView.setAdapter(m_Adapter);

        final ConversationService myConversationService =
                new ConversationService(
                        "2018-07-10",
                        getString(R.string.username),
                        getString(R.string.password));

        final TextView conversation = (TextView)findViewById(R.id.conversation);
        final EditText userInput = (EditText)findViewById(R.id.user_input);

        //db, listView 어댑터
        chatBotDbHelper = new ChatBotDBAdapter(getApplicationContext());
        chatBotDbHelper.open();

        m_Adapter.notifyDataSetChanged();

        userInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE) {

                    // 현재 시간 구하기
                    long now = System.currentTimeMillis();
                    // 현재 시간을 date 변수에 저장
                    Date date = new Date(now);
                    // 시간을 나타낼 포맷 정하기
                    SimpleDateFormat sdfNow = new SimpleDateFormat("aa HH:mm");
                    // 날짜를 나타낼 포맷 정하기
                    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy년 MM월 dd일");
                    // nowDate 변수에 값을 저장
                    formatTime = sdfNow.format(date);
                    formatDate = sdfDate.format(date);
                    final String inputText = userInput.getText().toString();
                    String inputText2 = userInput.getText().toString();

                    //**컨텍스트 넣기-여기에 넣어야지만 맨 처음에 갑작스럽게 습도, 온도, 빛 정보 물어봐도 식물의 현재 상태 정보 알려줌
                    if(context == null){
                        context = new HashMap<String, Object>();
                    }

                    //context로 아두이노로부터 받은 실시간 정보 넣기
                    context.put("Temp",Temp);
                    context.put("Light", Light);
                    context.put("Humidity", Humidity);

                    final MessageRequest request = new MessageRequest.Builder().inputText(inputText).context(context).build();

                    final String timeText = "\n\n" + formatTime;
                    inputText2 += timeText;

                    if(chatBotDbHelper.isEmpty(PLANT_NAME) || chatBotDbHelper.isCheckDatelog(formatDate)) {
                        chatBotDbHelper.insertColumn(PLANT_NAME, PLANT_NICKNAME, formatDate, 2, formatDate, formatTime); //db에 넣기
                        chatBotDbHelper.insertColumn(PLANT_NAME, PLANT_NICKNAME, inputText2, 1, formatDate, formatTime); //db에 넣기
                        //첫 대화 시 날짜 띄우기(해당 디비 내역이 비어있을 시, 그리고 db에 그 날짜에 대화한 목록이 없을 때 날짜 띄우기(db에 내용은 있는데)
                    }else {
                        chatBotDbHelper.insertColumn(PLANT_NAME, PLANT_NICKNAME, inputText2, 1, formatDate, formatTime); //db에 넣기
                    }
                    //chatBotDbHelper.insertColumn(PLANT_NAME, PLANT_NICKNAME, inputText2, 1, formatDate, formatTime); //db에 넣기
                    userInput.setText("");
                    m_Adapter.notifyDataSetChanged();
                    if(!chatBotDbHelper.isEmpty(PLANT_NAME)) setListItem(); //해당 plant와 관련된 내용이 db에 있으면 그 plant와의 대화내용 다 띄우기

                    myConversationService.message(getString(R.string.stuckyi_workspace), request).enqueue(new ServiceCallback<MessageResponse>() {
                        @Override
                        public void onResponse(MessageResponse response) {
                            // 현재 시간 구하기
                            long now = System.currentTimeMillis();
                            // 현재 시간을 date 변수에 저장
                            Date date = new Date(now);
                            // 시간을 나타낼 포맷 정하기
                            SimpleDateFormat sdfNow = new SimpleDateFormat("aa HH:mm");
                            // 날짜를 나타낼 포맷 정하기
                            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy년 MM월 dd일");
                            // nowDate 변수에 값을 저장
                            formatTime = sdfNow.format(date);
                            formatDate = sdfDate.format(date);
                            String timeText = "\n\n" + formatTime;
                            final String outputText = response.getText().get(0) + timeText;//이 위치 맞음

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    if(chatBotDbHelper.isEmpty(PLANT_NAME) || chatBotDbHelper.isCheckDatelog(formatDate)) {
                                        chatBotDbHelper.insertColumn(PLANT_NAME, PLANT_NICKNAME, formatDate, 2, formatDate, formatTime); //db에 넣기
                                        chatBotDbHelper.insertColumn(PLANT_NAME, PLANT_NICKNAME, outputText+context, 0, formatDate, formatTime); //db에 넣기
                                        //첫 대화 시 날짜 띄우기(해당 디비 내역이 비어있을 시, 그리고 db에 그 날짜에 대화한 목록이 없을 때 날짜 띄우기(db에 내용은 있는데)
                                    }else {
                                        chatBotDbHelper.insertColumn(PLANT_NAME, PLANT_NICKNAME, outputText+context, 0, formatDate, formatTime); //db에 넣기
                                    }
                                    //chatBotDbHelper.insertColumn(PLANT_NAME, PLANT_NICKNAME, outputText, 0, formatDate, formatTime); //db에 넣기
                                    m_Adapter.notifyDataSetChanged();
                                    if(!chatBotDbHelper.isEmpty(PLANT_NAME)) setListItem(); //해당 plant와 관련된 내용이 db에 있으면 그 plant와의 대화내용 다 띄우기

                                }
                            });
                        }


                        @Override
                        public void onFailure(Exception e) {
                            Log.d(TAG, e.getMessage());
                        }
                    });
                }
                return false;
            }
        });

        m_Adapter.notifyDataSetChanged();
        if(!chatBotDbHelper.isEmpty(PLANT_NAME)) setListItem();
        //앱 껐다 켜도 db저장된 거 나올 수 있도록
    }

    public void setListItem() {
        m_Adapter.clear();

        String[] talks = chatBotDbHelper.displayTalking(PLANT_NAME); //대화내용
        String[] times = chatBotDbHelper.displayTime(PLANT_NAME); //시간
        Integer[] types = chatBotDbHelper.displayType(PLANT_NAME); // 타입
        String[] dates = chatBotDbHelper.displayDate(PLANT_NAME); // 날짜
        //adapter를 통한 값 전달

        for (int i = 0; i < talks.length; i++) {
            m_Adapter.add(talks[i], types[i], dates[i], times[i]);
        }

        m_ListView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        m_ListView.setSelection(m_Adapter.getCount()-1);//맨 밑 띄워주기

        m_Adapter.notifyDataSetChanged();
    } //대화 목록 띄워주는 함수

    public NetworkInfo getNetworkInfo(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo;
    }// 현재 네트워크 상태를 반환

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // some doing
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}