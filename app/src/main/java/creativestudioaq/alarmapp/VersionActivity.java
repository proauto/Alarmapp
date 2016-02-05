package creativestudioaq.alarmapp;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

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
