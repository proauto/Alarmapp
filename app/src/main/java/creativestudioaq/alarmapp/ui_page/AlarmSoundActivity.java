package creativestudioaq.alarmapp.ui_page;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import creativestudioaq.alarmapp.R;

/**
 * Created by honggyu on 2016-02-01.
 */
public class AlarmSoundActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarmsound);

        Button cancelbutton = (Button) findViewById(R.id.cancelbutton);
        Button savebutton = (Button) findViewById(R.id.savebutton);

        cancelbutton.setText("취소");
        savebutton.setText("저장");

        cancelbutton.setOnClickListener(this);
        savebutton.setOnClickListener(this);
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
        }
    }
}
