package plantopia.sungshin.plantopia.ChatBot;

public class ListContents {
    String msg;
    Integer type;
    String time;
    String date;
    ListContents(String _msg, Integer _type, String _date, String _time) {
        this.msg = _msg;
        this.type = _type;
        this.time = _time;
        this.date = _date;
    }

    public Integer getType() {
        return type;
    }

    public String getMsg() {
        return msg;
    }

    public String getTime() {return time;}

    public String getDate() {return date;}
}
