package plantopia.sungshin.plantopia;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import plantopia.sungshin.plantopia.User.AutoLoginManager;
import plantopia.sungshin.plantopia.User.UserData;
import plantopia.sungshin.plantopia.User.SignInActivity;

public class InfoFragment extends android.support.v4.app.Fragment {
    static final int SETTING = 1;
    private static final int LOGIN = 2;
    private static final int ADD_PLANT = 3;
    private static final int LOGOUT = 4;
    private static final int LOGIN_SUCCESS = 5;

    private Unbinder unbinder;
    Activity activity;
    Context context;

    @BindView(android.R.id.tabhost)
    TabHost tabHost;
    @BindView(R.id.setting_btn)
    ImageButton settingBtn;
    @BindView(R.id.id_text)
    TextView idText;

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

        //자동로그인이 되어있지 않을 경우 로그인 창으로 이동
        if (!AutoLoginManager.getInstance(context).isLoggedIn()) {
            Toast.makeText(context, "로그인이 필요한 기능입니다.", Toast.LENGTH_SHORT).show();
            startActivityForResult(new Intent(context, SignInActivity.class), LOGIN);
        } else {
            UserData user = AutoLoginManager.getInstance(context).getUser();
            idText.setText(user.getUser_name());
        }

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

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        idText.setText(AutoLoginManager.getInstance(context).getUser().getUser_name());
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add:
                startActivityForResult(new Intent(context, AddPlantActivity.class), ADD_PLANT);
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
}
