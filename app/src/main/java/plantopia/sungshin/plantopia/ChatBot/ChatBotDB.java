package plantopia.sungshin.plantopia.ChatBot;

import android.provider.BaseColumns;

//db설계
public class ChatBotDB {
    public static final class CreateDB implements BaseColumns {
        public static final String ID = "id";
        public static final String PLANT = "plant"; //식물종류
        public static final String PLANT_NICK = "plant_nick"; //식물 닉네임
        public static final String CONTENT = "content"; //대화내용
        public static final String MESSAGETYPE = "messagetype"; // 메시지 구분을 위한 타입
        public static final String DATE = "date"; //날짜,시간 정보
        public static final String TIME = "time"; //날짜,시간 정보
        public static final String _TABLENAME = "CHATBOTTABLE";

        public static final String _CREATE =
                "create Table " + _TABLENAME + "(" + ID + " Integer primary key autoincrement, "
                        + PLANT + " string not null , "
                        + PLANT_NICK + " string not null , "
                        + CONTENT + " string not null , "
                        + MESSAGETYPE + " interger not null , "
                        + DATE + " string not null , "
                        + TIME + " string not null);";
    }
}
