package creativestudioaq.alarmapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by honggyu on 2016-02-05.
 */
public class TimeSettingDialog extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button sunday = (Button)findViewById(R.id.sunday);
        Button monday = (Button)findViewById(R.id.monday);
        Button tuesday = (Button)findViewById(R.id.tuesday);
        Button wednsday = (Button)findViewById(R.id.wednesday);
        Button thursday = (Button)findViewById(R.id.thursday);
        Button friday = (Button)findViewById(R.id.friday);
        Button saturday = (Button)findViewById(R.id.saturday);


        sunday.setOnClickListener(this);
        monday.setOnClickListener(this);
        tuesday.setOnClickListener(this);
        wednsday.setOnClickListener(this);
        thursday.setOnClickListener(this);
        friday.setOnClickListener(this);
        saturday.setOnClickListener(this);
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

        }
    }
}
