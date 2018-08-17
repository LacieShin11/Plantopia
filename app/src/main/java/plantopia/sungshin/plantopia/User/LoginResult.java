package plantopia.sungshin.plantopia.User;

import java.sql.Blob;

public class LoginResult {
    private boolean result;
    private String msg;
    private int user_id;
    private String user_email, user_name, user_pwd;
    private Blob user_img;

    public LoginResult(String user_email, String user_pwd) {
        this.user_email = user_email;
        this.user_pwd = user_pwd;
    }

    public LoginResult(boolean result, String msg, int user_id, String user_email, String user_name) {
        this.result = result;
        this.msg = msg;
        this.user_id = user_id;
        this.user_email = user_email;
        this.user_name = user_name;
    }

    public boolean isResult() {
        return result;
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
