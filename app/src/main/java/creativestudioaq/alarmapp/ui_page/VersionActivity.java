package creativestudioaq.alarmapp.ui_page;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import creativestudioaq.alarmapp.R;

/**
 * Created by honggyu on 2016-02-01.
 */
public class VersionActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_version);

        TextView versiontext = (TextView) findViewById(R.id.versiontext);

        versiontext.setText("Version 1.0");
    }
}
