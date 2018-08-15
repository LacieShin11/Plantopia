package plantopia.sungshin.plantopia;

import retrofit2.Call;
import retrofit2.http.POST;

public interface ServiceApiForUser {
    @POST("login")
    Call<UserData> userLogin();
}
