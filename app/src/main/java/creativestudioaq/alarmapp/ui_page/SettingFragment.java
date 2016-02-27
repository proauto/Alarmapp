package creativestudioaq.alarmapp.ui_page;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

import creativestudioaq.alarmapp.R;

/**
 * Created by honggyu on 2016-01-31.
 */
public class SettingFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    private LinearLayout settinglayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting, container, false);


        TextView nameText = (TextView) view.findViewById(R.id.nametext);
        TextView whomake = (TextView) view.findViewById(R.id.whomake);
        TextView share = (TextView) view.findViewById(R.id.share);
        settinglayout = (LinearLayout) view.findViewById(R.id.settingbackground);

        nameText.setText("My Name");
        whomake.setText("Info");
        share.setText("Share");


        nameText.setOnClickListener(this);
        whomake.setOnClickListener(this);
        share.setOnClickListener(this);

        setBackgroundColor();

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nametext:
                Intent intent1 = new Intent(getActivity(), NameActivity.class);
                startActivity(intent1);
                break;
            case R.id.whomake:
                Intent intent2 = new Intent(getActivity(), WhoMakeActivity.class);
                startActivity(intent2);
                break;
            case R.id.share:
                Intent msg = new Intent(Intent.ACTION_SEND);
                msg.addCategory(Intent.CATEGORY_DEFAULT);
                msg.putExtra(Intent.EXTRA_TEXT, "[앨리스] 몽환의 나라로 초대합니다.\n" +
                        "https://play.google.com/store/apps/details?id=creativestudioaq.alarmapp\n" +
                        "FROM 시계토끼");
                msg.setType("text/plain");
                startActivity(Intent.createChooser(msg, "공유"));
                break;
        }
    }


    public void setBackgroundColor() {
        int backgroundColor = ((MainActivity)getActivity()).getBackgroundColor();
        settinglayout.setBackgroundColor(backgroundColor);
    }



}
