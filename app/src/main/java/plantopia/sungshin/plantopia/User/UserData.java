package plantopia.sungshin.plantopia.User;

import java.sql.Blob;

public class UserData {
    private boolean result;
    private String msg;
    private int user_id;
    private String user_email, user_name, user_pwd;
    private String user_img;

    public UserData(String user_email, String user_pwd) {
        this.user_email = user_email;
        this.user_pwd = user_pwd;
    }

    public UserData(int user_id, String user_email, String user_name) {
        this.user_id = user_id;
        this.user_email = user_email;
        this.user_name = user_name;
    }

    public UserData(boolean result, String msg, int user_id, String user_email, String user_name) {
        this.result = result;
        this.msg = msg;
        this.user_id = user_id;
        this.user_email = user_email;
        this.user_name = user_name;
    }

    public boolean isResult() {
        return result;
    }

    public String getUser_pwd() {
        return user_pwd;
    }

    public void setUser_pwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public String getUser_img() {
        return user_img;
    }

    public void setUser_img(String user_img) {
        this.user_img = user_img;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
