package creativestudioaq.alarmapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by honggyu on 2016-01-31.
 */
public class SettingFragment extends android.support.v4.app.Fragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        TextView nameText = (TextView) view.findViewById(R.id.nametext);
        TextView notring = (TextView) view.findViewById(R.id.notring);
        TextView whomake = (TextView) view.findViewById(R.id.whomake);
        TextView share = (TextView) view.findViewById(R.id.share);
        TextView version = (TextView) view.findViewById(R.id.version);

        nameText.setText("이름");
        notring.setText("공휴일에 알람 울리지 않기");
        whomake.setText("만든 사람들");
        version.setText("버전정보");
        share.setText("공유");


        nameText.setOnClickListener(this);
        whomake.setOnClickListener(this);
        notring.setOnClickListener(this);
        version.setOnClickListener(this);
        share.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nametext:
                Intent intent1 = new Intent(getActivity(), NameActivity.class);
                startActivity(intent1);
                break;
            case R.id.notring:

                break;
            case R.id.whomake:
                Intent intent2 = new Intent(getActivity(), WhoMakeActivity.class);
                startActivity(intent2);
                break;
            case R.id.share:
                Intent msg = new Intent(Intent.ACTION_SEND);
                msg.addCategory(Intent.CATEGORY_DEFAULT);
                msg.putExtra(Intent.EXTRA_TEXT, "[앨리스] 몽환의 나라로 초대합니다.\n" +
                        "https://play.google.com/store/apps/details?id=creativestudioaq.daily&hl=ko\n" +
                        "FROM 시계토끼");
                msg.setType("text/plain");
                startActivity(Intent.createChooser(msg, "공유"));
                break;

            case R.id.version:
                Intent intent3 = new Intent(getActivity(), VersionActivity.class);
                startActivity(intent3);
                break;
        }
    }
}
