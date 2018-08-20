package plantopia.sungshin.plantopia.ChatBot;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import static plantopia.sungshin.plantopia.ChatBot.ChatBotDB.CreateDB.CONTENT;
import static plantopia.sungshin.plantopia.ChatBot.ChatBotDB.CreateDB.MESSAGETYPE;
import static plantopia.sungshin.plantopia.ChatBot.ChatBotDB.CreateDB.PLANT;
import static plantopia.sungshin.plantopia.ChatBot.ChatBotDB.CreateDB.PLANT_NICK;
import static plantopia.sungshin.plantopia.ChatBot.ChatBotDB.CreateDB.TIME;
import static plantopia.sungshin.plantopia.ChatBot.ChatBotDB.CreateDB._TABLENAME;

public class ChatBotDBAdapter {
    private static final String DATABASE_NAME = "chatbot.db";
    private static final int DATABASE_VERSION =1;
    public static SQLiteDatabase mDB;
    public static SQLiteDatabase rDB;
    private DatabaseHelper mDBHelper;
    public static ArrayList<String> list;
    private Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper {//상속받아 재정의, db생성 테이블 생성
        //생성자
        public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        //최초 DB를 만들 때 한 번만 호출된다.
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(ChatBotDB.CreateDB._CREATE); //테이블 생성
        }

        //버전이 업데이트 되었을 경우 DB를 다시 만들어 준다.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+ ChatBotDB.CreateDB._TABLENAME);
            onCreate(db);
        }//초기화할 때 호출한다고 함. 테이블을 삭제하고 새로 생성하는 역할
    }

    public ChatBotDBAdapter(Context context) {
        this.mCtx = context;
    }

    public ChatBotDBAdapter open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();//DB를 쓰기버전으로 데려옴
        rDB = mDBHelper.getReadableDatabase();//DB를 읽기버전으로 데려옴
        return this;
    }

    public void close() {
        mDB.close();
    }//DB닫기

    public boolean isEmpty(String plant){
        int i=0;
        Cursor c = mDB.rawQuery("Select * from CHATBOTTABLE where PLANT='" + plant + "';", null);

        String talking[] = new String[c.getCount()];

        while(c.moveToNext()){
            talking[i] = c.getString(c.getColumnIndex("content"));
            i++;
        }
        if(talking.length == 0) return true;
        else return false;
    }//db비었는지 여부

    public long insertColumn(String plant, String plant_nick, String content, Integer messagetype, String date, String time) {
        ContentValues values = new ContentValues();

        values.put(PLANT, plant);
        values.put(PLANT_NICK, plant_nick);
        values.put(CONTENT, content);
        values.put(MESSAGETYPE, messagetype);
        values.put(ChatBotDB.CreateDB.DATE, date);
        values.put(TIME, time);

        return mDB.insert(_TABLENAME, null, values);
    } //DB에 내용 추가

    public String[] displayTalking(String plant) {
        int i=0;
        Cursor c = mDB.rawQuery("Select * from CHATBOTTABLE where PLANT='" + plant + "';", null);
        String[] talking = new String[c.getCount()];

        while(c.moveToNext()){
            talking[i] = (c.getString(c.getColumnIndex("content")));
            i++;
        } //커서 객체 이용해서 테이블의 talking 내용 가져오기
        return talking;
    }

    public Integer[] displayType(String plant) {
        int i=0;
        Cursor c = mDB.rawQuery("Select * from CHATBOTTABLE where PLANT='" + plant + "';", null);
        Integer[] type = new Integer[c.getCount()];

        while(c.moveToNext()){
            type[i] = (c.getInt(c.getColumnIndex("messagetype")));
            i++;
        } //커서 객체 이용해서 테이블의 talking 내용 가져오기
        return type;
    }

    public String[] displayTime(String plant){
        int i=0;
        Cursor c = mDB.rawQuery("Select * from CHATBOTTABLE where PLANT='" + plant + "';", null);
        String[] times = new String[c.getCount()];

        while(c.moveToNext()){
            times[i] = (c.getString(c.getColumnIndex("time")));
            i++;
        } //커서 객체 이용해서 테이블의 talking 내용 가져오기
        return times;
    }

    public String[] displayDate(String plant){
        int i=0;
        Cursor c = mDB.rawQuery("Select * from CHATBOTTABLE where PLANT='" + plant + "';", null);
        String[] dates = new String[c.getCount()];

        while(c.moveToNext()){
            dates[i] = (c.getString(c.getColumnIndex("date")));
            i++;
        } //커서 객체 이용해서 테이블의 talking 내용 가져오기
        return dates;
    }

    public Cursor getTable() {
        Cursor res = rDB.rawQuery("select * from CHATBOTTABLE", null);
        return res;
    }//테이블 내용 전체 가져오기

    public void deleteTable() { mDB.delete("CHATBOTTABLE", null, null); } //DB테이블 삭제
}
