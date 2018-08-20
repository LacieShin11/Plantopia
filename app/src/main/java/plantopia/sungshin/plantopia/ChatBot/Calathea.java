package plantopia.sungshin.plantopia.ChatBot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.http.ServiceCallback;

import java.text.SimpleDateFormat;
import java.util.Date;

import plantopia.sungshin.plantopia.R;

public class Calathea extends AppCompatActivity {

    private static final String TAG = "CHATBOTTUTORIAL";
    private ChatBotDBAdapter chatBotDbHelper; // db 관련 객체
    public static final String PLANT_NAME = "CALATHEA";
    public static final String PLANT_NICKNAME = "칼라"; //임의로 지정한 유저 이름
    ListView m_ListView;
    ChatbotAdapter m_Adapter;
    String formatTime;
    String formatDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_chat);

        //액션바에 식물 애칭 넣기
        Intent intent = getIntent();
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

                    final String timeText = "\n\n" + formatTime;
                    inputText2 += timeText;

                    chatBotDbHelper.insertColumn(PLANT_NAME, PLANT_NICKNAME, inputText2, 1, formatDate, formatTime); //db에 넣기
                    userInput.setText("");
                    m_Adapter.notifyDataSetChanged();
                    if(!chatBotDbHelper.isEmpty(PLANT_NAME)) setListItem(); //해당 plant와 관련된 내용이 db에 있으면 그 plant와의 대화내용 다 띄우기

                    MessageRequest request = new MessageRequest.Builder().inputText(inputText).build();

                    myConversationService.message(getString(R.string.calathea_workspace), request).enqueue(new ServiceCallback<MessageResponse>() {
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

                                    chatBotDbHelper.insertColumn(PLANT_NAME, PLANT_NICKNAME, outputText, 0, formatDate, formatTime); //db에 넣기
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

        m_Adapter.notifyDataSetChanged();
    } //대화 목록 띄워주는 함수
}