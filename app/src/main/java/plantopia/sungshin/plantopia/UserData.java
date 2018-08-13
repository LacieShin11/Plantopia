package plantopia.sungshin.plantopia;

import org.jetbrains.annotations.Nullable;

import java.util.Date;

public class UserData {
    private int userId;
    private String userEmail, userName, userPwd;
    private Date userLogtime; //마지막 접속시간

    public UserData() {
    }

    public UserData(int userId, String userEmail, String userName) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userName = userName;
    }

    public UserData(int userId, String userEmail, String userName, String userPwd, @Nullable Date userLogtime) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userPwd = userPwd;
        this.userLogtime = userLogtime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    public Date getUserLogtime() {
        return userLogtime;
    }

    public void setUserLogtime(Date userLogtime) {
        this.userLogtime = userLogtime;
    }
}
