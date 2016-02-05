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

        Intent intentget = getIntent();
        String time = intentget.getStringExtra("time");

        Button cancelbutton = (Button) findViewById(R.id.cancelbutton);
        Button savebutton = (Button) findViewById(R.id.savebutton);
        TextView repeatnumber = (TextView) findViewById(R.id.repeatnumber);
        TextView alarmsound = (TextView) findViewById(R.id.alarmsound);
        TextView selecttime = (TextView) findViewById(R.id.selecttime);
        TextView selectday = (TextView) findViewById(R.id.selectday);


        cancelbutton.setText("< 시간 설정");
        savebutton.setText("저장");
        selecttime.setText(time);
        selectday.setText("월 목");

        cancelbutton.setOnClickListener(this);
        savebutton.setOnClickListener(this);
        repeatnumber.setOnClickListener(this);
        alarmsound.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancelbutton:
                finish();
                break;
            case R.id.savebutton:
                finish();
                break;
           /* case R.id.feeltalk:
                Intent intent1 = new Intent(MakeAlarmActivity.this,FeelTalkActivity.class);
                startActivity(intent1);
                break;*/
            case R.id.repeatnumber:
                Intent intent2 = new Intent(MakeAlarmActivity.this, RepeatNumberActivity.class);
                startActivity(intent2);
                break;
            case R.id.alarmsound:
                Intent intent3 = new Intent(MakeAlarmActivity.this, AlarmSoundActivity.class);
                startActivity(intent3);
                break;
        }
    }
}
