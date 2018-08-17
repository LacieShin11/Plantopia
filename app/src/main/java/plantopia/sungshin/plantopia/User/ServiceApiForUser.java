package plantopia.sungshin.plantopia.User;

import plantopia.sungshin.plantopia.User.UserData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ServiceApiForUser {

    //로그인
//    @FormUrlEncoded
    @POST("/login")
//    Call<LoginResult> getLoginResult(@Field("user_email") String user_email, @Field("user_pwd") String user_pwd);
    Call<LoginResult> getLoginResult(@Body LoginResult loginResult);

    //회원가입
    @FormUrlEncoded
    @POST("/join")
    Call<JoinResult> getJoinResult(@Field("user_email") String userEmail, @Field("user_pwd") String userPwd);

    // 아이디 중복 확인

    // 식물 정보 받아오기

    // 식물 정보 보내기
}
