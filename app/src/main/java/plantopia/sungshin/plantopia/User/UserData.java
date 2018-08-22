package plantopia.sungshin.plantopia.User;

public class UserData {
    private boolean result;
    private String msg;
    private int user_id, count_pot, count_diary, count_scrap;
    private String user_email, user_name, user_pwd;
    private String user_img;

    public UserData() {
        this.count_pot = 0;
        this.count_diary = 0;
        this.count_scrap = 0;
    }

    public UserData(String user_email, String user_pwd) {
        this.user_email = user_email;
        this.user_pwd = user_pwd;
        this.count_pot = 0;
        this.count_diary = 0;
        this.count_scrap = 0;
    }

    public UserData(int user_id, String user_email, String user_name, String user_img) {
        this.user_id = user_id;
        this.user_email = user_email;
        this.user_name = user_name;
        this.user_img = user_img;
    }

    public UserData(boolean result, String msg, int user_id, String user_email, String user_name) {
        this.result = result;
        this.msg = msg;
        this.user_id = user_id;
        this.user_email = user_email;
        this.user_name = user_name;
    }

    public UserData(int user_id, String user_email, String user_name, String user_img, int count_pot, int count_diary, int count_scrap) {
        this.user_id = user_id;
        this.count_pot = count_pot;
        this.count_diary = count_diary;
        this.count_scrap = count_scrap;
        this.user_email = user_email;
        this.user_name = user_name;
        this.user_img = user_img;
    }

    public UserData(boolean result, String msg, int user_id, String user_email, String user_name, String user_pwd, String user_img) {
        this.result = result;
        this.msg = msg;
        this.user_id = user_id;
        this.user_email = user_email;
        this.user_name = user_name;
        this.user_pwd = user_pwd;
        this.user_img = user_img;
    }

    public boolean isResult() {
        return result;
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

    public int getCount_pot() {
        return count_pot;
    }

    public void setCount_pot(int count_pot) {
        this.count_pot = count_pot;
    }

    public int getCount_diary() {
        return count_diary;
    }

    public void setCount_diary(int count_diary) {
        this.count_diary = count_diary;
    }

    public int getCount_scrap() {
        return count_scrap;
    }

    public void setCount_scrap(int count_scrap) {
        this.count_scrap = count_scrap;
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
}
