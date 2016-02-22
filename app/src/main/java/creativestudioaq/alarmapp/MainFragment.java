package creativestudioaq.alarmapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by honggyu on 2016-01-31.
 */
public class MainFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    private AnalogClockView mClockView;
    SubActionButton button1;
    SubActionButton button2;
    SubActionButton button3;
    ImageView rabbitbutton;
    LinearLayout mainlayout;
    TextView rabbitTongue;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);


        Button settingbutton = (Button) view.findViewById(R.id.settingbutton);
        Button alarmbutton = (Button) view.findViewById(R.id.alarmbutton);
        mainlayout = (LinearLayout) view.findViewById(R.id.mainlayout);
        mClockView = (AnalogClockView) view.findViewById(R.id.clock);
        rabbitTongue = (TextView)view.findViewById(R.id.rabbitTongue);
        settingbutton.setOnClickListener(this);
        alarmbutton.setOnClickListener(this);

        rabbitbutton = (ImageView) view.findViewById(R.id.rabbitbutton);

        rabbitbutton.post(new Runnable() {
            @Override
            public void run() {
                ((AnimationDrawable) rabbitbutton.getBackground()).start();
            }
        });
        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(getActivity());
        // repeat many times:
        ImageView itemIcon1 = new ImageView(getActivity());
        itemIcon1.setImageResource(R.drawable.icon1);
        button1 = itemBuilder.setContentView(itemIcon1).build();
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SimpleDialog.class);
                startActivity(intent);
            }
        });

        ImageView itemIcon2 = new ImageView(getActivity());
        itemIcon2.setImageResource(R.drawable.icon2);
        button2 = itemBuilder.setContentView(itemIcon2).build();
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        ImageView itemIcon3 = new ImageView(getActivity());
        itemIcon3.setImageResource(R.drawable.icon3);
        button3 = itemBuilder.setContentView(itemIcon3).build();
        button3.setOnClickListener(new View.OnClickListener(){
            Handler handler = new Handler();
            @Override
            public void onClick(View v) {
                rabbitbutton.performClick();
                rabbitTongue.setVisibility(View.VISIBLE);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rabbitTongue.setVisibility(View.INVISIBLE);
                    }
                }, 3000);
            }
        });


        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(getActivity())
                .setStartAngle(225)
                .setEndAngle(315)
                .addSubActionView(button1)
                .addSubActionView(button2)
                .addSubActionView(button3)
                .attachTo(rabbitbutton)
                .build();

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        mClockView.start();
        setBackgroundColor();
    }

    @Override
    public void onPause() {
        super.onPause();
        mClockView.stop();
        Log.v("@@@???", "???");
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {
            case R.id.settingbutton:
                ((MainActivity) getActivity()).getViewPager().setCurrentItem(0);
                break;
            case R.id.alarmbutton:
                ((MainActivity) getActivity()).getViewPager().setCurrentItem(2);
                break;

        }

    }

    private void showTimePickerDialog() {

        long now = System.currentTimeMillis();
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("HH", java.util.Locale.getDefault());
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("mm", java.util.Locale.getDefault());
        Date date = new Date(now);
        String strDate1 = dateFormat1.format(date);
        String strDate2 = dateFormat2.format(date);
        int numInt1 = Integer.parseInt(strDate1);
        int numInt2 = Integer.parseInt(strDate2);


        Dialog.Builder builder = new TimePickerDialog.Builder(R.style.Material_App_Dialog_TimePicker_gogo, numInt1, numInt2) {
            @Override
            public void onPositiveActionClicked(DialogFragment fragment) {
                TimePickerDialog dialog = (TimePickerDialog) fragment.getDialog();

                SimpleDateFormat dateFormat3 = new SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault());
                Intent intent1 = new Intent(getActivity(), MakeAlarmActivity.class);
                intent1.putExtra("time", dialog.getFormattedTime(dateFormat3));
                getActivity().startActivity(intent1);

                super.onPositiveActionClicked(fragment);
            }

            @Override
            public void onNegativeActionClicked(DialogFragment fragment) {
                super.onNegativeActionClicked(fragment);
            }
        };
        builder.positiveAction("저장").negativeAction("취소");
        DialogFragment fragment = DialogFragment.newInstance(builder);
        fragment.show(getFragmentManager(), null);
    }

    public void setBackgroundColor(){

        Calendar tempCal = Calendar.getInstance();
        int hour = tempCal.getTime().getHours();

        String backgroundColor;


        if( hour < 2 )
            backgroundColor = "#3D4244";
        else if ( hour < 7 )
            backgroundColor = "#6C758E";
        else if ( hour < 12 )
           backgroundColor = "#FF8E81";
        else if ( hour < 17)
           backgroundColor = "#64A0BC";
        else if ( hour < 22 )
            backgroundColor = "#284C76";
        else
            backgroundColor = "#3D4244";

        mainlayout.setBackgroundColor(Color.parseColor(backgroundColor));
    }
}
