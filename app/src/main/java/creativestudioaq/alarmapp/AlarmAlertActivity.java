package creativestudioaq.alarmapp;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;


public class AlarmAlertActivity extends Activity {

    private Alarm alarm;
    private MediaPlayer mediaPlayer;

    private StringBuilder answerBuilder = new StringBuilder();


    private Game game;
    private Vibrator vibrator;

    private boolean alarmActive;

    private TextView problemView;
    private EditText answerView;
    private String answerString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        setContentView(R.layout.alarm_alert);

        Bundle bundle = this.getIntent().getExtras();
        alarm = (Alarm) bundle.getSerializable("alarm");


        long now = System.currentTimeMillis();

        Date date = new Date(now);

        SimpleDateFormat sdfNow = new SimpleDateFormat("HH:mm");
        String strNow = sdfNow.format(date);

        TextView time = (TextView) findViewById(R.id.time);
        TextView mention = (TextView) findViewById(R.id.mention);
        TextView gamequestion = (TextView) findViewById(R.id.gamequestion);

        time.setText(strNow);
        mention.setText("시계토끼와 대화하면\n알람이 꺼져요.");
        gamequestion.setText("오늘 나는 행복해.\n오늘 나는 행복해.");


        //게임 띄우기

        game = new Game();


        problemView = (TextView) findViewById(R.id.gamequestion);
        problemView.setText(game.getAnswer());

        answerView = (EditText) findViewById(R.id.gameanswer);


        final Button gamebutton = (Button) findViewById(R.id.gamebutton);
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void afterTextChanged(Editable edit) {
                String s = edit.toString();
                if (s.length() > 0)
                    gamebutton.setBackgroundResource(R.drawable.redrectangle);
                else
                    gamebutton.setBackgroundResource(R.drawable.pinkrectangle);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        };
        answerView.addTextChangedListener(textWatcher);

        gamebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (game.getAnswer().equals(answerView.getText().toString())) {

                    alarmActive = false;
                    if (vibrator != null)
                        vibrator.cancel();
                    try {
                        mediaPlayer.stop();
                    } catch (IllegalStateException ise) {

                    }
                    try {
                        mediaPlayer.release();
                    } catch (Exception e) {

                    }
                    finish();


                } else {
                    answerView.setText(null);
                    answerView.setHint("다시 한번 써보세요");
                }
            }
        });

        TelephonyManager telephonyManager = (TelephonyManager) this
                .getSystemService(Context.TELEPHONY_SERVICE);

        PhoneStateListener phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING:
                        Log.d(getClass().getSimpleName(), "Incoming call: "
                                + incomingNumber);
                        try {
                            mediaPlayer.pause();
                        } catch (IllegalStateException e) {

                        }
                        break;
                    case TelephonyManager.CALL_STATE_IDLE:
                        Log.d(getClass().getSimpleName(), "Call State Idle");
                        try {
                            mediaPlayer.start();
                        } catch (IllegalStateException e) {

                        }
                        break;
                }
                super.onCallStateChanged(state, incomingNumber);
            }
        };

        telephonyManager.listen(phoneStateListener,
                PhoneStateListener.LISTEN_CALL_STATE);

        // Toast.makeText(this, answerString, Toast.LENGTH_LONG).show();

        startAlarm();
        mediaPlayer.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        alarmActive = true;
    }

    private void startAlarm() {

        if (alarm.getAlarmTonePath() != "") {
            mediaPlayer = new MediaPlayer();
            if (alarm.getVibrate()) {
                vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                long[] pattern = {1000, 200, 200, 200};
                vibrator.vibrate(pattern, 0);
            }
            try {
                mediaPlayer.setVolume(1.0f, 1.0f);
                mediaPlayer.setDataSource(this,
                        Uri.parse(alarm.getAlarmTonePath()));
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                mediaPlayer.setLooping(true);
                mediaPlayer.prepare();
                mediaPlayer.start();

            } catch (Exception e) {
                mediaPlayer.release();
                alarmActive = false;
            }
        }

    }

    @Override
    public void onBackPressed() {
        if (!alarmActive)
            super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        StaticWakeLock.lockOff(this);
    }

    @Override
    protected void onDestroy() {
        try {
            if (vibrator != null)
                vibrator.cancel();
        } catch (Exception e) {

        }
        try {
            mediaPlayer.stop();
        } catch (Exception e) {

        }
        try {
            mediaPlayer.release();
        } catch (Exception e) {

        }
        super.onDestroy();
    }
}