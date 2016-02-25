package creativestudioaq.alarmapp.ui_page;

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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.rey.material.widget.Slider;
import com.rey.material.widget.Switch;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import creativestudioaq.alarmapp.tool.AlarmServiceBroadcastReciever;
import creativestudioaq.alarmapp.R;
import creativestudioaq.alarmapp.data.Alarm;
import creativestudioaq.alarmapp.data.Database;


/**
 * Created by honggyu on 2016-01-31.
 */

public class MakeAlarmActivity extends Activity implements View.OnClickListener {

    public Alarm alarm;
    private Slider volumeSlider;
    private String time1, time2;
    private CountDownTimer alarmToneTimer;
    private MediaPlayer mediaPlayer;
    private TextView alarmsound, firsttalk, secondtalk, repeatminute;
    private Random random;
    private String[] rabbitQuestion;

    private final int REPEAT_REQUEST_CODE = 100;
    private final int[] minuitelist = {3, 5, 10, 15, 30};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makealarm);

        random = new Random();
        rabbitQuestion = getResources().getStringArray(R.array.question_list);

        putTimeString();
        setLayout();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    public void putTimeString() {
        Intent intentget = getIntent();

        if (intentget.getSerializableExtra("alarm") == null) {

            time1 = intentget.getStringExtra("time");

            alarm = new Alarm();
            alarm.setAlarmTime(time1);
            alarm.setRabbitFeeling(rabbitQuestion[random.nextInt(rabbitQuestion.length-1)]);

        } else {
            setMathAlarm((Alarm) intentget.getSerializableExtra("alarm"));
            time1 = alarm.getAlarmTimeString();
        }

        SimpleDateFormat dateFormat1 = new SimpleDateFormat("aa KK시 mm분", java.util.Locale.getDefault());
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm", java.util.Locale.getDefault());

        Date date = new Date();

        try {
            date = dateFormat2.parse(time1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        time2 = dateFormat1.format(date);
    }


    public void setLayout() {
        Button cancelbutton = (Button) findViewById(R.id.cancelbutton);
        Button savebutton = (Button) findViewById(R.id.savebutton);
        repeatminute = (TextView) findViewById(R.id.repeatminute);
        alarmsound = (TextView) findViewById(R.id.alarmsound);
        TextView selecttime = (TextView) findViewById(R.id.selecttime);
        firsttalk = (TextView) findViewById(R.id.firsttalk);
        secondtalk = (TextView) findViewById(R.id.secondtalk);
        RelativeLayout toneLayout = (RelativeLayout) findViewById(R.id.toneLayout);
        RelativeLayout repeatLayout = (RelativeLayout) findViewById(R.id.repeatLayout);
        volumeSlider = (Slider) findViewById(R.id.alarmvolume);
        Switch vibrateSwitch = (Switch) findViewById(R.id.notibutton);
        Switch repeatSwitch = (Switch) findViewById(R.id.repeatbutton);
        Switch feelingSwitch = (Switch) findViewById(R.id.feelbutton);

        ToggleButton[] dayCheckArray = new ToggleButton[7];
        dayCheckArray[0] = (ToggleButton) findViewById(R.id.sunday);
        dayCheckArray[1] = (ToggleButton) findViewById(R.id.monday);
        dayCheckArray[2] = (ToggleButton) findViewById(R.id.tuesday);
        dayCheckArray[3] = (ToggleButton) findViewById(R.id.wednesday);
        dayCheckArray[4] = (ToggleButton) findViewById(R.id.thursday);
        dayCheckArray[5] = (ToggleButton) findViewById(R.id.friday);
        dayCheckArray[6] = (ToggleButton) findViewById(R.id.saturday);

        cancelbutton.setText("< 취소");
        savebutton.setText("저장");


        alarmsound.setText(RingtoneManager.getRingtone(this, Uri.parse(alarm.getAlarmTonePath())).getTitle(this));

        final SpannableStringBuilder sps = new SpannableStringBuilder(time2);
        sps.setSpan(new AbsoluteSizeSpan(50), 0, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        selecttime.append(sps);

        if(alarm.getRabbitFeeling().isEmpty()) {
            firsttalk.setText(rabbitQuestion[random.nextInt(rabbitQuestion.length-1)]);
        }else{
            firsttalk.setText(alarm.getRabbitFeeling());
        }

        if(alarm.getMyFeeling().isEmpty()) {
            secondtalk.setText("시계토끼의 말에 이곳을 클릭해서 대답해주세요.");
        }else{
            secondtalk.setText(alarm.getMyFeeling());
        }

        vibrateSwitch.setChecked(alarm.getVibrate());
        repeatSwitch.setChecked(alarm.getRepeatUse());
        feelingSwitch.setChecked(alarm.getFeelingOk());
        volumeSlider.setValue(alarm.getVolume(), true);
        repeatminute.setText(alarm.getRepeatMinute() + "분 마다");

        for (Alarm.Day day : alarm.getDays()) {
            dayCheckArray[day.ordinal()].setChecked(true);
        }


        cancelbutton.setOnClickListener(this);
        savebutton.setOnClickListener(this);
        firsttalk.setOnClickListener(this);
        secondtalk.setOnClickListener(this);
        repeatLayout.setOnClickListener(this);
        toneLayout.setOnClickListener(this);
        vibrateSwitch.setOnCheckedChangeListener(switchCheckListener);
        repeatSwitch.setOnCheckedChangeListener(switchCheckListener);
        feelingSwitch.setOnCheckedChangeListener(switchCheckListener);

        for (int i = 0; i < 7; i++) {
            dayCheckArray[i].setOnCheckedChangeListener(checkedChangeListener);
        }
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
            case R.id.firsttalk:
                alarm.setRabbitFeeling(rabbitQuestion[random.nextInt(rabbitQuestion.length-1)]);
                firsttalk.setText(alarm.getRabbitFeeling());
                break;
            case R.id.secondtalk:
                writeFeeling(1);
                break;
            case R.id.repeatLayout:
                repeatMinuteSelect();
                break;
            case R.id.toneLayout:
                AlarmToneSelect();
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
        switch (requestCode) {
            case REPEAT_REQUEST_CODE:
                Log.i("Repeat", "Repeat Activity Finish");

                if (resultCode == RESULT_OK) {
                    int minute = data.getIntExtra("minute", 0);
                    alarm.setRepeatMinute(minuitelist[minute]);

                    repeatminute.setText(alarm.getRepeatMinute() + "분");
                }
                break;
        }
    }

    public void alarmSave() {

        if (alarm.getDays().length < 1) {
            Toast.makeText(this, "요일을 선택해주세요.", Toast.LENGTH_SHORT).show();
        } else {
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
    }

    protected void callMathAlarmScheduleService() {
        Intent mathAlarmServiceIntent = new Intent(this, AlarmServiceBroadcastReciever.class);
        sendBroadcast(mathAlarmServiceIntent, null);
    }

    public Switch.OnCheckedChangeListener switchCheckListener = new Switch.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(Switch view, boolean checked) {
            switch (view.getId()) {
                case R.id.notibutton:
                    alarm.setVibrate(checked);
                    if (checked) {
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

    public CheckBox.OnCheckedChangeListener checkedChangeListener = new CheckBox.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            Alarm.Day day = Alarm.Day.SUNDAY;
            switch (buttonView.getId()) {
                case R.id.sunday:
                    day = Alarm.Day.SUNDAY;
                    break;
                case R.id.monday:
                    day = Alarm.Day.MONDAY;
                    break;
                case R.id.tuesday:
                    day = Alarm.Day.TUESDAY;
                    break;
                case R.id.wednesday:
                    day = Alarm.Day.WEDNESDAY;
                    break;
                case R.id.thursday:
                    day = Alarm.Day.THURSDAY;
                    break;
                case R.id.friday:
                    day = Alarm.Day.FRIDAY;
                    break;
                case R.id.saturday:
                    day = Alarm.Day.SATURDAY;
                    break;
            }

            if (isChecked)
                alarm.addDay(day);
            else
                alarm.removeDay(day);
        }
    };


    public void AlarmToneSelect() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MakeAlarmActivity.this);
        alert.setTitle("알람음 선택");

        RingtoneManager ringtoneMgr = new RingtoneManager(this);
        ringtoneMgr.setType(RingtoneManager.TYPE_ALARM);
        Cursor alarmsCursor = ringtoneMgr.getCursor();

        final String[] alarmTones = new String[alarmsCursor.getCount() + 1];
        alarmTones[0] = "Silent";
        final String[] alarmTonePaths = new String[alarmsCursor.getCount() + 1];
        alarmTonePaths[0] = "";

        if (alarmsCursor.moveToFirst()) {
            do {
                alarmTones[alarmsCursor.getPosition() + 1] = ringtoneMgr.getRingtone(alarmsCursor.getPosition()).getTitle(this);
                alarmTonePaths[alarmsCursor.getPosition() + 1] = ringtoneMgr.getRingtoneUri(alarmsCursor.getPosition()).toString();
            } while (alarmsCursor.moveToNext());
        }

        CharSequence[] items = new CharSequence[alarmsCursor.getCount() + 1];
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

    public void repeatMinuteSelect(){
        AlertDialog.Builder alert = new AlertDialog.Builder(MakeAlarmActivity.this);
        alert.setTitle("반복 선택");

        final CharSequence[] items = new CharSequence[minuitelist.length];

        for(int i = 0 ; i < items.length; i++){
            items[i] = minuitelist[i] + "분 마다";
        }

        alert.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alarm.setRepeatMinute(minuitelist[which]);
                repeatminute.setText(items[which]);
            }
        });

        alert.show();

    }

    public void writeFeeling(final int sep) {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("대화 설정");
        alert.setMessage("대화 내용을 입력해주세요.");

        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        input.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});

        if (sep == 0)
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
        alert.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
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


        alert.setNegativeButton(getResources().getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

        alert.show();
    }

}






