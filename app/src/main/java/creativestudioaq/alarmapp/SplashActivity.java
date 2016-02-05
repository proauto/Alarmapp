package creativestudioaq.alarmapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by honggyu on 2016-01-31.
 */
public class SplashActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Intent intent1 = new Intent(this, MainActivity.class);
        startActivity(intent1);
        finish();

    }
}
