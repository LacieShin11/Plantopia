package plantopia.sungshin.plantopia.customView;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import plantopia.sungshin.plantopia.R;
import plantopia.sungshin.plantopia.User.ApplicationController;
import plantopia.sungshin.plantopia.User.AutoLoginManager;
import plantopia.sungshin.plantopia.User.ServerURL;
import plantopia.sungshin.plantopia.User.ServiceApiForUser;
import plantopia.sungshin.plantopia.User.UserData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScrapDeleteMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
    private int position, scrap_id;
    ServiceApiForUser service;
    Context mContext;

    public ScrapDeleteMenuItemClickListener(int scrap_id, Context context) {
        this.scrap_id = scrap_id;
        this.mContext = context;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_scrap_delete:
                //서버 연결
                ApplicationController applicationController = ApplicationController.getInstance();
                applicationController.buildService(ServerURL.URL, 3000);
                service = ApplicationController.getInstance().getService();

                final UserData userData = AutoLoginManager.getInstance(mContext).getUser();
                Call<UserData> call = service.deleteScrap(scrap_id);
                call.enqueue(new Callback<UserData>() {
                    @Override
                    public void onResponse(Call<UserData> call, Response<UserData> response) {
                        Toast.makeText(mContext, "스크랩 삭제", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<UserData> call, Throwable t) {
                        Toast.makeText(mContext, R.string.name_error, Toast.LENGTH_SHORT).show();
                    }
                });
                break;
        }
        return false;
    }
}
