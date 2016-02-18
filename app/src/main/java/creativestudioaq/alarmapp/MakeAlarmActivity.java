package creativestudioaq.alarmapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.rey.material.widget.Slider;
import com.rey.material.widget.Switch;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by honggyu on 2016-01-31.
 */

public class MakeAlarmActivity extends Activity implements View.OnClickListener {

    public Alarm alarm;
    private Slider volumeSlider;
    private String time1, time2;
    private CountDownTimer alarmToneTimer;
    private MediaPlayer mediaPlayer;
    private TextView alarmsound, firsttalk, secondtalk, repeatnumber, repeatminute;

    private final int REPEAT_REQUEST_CODE = 100;
    private int[] numlist = {1, 2, 3, 5, 10};
    private int[] minuitelist = {3, 5, 10, 15, 30};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makealarm);

        putTimeString();
        setLayout();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }


    public void putTimeString(){
        Intent intentget = getIntent();

        if(intentget.getSerializableExtra("alarm") == null){

            time1 = intentget.getStringExtra("time");

            alarm = new Alarm();
            alarm.setAlarmTime(time1);

        }else{
            setMathAlarm((Alarm) intentget.getSerializableExtra("alarm"));
            time1 = alarm.getAlarmTimeString();
        }

        SimpleDateFormat dateFormat1 = new SimpleDateFormat("aa KK:mm", java.util.Locale.getDefault());
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm", java.util.Locale.getDefault());

        Date date = new Date();

        try{
            date = dateFormat2.parse(time1);
        }catch (Exception e){
            e.printStackTrace();
        }

        time2 = dateFormat1.format(date);
    }



    public void setLayout(){
        Button cancelbutton = (Button) findViewById(R.id.cancelbutton);
        Button savebutton = (Button) findViewById(R.id.savebutton);
        repeatnumber = (TextView) findViewById(R.id.repeatnumber);
        repeatminute = (TextView) findViewById(R.id.repeatminute);
        alarmsound = (TextView) findViewById(R.id.alarmsound);
        TextView selecttime = (TextView) findViewById(R.id.selecttime);
        firsttalk = (TextView) findViewById(R.id.firsttalk);
        secondtalk = (TextView) findViewById(R.id.secondtalk);
        RelativeLayout toneLayout = (RelativeLayout) findViewById(R.id.toneLayout);
        RelativeLayout repeatLayout = (RelativeLayout) findViewById(R.id.repeatLayout);
        //TextView selectday = (TextView) findViewById(R.id.selectday);
        volumeSlider = (Slider) findViewById(R.id.alarmvolume);
        Switch vibrateSwitch = (Switch) findViewById(R.id.notibutton);
        Switch repeatSwitch = (Switch) findViewById(R.id.repeatbutton);
        Switch feelingSwitch = (Switch) findViewById(R.id.feelbutton);


        cancelbutton.setText("< 시간 설정");
        savebutton.setText("저장");

        //selecttime.setText(time2);
        //selectday.setText("월 목");

        alarmsound.setText(RingtoneManager.getRingtone(this, Uri.parse(alarm.getAlarmTonePath())).getTitle(this));

        final SpannableStringBuilder sps = new SpannableStringBuilder(time2);
        sps.setSpan(new AbsoluteSizeSpan(50), 0, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        selecttime.append(sps);

        firsttalk.setText(alarm.getRabbitFeeling());
        secondtalk.setText(alarm.getMyFeeling());
        vibrateSwitch.setChecked(alarm.getVibrate());
        repeatSwitch.setChecked(alarm.getRepeatUse());
        feelingSwitch.setChecked(alarm.getFeelingOk());
        volumeSlider.setValue(alarm.getVolume(), true);
        repeatminute.setText(alarm.getRepeatMinute() + "분");
        repeatnumber.setText(alarm.getRepeatNum() + "회");


        cancelbutton.setOnClickListener(this);
        savebutton.setOnClickListener(this);
        //repeatnumber.setOnClickListener(this);
        //alarmsound.setOnClickListener(this);
        firsttalk.setOnClickListener(this);
        secondtalk.setOnClickListener(this);
        repeatLayout.setOnClickListener(this);
        toneLayout.setOnClickListener(this);
        vibrateSwitch.setOnCheckedChangeListener(switchCheckListener);
        repeatSwitch.setOnCheckedChangeListener(switchCheckListener);
        feelingSwitch.setOnCheckedChangeListener(switchCheckListener);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancelbutton:
                finish();
                break;
            case R.id.savebutton:
                alarmSave();
                break;
           /* case R.id.feeltalk:
                Intent intent1 = new Intent(MakeAlarmActivity.this,FeelTalkActivity.class);
                startActivity(intent1);
                break;*/
            case R.id.firsttalk:
                writeFeeling(0);
                break;
            case R.id.secondtalk:
                writeFeeling(1);
                break;
            case R.id.repeatLayout:
                Intent intent2 = new Intent(MakeAlarmActivity.this, RepeatNumberActivity.class);
                intent2.putExtra("minute", whichPositionInList(minuitelist, alarm.getRepeatMinute()));
                intent2.putExtra("num", whichPositionInList(numlist, alarm.getRepeatNum()));
                startActivityForResult(intent2, REPEAT_REQUEST_CODE);
                break;
            case R.id.toneLayout:
                AlarmToneSelect();
                break;
        }
    }

    public void daySelect(View v){
        switch (v.getId()){
            case R.id.sunday:
                //alarm.addDay(Alarm.Day.SUNDAY);
                alarm.removeDay(Alarm.Day.SUNDAY);
                break;
            case R.id.monday:
                //alarm.addDay(Alarm.Day.MONDAY);
                break;
            case R.id.tuesday:
                //alarm.addDay(Alarm.Day.TUESDAY);
                break;
            case R.id.wednesday:
                //alarm.addDay(Alarm.Day.WEDNESDAY);
                break;
            case R.id.thursday:
                //alarm.addDay(Alarm.Day.THURSDAY);
                break;
            case R.id.friday:
                //alarm.addDay(Alarm.Day.FRIDAY);
                break;
            case R.id.saturday:
                //alarm.addDay(Alarm.Day.SATURDAY);
                alarm.removeDay(Alarm.Day.SATURDAY);
                break;
        }
    }


    public Alarm getMathAlarm() {
        return alarm;
    }

    public void setMathAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case REPEAT_REQUEST_CODE:
                Log.i("Repeat", "Repeat Activity Finish");

                if(resultCode == RESULT_OK){

                    int num = data.getIntExtra("num", 0);
                    int minute = data.getIntExtra("minute", 0);

                    alarm.setRepeatNum(numlist[num]);
                    alarm.setRepeatMinute(minuitelist[minute]);

                    repeatminute.setText(alarm.getRepeatMinute() + "분");
                    repeatnumber.setText(alarm.getRepeatNum() + "회");
                }
                break;
        }
    }

    public void alarmSave(){

        alarm.setVolume(volumeSlider.getValue());

        Database.init(getApplicationContext());
        if (getMathAlarm().getId() < 1) {
            Database.create(getMathAlarm());
        } else {
            Database.update(getMathAlarm());
        }
        callMathAlarmScheduleService();
        Toast.makeText(this, getMathAlarm().getTimeUntilNextAlarmMessage(), Toast.LENGTH_LONG).show();
        finish();
    }

    protected void callMathAlarmScheduleService() {
        Intent mathAlarmServiceIntent = new Intent(this, AlarmServiceBroadcastReciever.class);
        sendBroadcast(mathAlarmServiceIntent, null);
    }

    public Switch.OnCheckedChangeListener switchCheckListener = new Switch.OnCheckedChangeListener(){
        @Override
        public void onCheckedChanged(Switch view, boolean checked) {
            switch (view.getId()){
                case R.id.notibutton:
                    alarm.setVibrate(checked);
                    if(checked){
                        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
                        vibrator.vibrate(1000);
                    }
                    break;
                case R.id.feelbutton:
                    alarm.setFeelingOk(checked);
                    break;
                case R.id.repeatbutton:
                    alarm.setRepeatUse(checked);
                    break;
            }
        }
    };


    public void AlarmToneSelect(){
        AlertDialog.Builder alert = new AlertDialog.Builder(MakeAlarmActivity.this);
        alert.setTitle("Test");

        RingtoneManager ringtoneMgr = new RingtoneManager(this);
        ringtoneMgr.setType(RingtoneManager.TYPE_ALARM);
        Cursor alarmsCursor = ringtoneMgr.getCursor();

        final String[] alarmTones = new String[alarmsCursor.getCount()+1];
        alarmTones[0] = "Silent";
        final String[] alarmTonePaths = new String[alarmsCursor.getCount()+1];
        alarmTonePaths[0] = "";

        if (alarmsCursor.moveToFirst()) {
            do {
                alarmTones[alarmsCursor.getPosition()+1] = ringtoneMgr.getRingtone(alarmsCursor.getPosition()).getTitle(this);
                alarmTonePaths[alarmsCursor.getPosition()+1] = ringtoneMgr.getRingtoneUri(alarmsCursor.getPosition()).toString();
            }while(alarmsCursor.moveToNext());
        }

        CharSequence[] items = new CharSequence[alarmsCursor.getCount()+1];
        for (int i = 0; i < items.length; i++)
            items[i] = alarmTones[i];

        alert.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alarm.setAlarmTonePath(alarmTonePaths[which]);
                alarmsound.setText(alarmTones[which]);
                if (alarm.getAlarmTonePath() != null) {
                    if (mediaPlayer == null) {
                        mediaPlayer = new MediaPlayer();
                    } else {
                        if (mediaPlayer.isPlaying())
                            mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                    try {
                        mediaPlayer.setVolume(0.2f, 0.2f);
                        mediaPlayer.setDataSource(MakeAlarmActivity.this, Uri.parse(alarm.getAlarmTonePath()));
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_ALARM);
                        mediaPlayer.setLooping(false);
                        mediaPlayer.prepare();
                        mediaPlayer.start();

                        if (alarmToneTimer != null)
                            alarmToneTimer.cancel();
                        alarmToneTimer = new CountDownTimer(3000, 3000) {
                            @Override
                            public void onTick(long millisUntilFinished) {

                            }

                            @Override
                            public void onFinish() {
                                try {
                                    if (mediaPlayer.isPlaying())
                                        mediaPlayer.stop();
                                } catch (Exception e) {

                                }
                            }
                        };
                        alarmToneTimer.start();
                    } catch (Exception e) {
                        try {
                            if (mediaPlayer.isPlaying())
                                mediaPlayer.stop();
                        } catch (Exception e2) {

                        }
                    }
                }
            }
        });

        alert.show();
    }


    public void writeFeeling(final int sep){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("대화 설정");
        alert.setMessage("대화 내용을 입력해주세요.");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        input.setFilters(new InputFilter[] { new InputFilter.LengthFilter(20) });

        if(sep == 0)
            input.setText(alarm.getRabbitFeeling());
        else
            input.setText(alarm.getMyFeeling());

        input.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                return true;
            }
        });

        alert.setView(input);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String value = input.getText().toString();

                if (sep == 0) {
                    alarm.setRabbitFeeling(value);
                    firsttalk.setText(value);
                } else {
                    alarm.setMyFeeling(value);
                    secondtalk.setText(value);
                }

            }
        });


        alert.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

        alert.show();
    }

    public int whichPositionInList(int[] list, int v){

        int i;

        for(i = 0; i < list.length; i++){
            if( list[i] == v )
                break;
        }

        return i;
    }


}






