package plantopia.sungshin.plantopia;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetService {
    //retrofit
    @GET ("/test/{id}")
    Call<List<UserData>> listDummies (@Path("id") String position);
}