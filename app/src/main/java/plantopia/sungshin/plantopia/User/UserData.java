package plantopia.sungshin.plantopia.User;

import org.jetbrains.annotations.Nullable;

import java.sql.Blob;
import java.util.Date;

public class UserData {
    private int user_id;
    private String user_email, user_name, user_pwd;
    private Date user_logtime; //마지막 접속시간
    private Blob user_img;

    public UserData(String user_email, String user_pwd) {
        this.user_email = user_email;
        this.user_pwd = user_pwd;
    }

    public UserData(int user_id, String user_email, String user_pwd) {
        this.user_id = user_id;
        this.user_email = user_email;
        this.user_pwd = user_pwd;
    }

    public UserData(int user_id, String user_email, String user_name, String user_pwd) {
        this.user_id = user_id;
        this.user_email = user_email;
        this.user_name = user_name;
        this.user_pwd = user_pwd;
    }

    public UserData(int user_id, String user_email, String user_name, String user_pwd, Date user_logtime, Blob user_img) {
        this.user_id = user_id;
        this.user_email = user_email;
        this.user_name = user_name;
        this.user_pwd = user_pwd;
        this.user_logtime = user_logtime;
        this.user_img = user_img;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public String getUserEmail() {
        return user_email;
    }

    public void setUserEmail(String user_email) {
        this.user_email = user_email;
    }

    public String getUserName() {
        return user_name;
    }

    public void setUserName(String user_name) {
        this.user_name = user_name;
    }

    public String getUserPwd() {
        return user_pwd;
    }

    public void setUserPwd(String user_pwd) {
        this.user_pwd = user_pwd;
    }

    public Date getUserLogtime() {
        return user_logtime;
    }

    public void setUserLogtime(Date user_logtime) {
        this.user_logtime = user_logtime;
    }

    public Blob getUserImg() {
        return user_img;
    }

    public void setUserImg(Blob user_img) {
        this.user_img = user_img;
    }
}
