package creativestudioaq.alarmapp.ui_page;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimerTask;

import creativestudioaq.alarmapp.R;
import creativestudioaq.alarmapp.data.Alarm;
import creativestudioaq.alarmapp.data.Database;
import creativestudioaq.alarmapp.data.DatabaseSimple;
import creativestudioaq.alarmapp.tool.AlarmServiceBroadcastReciever;
import creativestudioaq.alarmapp.tool.StaticWakeLock;
import creativestudioaq.alarmapp.ui_element.FlipAnimation;


public class AlarmAlertActivity2 extends Activity {

    private TimerTask second;
    private TextView timer_text;
    private final Handler handler = new Handler();
    private Alarm alarm;
    private MediaPlayer mediaPlayer;

    private StringBuilder answerBuilder = new StringBuilder();
    private Vibrator vibrator;

    private boolean alarmActive;
    private TextView tv_cardname, bottomtext, time, mention;
    private Button pause, repeat;
    private int randomNum;
    private Random random;
    private String[] cardNameList, cardTextList;
    private LinearLayout bottomlayout;
    private boolean isRepeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        setContentView(R.layout.alarm_alert2);

        Bundle bundle = this.getIntent().getExtras();
        alarm = (Alarm) bundle.getSerializable("alarm");
        random = new Random();
        cardNameList = getResources().getStringArray(R.array.card_name);
        cardTextList = getResources().getStringArray(R.array.card_text);
        isRepeat = false;

        long now = System.currentTimeMillis();

        Date date = new Date(now);

        SimpleDateFormat sdfNow = new SimpleDateFormat("HH:mm");
        String strNow = sdfNow.format(date);


        time = (TextView) findViewById(R.id.time);
        mention = (TextView) findViewById(R.id.mention);
        pause = (Button) findViewById(R.id.pausebutton);
        repeat = (Button) findViewById(R.id.repeatbutton);
        tv_cardname = (TextView) findViewById(R.id.cardname);
        bottomtext = (TextView) findViewById(R.id.bottomtext);
        bottomlayout = (LinearLayout)findViewById(R.id.bottomlayout);

        bottomlayout.setVisibility(View.INVISIBLE);
        time.setText(strNow);
        mention.setText("오늘의 카드를 확인하면\n알람이 꺼져요.");

        View rootLayout = (View) findViewById(R.id.main_activity_card_face);
        View backLayout = (View) findViewById(R.id.main_activity_card_back);

        rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                flipCard();
                time.setVisibility(View.INVISIBLE);
                mention.setVisibility(View.INVISIBLE);
                bottomtext.setText(cardTextList[randomNum]);
                bottomlayout.setVisibility(View.VISIBLE);
                bottomtext.setVisibility(View.VISIBLE);

                String cardName = cardNameList[randomNum];
                final SpannableString sp = new SpannableString(cardName);
                if(randomNum != 4)
                    sp.setSpan(new RelativeSizeSpan(1.5f), 5, cardName.length(), 0);
                else
                    sp.setSpan(new RelativeSizeSpan(1.5f), 0, cardName.length(), 0);
                tv_cardname.setText(sp);

            }
        });

        backLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                finish();
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


        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmSave();
                finish();
            }
        });
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
                float volume = alarm.getVolume() * 0.01f;
                mediaPlayer.setVolume(volume, volume);
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
    protected void onStop() {
        super.onStop();

        if( !isRepeat )
            removeSimpleAlarm();
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

    private void flipCard() {
        View rootLayout = (View) findViewById(R.id.main_activity_root);
        View cardFace = (View) findViewById(R.id.main_activity_card_face);
        View cardBack = (View) findViewById(R.id.main_activity_card_back);

        TypedArray imgArray = getResources().obtainTypedArray(R.array.random_card);
        randomNum = random.nextInt(imgArray.length());
        cardBack.setBackgroundResource(imgArray.getResourceId(randomNum, -1));

        FlipAnimation flipAnimation = new FlipAnimation(cardFace, cardBack);

        if (cardFace.getVisibility() == View.GONE) {
            flipAnimation.reverse();
        }
        rootLayout.startAnimation(flipAnimation);
    }

    public void alarmSave() {
        isRepeat = true;

        if(alarm.getSimple()){
            DatabaseSimple.init(getApplicationContext());
            Calendar calendar = Calendar.getInstance();
            calendar.add(calendar.MINUTE, + alarm.getRepeatMinute());
            alarm.setAlarmTime(calendar);
            DatabaseSimple.update(alarm);
        }else {
            Database.init(getApplicationContext());
            if (alarm.getId() < 1) {
                Calendar calendar = Calendar.getInstance();
                calendar.add(calendar.MINUTE, +alarm.getRepeatMinute());
                alarm.setAlarmTime(calendar);
                Database.create(alarm);
            } else {
                Calendar calendar = Calendar.getInstance();
                calendar.add(calendar.MINUTE, +alarm.getRepeatMinute());
                alarm.setAlarmTime(calendar);
                Database.update(alarm);
            }
        }
        callMathAlarmScheduleService();
        Toast.makeText(this, alarm.getTimeUntilNextAlarmMessage(), Toast.LENGTH_LONG).show();
        finish();

    }


    protected void callMathAlarmScheduleService() {
        Intent mathAlarmServiceIntent = new Intent(this, AlarmServiceBroadcastReciever.class);
        sendBroadcast(mathAlarmServiceIntent, null);
    }


    public void removeSimpleAlarm(){
        if(alarm.getSimple()){
            DatabaseSimple.init(getApplicationContext());
            DatabaseSimple.deleteEntry(alarm);

            Intent mathAlarmServiceIntent = new Intent(getApplicationContext(), AlarmServiceBroadcastReciever.class);
            getApplicationContext().sendBroadcast(mathAlarmServiceIntent);
        }
    }
}