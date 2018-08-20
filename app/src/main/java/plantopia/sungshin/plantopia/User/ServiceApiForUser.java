package plantopia.sungshin.plantopia.User;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ServiceApiForUser {
    //로그인
    @POST("/user/login")
    Call<UserData> getLoginResult(@Body UserData loginResult);

    //회원가입
    @POST("/user/join")
    Call<UserData> getJoinResult(@Body UserData joinResult);

    //닉네임 수정
    @POST("/user/updateName")
    Call<UserData> setUserName(@Body UserData editResult);

    //프로필 이미지 수정
    @Multipart
    @POST("/user/updateImg")
    Call<ResponseBody> updateUserImg(@Part("user_email") RequestBody userEmail, @Part MultipartBody.Part userImg);

    // 아이디 중복 확인
    @FormUrlEncoded
    @POST("/user/emailCheck")
    Call<UserData> checkDuplication(@Field("user_email") String userEmail);

    // 식물 정보 받아오기

    // 식물 정보 보내기
}
