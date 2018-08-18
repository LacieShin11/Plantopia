package plantopia.sungshin.plantopia.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ServiceApiForUser {

    //로그인
    @POST("/login")
    Call<UserData> getLoginResult(@Body UserData loginResult);

    //회원가입
    @POST("/join")
    Call<UserData> getJoinResult(@Body UserData joinResult);

    //닉네임 수정
    @POST("/editName")
    Call<UserData> setUserName(@Body UserData editResult);

    //프로필 이미지 수정

    // 아이디 중복 확인
    @FormUrlEncoded
    @POST("/emailCheck")
    Call<UserData> checkDuplication(@Field("user_email") String userEmail);

    // 식물 정보 받아오기

    // 식물 정보 보내기
}
