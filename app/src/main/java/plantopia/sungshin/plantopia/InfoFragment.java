package plantopia.sungshin.plantopia;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import plantopia.sungshin.plantopia.User.ApplicationController;
import plantopia.sungshin.plantopia.User.AutoLoginManager;
import plantopia.sungshin.plantopia.User.Count;
import plantopia.sungshin.plantopia.User.ServerURL;
import plantopia.sungshin.plantopia.User.ServiceApiForUser;
import plantopia.sungshin.plantopia.User.SignInActivity;
import plantopia.sungshin.plantopia.User.UserData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoFragment extends android.support.v4.app.Fragment {
    static final int SETTING = 1;
    private static final int LOGIN = 2;
    private static final int ADD_PLANT = 3;
    private static final int LOGOUT = 4;
    private static final int LOGIN_SUCCESS = 5;

    private Menu menu;
    private Unbinder unbinder;
    Activity activity;
    Context context;
    ServiceApiForUser service;

    @BindView(R.id.layout_need_login)
    LinearLayout needLoginLayout;
    @BindView(R.id.layout_login)
    LinearLayout loginLayout;
    @BindView(R.id.login_btn)
    Button loginBtn;
    @BindView(R.id.profile_img)
    CircleImageView profileImg;

    @BindView(android.R.id.tabhost)
    TabHost tabHost;
    @BindView(R.id.setting_btn)
    ImageButton settingBtn;
    @BindView(R.id.id_text)
    TextView idText;

    @BindView(R.id.save_count_text)
    TextView saveCountText;
    @BindView(R.id.diary_count_text)
    TextView diaryCountText;
    @BindView(R.id.flowerpot_count_text)
    TextView flowerpotCountText;
    @BindView(R.id.progressbar)
    ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab5, container, false);
        unbinder = ButterKnife.bind(this, view);
        UserData user = AutoLoginManager.getInstance(context).getUser();

        //자동로그인이 되어있지 않을 경우 로그인 창으로 이동
        Log.d("로그인 여부", AutoLoginManager.getInstance(context).isLoggedIn() + "");
        //서버 연결
        ApplicationController applicationController = ApplicationController.getInstance();
        applicationController.buildService(ServerURL.URL, 3000);
        service = ApplicationController.getInstance().getService();

        flowerpotCountText.setText(user.getCount_pot() + "");
        diaryCountText.setText(user.getCount_diary() + "");
        saveCountText.setText(user.getCount_scrap() + "");

        setLoginLayout(AutoLoginManager.getInstance(context).isLoggedIn());

        LocalActivityManager mLocalActivityManager = new LocalActivityManager(getActivity(), false);
        mLocalActivityManager.dispatchCreate(savedInstanceState);
        tabHost.setup(mLocalActivityManager);

        setupTab(new TextView(context), "식물");
        setupTab(new TextView(context), "다이어리");
        setupTab(new TextView(context), "스크랩");
        tabHost.setCurrentTab(0);

        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProfileSettingActivity.class);
                startActivityForResult(intent, SETTING);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(context, SignInActivity.class), LOGIN);
            }
        });

        return view;
    }

    public void setCount() {
        UserData user = AutoLoginManager.getInstance(context).getUser();

        Call<Count> countCall = service.getCount(user.getUser_id());
        countCall.enqueue(new Callback<Count>() {
            @Override
            public void onResponse(Call<Count> call, Response<Count> response) {
                if (response.isSuccessful()) {
                    flowerpotCountText.setText(String.valueOf(response.body().getPotCount()));
                    diaryCountText.setText(String.valueOf(response.body().getDiaryCount()));
                    saveCountText.setText(String.valueOf(response.body().getScrapCount()));

                    AutoLoginManager.getInstance(context).setUserCount(
                            response.body().getPotCount(),
                            response.body().getDiaryCount(),
                            response.body().getScrapCount()
                    );
                }
            }

            @Override
            public void onFailure(Call<Count> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(activity, R.string.name_error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        setLoginLayout(AutoLoginManager.getInstance(context).isLoggedIn());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_tab, menu);
        this.menu = menu;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                if (AutoLoginManager.getInstance(context).isLoggedIn())
                    startActivityForResult(new Intent(context, AddPlantActivity.class), ADD_PLANT);
                else
                    Toast.makeText(context, getString(R.string.need_login), Toast.LENGTH_SHORT).show();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    // 탭 위젯 설정 및 추가
    public void setupTab(final View view, final String tag) {
        View tabview = createTabView(tabHost.getContext(), tag);

        TabHost.TabSpec ts = tabHost.newTabSpec(tag).setIndicator(tabview);

        switch (tag) {
            case "식물":
                ts.setContent(new Intent(getContext(), InfoTab1Activity.class));
                break;
            case "다이어리":
                ts.setContent(new Intent(getContext(), InfoTab2Activity.class));
                break;
            case "스크랩":
                ts.setContent(new Intent(getContext(), InfoTab3Activity.class));
                break;
        }

        tabHost.addTab(ts);
    }

    //탭 위젯에 뷰 구성
    public static View createTabView(final Context context, final String text) {
        View view = LayoutInflater.from(context).inflate(R.layout.info_tabwidget, null);
        ImageView img = view.findViewById(R.id.info_tab_icon);

        switch (text) {
            case "식물":
                img.setImageResource(R.drawable.plant);
                break;

            case "다이어리":
                img.setImageResource(R.drawable.diary);
                break;

            case "스크랩":
                img.setImageResource(R.drawable.save);
                break;
        }

        return view;
    }

    //로그인여부에 따라 레이아웃 변경
    private void setLoginLayout(boolean isLoggined) {
        progressBar.setVisibility(View.VISIBLE);

        if (isLoggined) {
            needLoginLayout.setVisibility(View.GONE);
            loginLayout.setVisibility(View.VISIBLE);

            UserData user = AutoLoginManager.getInstance(context).getUser();
            idText.setText(user.getUser_name()); //닉네임 설정http://examplebucket.s3-website-us-west-2.amazonaws.com/photo.jpg

            if (user.getUser_img() == null) //프로필 사진 설정
                profileImg.setImageResource(R.drawable.add_profile_images_02);
            else {
                Glide.with(context).load(user.getUser_img()).into(profileImg);
            }

            setCount();
        } else {
            needLoginLayout.setVisibility(View.VISIBLE);
            loginLayout.setVisibility(View.GONE);
        }

        progressBar.setVisibility(View.INVISIBLE);
    }

}
