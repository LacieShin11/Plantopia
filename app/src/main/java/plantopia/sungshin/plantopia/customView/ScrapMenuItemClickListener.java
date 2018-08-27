package plantopia.sungshin.plantopia.customView;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.widget.Toast;

import plantopia.sungshin.plantopia.Diray.ScrapItem;
import plantopia.sungshin.plantopia.R;
import plantopia.sungshin.plantopia.User.ApplicationController;
import plantopia.sungshin.plantopia.User.AutoLoginManager;
import plantopia.sungshin.plantopia.User.ServerURL;
import plantopia.sungshin.plantopia.User.ServiceApiForUser;
import plantopia.sungshin.plantopia.User.UserData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScrapMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
    private int position, owner_id, scrap_id;
    private String url, title, source, image;
    ServiceApiForUser service;
    Context mContext;

    public ScrapMenuItemClickListener(int owner_id, int positon, String url, String title, String source, String image, Context context) {
        this.owner_id = owner_id;
        this.position = positon;
        this.url = url;
        this.title = title;
        this.source = source;
        this.image = image;
        this.mContext = context;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.menu_scrap:
                //서버 연결
                ApplicationController applicationController = ApplicationController.getInstance();
                applicationController.buildService(ServerURL.URL, 3000);
                service = ApplicationController.getInstance().getService();

                final UserData userData = AutoLoginManager.getInstance(mContext).getUser();

                final ScrapItem scrapItem = new ScrapItem(owner_id, title, source, image, url);
                Call<UserData> call = service.addScrap(scrapItem);
                call.enqueue(new Callback<UserData>() {
                    @Override
                    public void onResponse(Call<UserData> call, Response<UserData> response) {
                        Toast.makeText(mContext, "스크랩 완료", Toast.LENGTH_SHORT).show();
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