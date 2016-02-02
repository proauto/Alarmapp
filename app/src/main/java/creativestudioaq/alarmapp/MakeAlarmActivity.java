package creativestudioaq.alarmapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by honggyu on 2016-01-31.
 */
public class MakeAlarmActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_makealarm);

        Button cancelbutton = (Button)findViewById(R.id.cancelbutton);
        Button savebutton = (Button)findViewById(R.id.savebutton);
        Button sunday = (Button)findViewById(R.id.sunday);
        Button monday = (Button)findViewById(R.id.monday);
        Button tuesday = (Button)findViewById(R.id.tuesday);
        Button wednsday = (Button)findViewById(R.id.wednesday);
        Button thursday = (Button)findViewById(R.id.thursday);
        Button friday = (Button)findViewById(R.id.friday);
        Button saturday = (Button)findViewById(R.id.saturday);
        TextView feeltalk = (TextView)findViewById(R.id.feeltalk);
        TextView repeatnumber = (TextView)findViewById(R.id.repeatnumber);
        TextView alarmsound = (TextView)findViewById(R.id.alarmsound);

        cancelbutton.setText("취소");
        savebutton.setText("저장");

        cancelbutton.setOnClickListener(this);
        savebutton.setOnClickListener(this);
        sunday.setOnClickListener(this);
        monday.setOnClickListener(this);
        tuesday.setOnClickListener(this);
        wednsday.setOnClickListener(this);
        thursday.setOnClickListener(this);
        friday.setOnClickListener(this);
        saturday.setOnClickListener(this);
        feeltalk.setOnClickListener(this);
        repeatnumber.setOnClickListener(this);
        alarmsound.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.cancelbutton:
                finish();
                break;
            case R.id.savebutton:
                finish();
                break;
            case R.id.sunday:
                break;
            case R.id.monday:
                break;
            case R.id.tuesday:
                break;
            case R.id.wednesday:
                break;
            case R.id.thursday:
                break;
            case R.id.friday:
                break;
            case R.id.saturday:
                break;
            case R.id.feeltalk:
                Intent intent1 = new Intent(MakeAlarmActivity.this,FeelTalkActivity.class);
                startActivity(intent1);
                break;
            case R.id.repeatnumber:
                Intent intent2 = new Intent(MakeAlarmActivity.this,RepeatNumberActivity.class);
                startActivity(intent2);
                break;
            case R.id.alarmsound:
                Intent intent3 = new Intent(MakeAlarmActivity.this,AlarmSoundActivity.class);
                startActivity(intent3);
                break;
        }
    }
}
