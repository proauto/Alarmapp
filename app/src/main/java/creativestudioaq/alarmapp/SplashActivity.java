package creativestudioaq.alarmapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * Created by honggyu on 2016-01-31.
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        ImageView dot = (ImageView)findViewById(R.id.splash_dot);
        RelativeLayout splashlayout = (RelativeLayout)findViewById(R.id.splashlayout);

        AlphaAnimation animation1 = new AlphaAnimation(0f, 1.0f);
        animation1.setDuration(2500);
        dot.startAnimation(animation1);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2500);
    }
}
