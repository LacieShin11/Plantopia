package plantopia.sungshin.plantopia;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

public class AutoLoginManager {
    private static final String SHARED_PREF_NAME = "plantopia_sharedpref";
    private static final String KEY_NAME = "keyname";
    private static final String KEY_EMAIL = "keyemail";
    //    private static final String KEY_PWD = "keypwd";
    private static final String KEY_ID = "keyid";

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
        editor.putInt(KEY_ID, user.getUserId());
        editor.putString(KEY_NAME, user.getUserName());
        editor.putString(KEY_EMAIL, user.getUserEmail());
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
                sharedPreferences.getString(KEY_NAME, null),
                sharedPreferences.getString(KEY_EMAIL, null)
        );
    }

    //로그아웃 메소드
    public void logout() {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Toast.makeText(mContext, "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
        mContext.startActivity(new Intent(mContext, SignInActivity.class)); //다시 로그인 창으로 이동
    }
}
