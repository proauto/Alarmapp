package creativestudioaq.alarmapp.ui_page;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import creativestudioaq.alarmapp.R;
import creativestudioaq.alarmapp.data.Alarm;
import creativestudioaq.alarmapp.data.Database;
import creativestudioaq.alarmapp.tool.AlarmServiceBroadcastReciever;
import creativestudioaq.alarmapp.tool.StaticWakeLock;


public class AlarmAlertActivity extends Activity {

    private TimerTask second;
    private Alarm alarm;
    private MediaPlayer mediaPlayer;
    private Vibrator vibrator;

    private boolean alarmActive;
    private EditText answerView;
    String myfeeling;
    String [] myAnswer;


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

        myAnswer = getResources().getStringArray(R.array.answer_list);

        String rabbitfeeling = alarm.getRabbitFeeling();

        if(alarm.getMyFeeling().isEmpty()) {
            Random random = new Random();
            myfeeling = myAnswer[random.nextInt(myAnswer.length-1)];
        }else{
            myfeeling = alarm.getMyFeeling();
        }

        int repeatminute = alarm.getRepeatMinute();
        myAnswer = getResources().getStringArray(R.array.answer_list);

        long now = System.currentTimeMillis();

        Date date = new Date(now);

        SimpleDateFormat sdfNow = new SimpleDateFormat("HH:mm");
        String strNow = sdfNow.format(date);

        TextView time = (TextView) findViewById(R.id.time);
        TextView mention = (TextView) findViewById(R.id.mention);
        TextView gamequestion = (TextView) findViewById(R.id.gamequestion);
        TextView realanswer = (TextView) findViewById(R.id.realanswer);
        Button pause = (Button) findViewById(R.id.pausebutton);
        Button repeat = (Button) findViewById(R.id.repeatbutton);

        time.setText(strNow);
        mention.setText("시계토끼와 대화하면\n알람이 꺼져요.");
        gamequestion.setText(rabbitfeeling);
        realanswer.setText(myfeeling);


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
                if (myfeeling.equals(answerView.getText().toString())) {

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

//                   Toast.makeText(AlarmAlertActivity.this,"커스텀 토스트로 변경",Toast.LENGTH_LONG).show();

                    second = new TimerTask() {

                        @Override
                        public void run() {
                            Log.i("Test", "Timer start");
                            finish();
                        }
                    };
                    Timer timer = new Timer();
                    timer.schedule(second, 2000);


                } else {
                    answerView.setText(null);
                    answerView.setHint("다시 한번 써보세요!");
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

    public void alarmSave() {


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
        callMathAlarmScheduleService();
        Toast.makeText(this, alarm.getTimeUntilNextAlarmMessage(), Toast.LENGTH_LONG).show();
        finish();

    }


    protected void callMathAlarmScheduleService() {
        Intent mathAlarmServiceIntent = new Intent(this, AlarmServiceBroadcastReciever.class);
        sendBroadcast(mathAlarmServiceIntent, null);
    }

}