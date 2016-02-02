package creativestudioaq.alarmapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by honggyu on 2016-01-31.
 */
public class AlarmFragment extends android.support.v4.app.Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_alarm,container,false);


        ListView alarmlist = (ListView)view.findViewById(R.id.alarmlist);
        Button alarmplus = (Button)view.findViewById(R.id.alarmplus);
        ArrayList<Alarmlist> _lists = new ArrayList<Alarmlist>();


        //예시 생성
        Alarmlist s = new Alarmlist("11:30","월,금",true);
        _lists.add(s);
        s = new Alarmlist("10:30","모든 요일",false);
        _lists.add(s);


        //어댑터생성
        AlarmlistAdapter adapter = new AlarmlistAdapter(getActivity(),R.layout.alarmlist_sub,_lists);
        alarmlist.setAdapter(adapter);

        alarmplus.setText("알람추가");
        alarmplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getActivity(),MakeAlarmActivity.class);
                startActivity(intent1);
            }
        });
        return view;
    }
}
