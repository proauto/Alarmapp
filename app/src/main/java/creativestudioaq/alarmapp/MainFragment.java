package creativestudioaq.alarmapp;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

/**
 * Created by honggyu on 2016-01-31.
 */
public class MainFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    private AnalogClockView mClockView;
    SubActionButton button1;
    SubActionButton button2;
    SubActionButton button3;
    ImageView rabbitbutton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);


        Button settingbutton = (Button) view.findViewById(R.id.settingbutton);
        Button alarmbutton = (Button) view.findViewById(R.id.alarmbutton);
        mClockView = (AnalogClockView) view.findViewById(R.id.clock);
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
                Intent intent = new Intent(getActivity(), MakeAlarmActivity.class);
                startActivity(intent);
            }
        });

        ImageView itemIcon2 = new ImageView(getActivity());
        itemIcon2.setImageResource(R.drawable.icon2);
        button2 = itemBuilder.setContentView(itemIcon2).build();

        ImageView itemIcon3 = new ImageView(getActivity());
        itemIcon3.setImageResource(R.drawable.icon3);
        button3 = itemBuilder.setContentView(itemIcon3).build();


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
    }

    @Override
    public void onPause() {
        super.onPause();
        mClockView.stop();
        Log.v("@@@???","???");

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
}
