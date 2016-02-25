package creativestudioaq.alarmapp.tool;

/**
 * Created by HosungKim on 2016-02-13.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import creativestudioaq.alarmapp.data.Alarm;
import creativestudioaq.alarmapp.ui_page.AlarmAlertActivity;
import creativestudioaq.alarmapp.ui_page.AlarmAlertActivity2;

public class AlarmAlertBroadcastReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {


        Intent mathAlarmServiceIntent = new Intent(
                context,
                AlarmServiceBroadcastReciever.class);
        context.sendBroadcast(mathAlarmServiceIntent, null);

        StaticWakeLock.lockOn(context);
        Bundle bundle = intent.getExtras();
        final Alarm alarm = (Alarm) bundle.getSerializable("alarm");
        boolean feelingok = alarm.getFeelingOk();

        Intent mathAlarmAlertActivityIntent;

        if(feelingok==true){

            mathAlarmAlertActivityIntent = new Intent(context, AlarmAlertActivity.class);

        }else{

            mathAlarmAlertActivityIntent = new Intent(context, AlarmAlertActivity2.class);

        }

        mathAlarmAlertActivityIntent.putExtra("alarm", alarm);

        mathAlarmAlertActivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(mathAlarmAlertActivityIntent);
    }

}