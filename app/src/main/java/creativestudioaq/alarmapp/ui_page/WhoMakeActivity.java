package creativestudioaq.alarmapp.ui_page;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import creativestudioaq.alarmapp.R;

/**
 * Created by honggyu on 2016-02-01.
 */
public class WhoMakeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whomake);

        TextView whomaketext = (TextView) findViewById(R.id.whomaketext);
        whomaketext.setText("만든 사람들\n\n개발자 : 김호성, 민지영, 이홍규\n디자이너 : 고은별, 김유리, 서민아");
    }
}
