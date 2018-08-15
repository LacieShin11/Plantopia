package plantopia.sungshin.plantopia.Music;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import plantopia.sungshin.plantopia.R;

public class MusicFragment extends android.support.v4.app.Fragment {
    static final int PLAYING = 0;
    static final int PAUSE = 1;
    static final int STOP = 2;
    Activity activity;

    private MediaPlayer mediaPlayer;
    private int playedMusic;
    private int musicState;
    private Unbinder unbinder;
    private MusicListAdapter adapter;
    private MusicListItem[] musicListItems = {
            new MusicListItem("Time Travel In the Dream", "by HyunKyung", "2:48"),
            new MusicListItem("Moonlight Dream", "by 현경이", "3:01"),
            new MusicListItem("Wish Upon a Shooting Star", "by 의정부의 딸", "2:45"),
            new MusicListItem("새벽별", "by Hope of Uijungbu", "4:04"),
            new MusicListItem("그림자와 빛", "by 갓.현.경", "5:00")
    };
    private int[] musicID =
            {R.raw.time_travel_in_the_dream, R.raw.moonlight_dream,
                    R.raw.wish_upon_a_shooting_star, R.raw.dawn_star, R.raw.shadow_and_light};

    @BindView(R.id.music_list)
    ListView musicList;
    @BindView(R.id.music_info_layout)
    RelativeLayout musicInfoLayout;
    @BindView(R.id.prev_music_btn)
    ImageButton prevBtn;
    @BindView(R.id.next_music_btn)
    ImageButton nextBtn;
    @BindView(R.id.music_speaker_btn)
    ImageButton musicSpeakerBtn;
    @BindView(R.id.music_stop_btn)
    ImageButton musicStopBtn;
    @BindView(R.id.music_state_btn)
    ImageButton musicStateBtn;
    @BindView(R.id.shadow)
    View shadow;
    @BindView(R.id.played_song_title)
    TextView musicTitleText;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if ((context instanceof Activity)) activity = (Activity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab4, container, false);

        unbinder = ButterKnife.bind(this, view);

        adapter = new MusicListAdapter();
        musicList.setAdapter(adapter);
        adapter.setItem(musicListItems);

        changeMusicLayout(false);

        musicList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (musicList.getVisibility() == View.INVISIBLE)
                    changeMusicLayout(true);

                if (mediaPlayer != null) {
                    mediaPlayer.release();
                }

                playedMusic = position;
                mediaPlayer = MediaPlayer.create(getContext(), musicID[position]);

                if (mediaPlayer != null) {
                    musicState = PLAYING;
                    mediaPlayer.start();
                    musicStateBtn.setImageResource(R.drawable.ic_pause_24dp);
                    musicTitleText.setText(adapter.getItem(position).getSongTitle());

                    changeMusicLayout(true);
                }

            }
        });

        ImageButton.OnClickListener imgBtnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.music_state_btn:
                        if (musicState == PAUSE) {
                            try {
                                mediaPlayer.start();
                                musicState = PLAYING;
                                musicStateBtn.setImageResource(R.drawable.ic_pause_24dp);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (musicState == PLAYING) {
                            mediaPlayer.pause();
                            musicState = PAUSE;
                            musicStateBtn.setImageResource(R.drawable.ic_play_arrow_24dp);
                        }

                        break;

                    case R.id.music_speaker_btn:
                        activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
                        new Thread(new Runnable() {
                            public void run() {
                                new Instrumentation()
                                        .sendKeyDownUpSync(KeyEvent.KEYCODE_VOLUME_UP);
                            }
                        }).start();

                        break;

                    case R.id.prev_music_btn:
                        changeMusic(-1);
                        break;

                    case R.id.next_music_btn:
                        changeMusic(1);
                        break;

                    case R.id.music_stop_btn:
                        musicState = STOP;
                        mediaPlayer.stop();
                        changeMusicLayout(false);

                        activity.setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE); //볼륨 조절시 일반볼륨 변경
                        break;
                }
            }
        };

        nextBtn.setOnClickListener(imgBtnClickListener);
        prevBtn.setOnClickListener(imgBtnClickListener);
        musicSpeakerBtn.setOnClickListener(imgBtnClickListener);
        musicStopBtn.setOnClickListener(imgBtnClickListener);
        musicStateBtn.setOnClickListener(imgBtnClickListener);

        return view;
    }

    //다음 곡 재생, 이전 곡 재생 함수
    public void changeMusic(int direction) {
        playedMusic += direction;

        if (playedMusic == -1) playedMusic = 4;
        if (playedMusic == 5) playedMusic = 0;

        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = MediaPlayer.create(getContext(), musicID[playedMusic]);

        if (musicState == PLAYING)
            mediaPlayer.start();

        musicTitleText.setText(adapter.getItem(playedMusic).getSongTitle());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();

        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void changeMusicLayout(boolean isVisible) {
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) musicList.getLayoutParams();

        if (!isVisible) {
            musicInfoLayout.setVisibility(View.GONE);
            shadow.setVisibility(View.GONE);

            params.topMargin = 180;
        } else {
            musicInfoLayout.setVisibility(View.VISIBLE);
            shadow.setVisibility(View.VISIBLE);
            params.topMargin = 120;
        }

        musicList.setLayoutParams(params);
    }
}
