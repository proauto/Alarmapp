package creativestudioaq.alarmapp.ui_page;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import creativestudioaq.alarmapp.tool.AlarmServiceBroadcastReciever;
import creativestudioaq.alarmapp.R;
import creativestudioaq.alarmapp.data.Alarm;
import creativestudioaq.alarmapp.data.DatabaseSimple;

/**
 * Created by MIN on 2016. 2. 20..
 */
public class SimpleDialog extends Activity {

    private TextView tvHour, tvMinute;
    private int hour, minute;
    private Alarm alarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_simple);

        this.setFinishOnTouchOutside(false);

        tvHour = (TextView)findViewById(R.id.simple_tv_hour);
        tvMinute = (TextView)findViewById(R.id.simple_tv_minute);

        hour = minute = 0;

        displayTime(0);
    }

    public void displayTime(int plus){

        minute += plus;
        hour += minute/60;
        minute %= 60;

        tvHour.setText(String.format("%d", hour));
        tvMinute.setText(String.format("%02d", minute));
    }

    public void simpleTimeSelect(View v){
        int plus = 0;
        switch (v.getId()){
            case R.id.simple_button_plus_1:
                plus = 1;
                break;
            case R.id.simple_button_plus_5:
                plus = 5;
                break;
            case R.id.simple_button_plus_10:
                plus = 10;
                break;
            case R.id.simple_button_plus_60:
                plus = 60;
                break;
        }

        displayTime(plus);
    }

    public void DialogFinishClick(View v){
        switch (v.getId()){
            case R.id.simple_button_ok:
                alarm = new Alarm();

                Date date = new Date();
                long now = date.getTime();
                now = now + minute * 60000 + hour * 60000 * 60 + 1000;

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(now);

                alarm.setAlarmTime(calendar);

                DatabaseSimple.init(getApplicationContext());
                DatabaseSimple.create(alarm);

                callMathAlarmScheduleService();
                Toast.makeText(this, alarm.getTimeUntilNextAlarmMessage(), Toast.LENGTH_LONG).show();
                break;
            case R.id.simple_button_cancel:
                break;
        }
        finish();
    }

    protected void callMathAlarmScheduleService() {
        Intent mathAlarmServiceIntent = new Intent(this, AlarmServiceBroadcastReciever.class);
        sendBroadcast(mathAlarmServiceIntent, null);
    }
}
