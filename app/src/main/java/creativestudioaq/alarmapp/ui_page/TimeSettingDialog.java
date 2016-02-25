package creativestudioaq.alarmapp.ui_page;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import creativestudioaq.alarmapp.R;
import creativestudioaq.alarmapp.ui_page.MakeAlarmActivity;

/**
 * Created by honggyu on 2016-02-05.
 */
public class TimeSettingDialog extends Dialog implements View.OnClickListener {

    Context ctx;

    public TimeSettingDialog(Context context) {
        super(context);
        ctx = context;

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_timeselect);


        Button sunday = (Button) findViewById(R.id.sunday);
        Button monday = (Button) findViewById(R.id.monday);
        Button tuesday = (Button) findViewById(R.id.tuesday);
        Button wednsday = (Button) findViewById(R.id.wednesday);
        Button thursday = (Button) findViewById(R.id.thursday);
        Button friday = (Button) findViewById(R.id.friday);
        Button saturday = (Button) findViewById(R.id.saturday);
        Button cancelbutton = (Button) findViewById(R.id.cancelbutton);
        Button savebutton = (Button) findViewById(R.id.savebutton);


        sunday.setOnClickListener(this);
        monday.setOnClickListener(this);
        tuesday.setOnClickListener(this);
        wednsday.setOnClickListener(this);
        thursday.setOnClickListener(this);
        friday.setOnClickListener(this);
        saturday.setOnClickListener(this);
        cancelbutton.setOnClickListener(this);
        savebutton.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

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
            case R.id.cancelbutton:
                dismiss();
                break;
            case R.id.savebutton:
                Intent intent1 = new Intent(ctx, MakeAlarmActivity.class);
                ctx.startActivity(intent1);
                dismiss();
                break;

        }
    }
}
