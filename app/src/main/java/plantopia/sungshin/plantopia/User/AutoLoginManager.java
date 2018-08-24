package plantopia.sungshin.plantopia.User;

import android.content.Context;
import android.content.SharedPreferences;

public class AutoLoginManager {
    private static final String SHARED_PREF_NAME = "plantopia_sharedpref";
    private static final String KEY_NAME = "keyname";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_IMG = "keyimg";
    private static final String KEY_ID = "keyid";
    private static final String KEY_POT_COUNT = "keycountpot";
    private static final String KEY_DIARY_COUNT = "keycountdiary";
    private static final String KEY_SCRAP_COUNT = "keycountscrap";

    private static AutoLoginManager mInstance;
    private static Context mContext;

    public AutoLoginManager(Context context) {
        mContext = context;
    }

    public static synchronized AutoLoginManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AutoLoginManager(context);
        }
        return mInstance;
    }

    //shared preferences에 유저 정보를 저장하는 메소드
    public void userLogin(UserData user) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getUser_id());
        editor.putString(KEY_NAME, user.getUser_name());
        editor.putString(KEY_EMAIL, user.getUser_email());
        editor.putString(KEY_IMG, user.getUser_img());
        editor.apply();
    }

    public void setUserName(String name) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_NAME, name);
        editor.apply();
    }

    public void setUserCount(int count1, int count2, int count3) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_POT_COUNT, count1);
        editor.putInt(KEY_DIARY_COUNT, count2);
        editor.putInt(KEY_SCRAP_COUNT, count3);

        editor.apply();
    }

    public void setUserImg(String imgPath) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_IMG, imgPath);
        editor.apply();
    }

    //로그인 여부를 확인하는 메소드
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAME, null) != null;
    }

    //로그인한 유저 정보 제공
    public UserData getUser() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new UserData(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_NAME, null),
                sharedPreferences.getString(KEY_IMG, null),
                sharedPreferences.getInt(KEY_POT_COUNT, 0),
                sharedPreferences.getInt(KEY_DIARY_COUNT, 0),
                sharedPreferences.getInt(KEY_SCRAP_COUNT, 0)
        );
    }

    //로그아웃 메소드
    public void logout() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        //Toast.makeText(mContext, R.string.need_login, Toast.LENGTH_SHORT).show();
        //mContext.startActivity(new Intent(mContext, SignInActivity.class)); //다시 로그인 창으로 이동
    }
}