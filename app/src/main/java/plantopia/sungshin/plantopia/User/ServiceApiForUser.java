package plantopia.sungshin.plantopia.User;

import java.util.List;

import plantopia.sungshin.plantopia.Diray.DiaryItem;
import plantopia.sungshin.plantopia.PlantItem;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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
    @POST("/user/updateImg")
    Call<UserData> updateUserImg(@Body UserData updateData);

    // 아이디 중복 확인
    @POST("/user/emailCheck")
    Call<UserData> checkDuplication(@Body UserData userEmail);

    // 식물 정보 받아오기
    @GET("/plant/get/{user_id}")
    Call<List<PlantItem>> getPlant(@Path("user_id") String userId);

    // 식물 정보 보내기

    //푸시 알림보내기
    @POST("/user/push")
    Call<UserData> pushAlert(@Body UserData user);

    // 푸시 알림 체크
    @POST("/user/pushCheck")
    Call<UserData> checkToken(@Body UserData userToken);
    @POST("/plant/add")
    Call<UserData> checkDuplication(@Body PlantItem plantItem);

    // 다이어리 추가하기
    @POST("/diary/add")
    Call<UserData> addDiary(@Body DiaryItem diaryItem);

    @GET("/diary/get/{user_id}")
    Call<List<DiaryItem>> getDiary(@Path("user_id") int userId);
}