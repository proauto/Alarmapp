package creativestudioaq.alarmapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
        Button facebook = (Button) view.findViewById(R.id.facebook);
        Button kakao = (Button) view.findViewById(R.id.kakao);
        Button tweet = (Button) view.findViewById(R.id.tweet);
        TextView version = (TextView) view.findViewById(R.id.version);

        nameText.setText("이름");
        notring.setText("공휴일에 알람 울리지 않기");
        whomake.setText("만든 사람들");
        version.setText("버전정보");
        facebook.setText("페북");
        kakao.setText("카톡");
        tweet.setText("트윗");

        nameText.setOnClickListener(this);
        whomake.setOnClickListener(this);
        notring.setOnClickListener(this);
        version.setOnClickListener(this);
        facebook.setOnClickListener(this);
        kakao.setOnClickListener(this);
        tweet.setOnClickListener(this);


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
            case R.id.facebook:
                break;
            case R.id.kakao:
                break;
            case R.id.tweet:
                break;
            case R.id.version:
                Intent intent3 = new Intent(getActivity(), VersionActivity.class);
                startActivity(intent3);
                break;
        }
    }
}
