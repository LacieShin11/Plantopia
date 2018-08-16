package plantopia.sungshin.plantopia.User;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApplicationController extends Application {
    private static ApplicationController instance;
    public static ApplicationController getInstance() {
        return instance;
    }
    private ServiceApiForUser service;
    public ServiceApiForUser getService() {
        return service;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationController.instance = this;
    }

    private String baseUrl;

    public void buildService(String ip, int port) {
        synchronized (ApplicationController.class) {
            if (service == null) {
                baseUrl = String.format("http://%s:%d", ip, port);
                Gson gson = new GsonBuilder().create();

                GsonConverterFactory factory = GsonConverterFactory.create(gson);

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .addConverterFactory(factory)
                        .build();

                service = retrofit.create(ServiceApiForUser.class);
            }
        }
    }

}
