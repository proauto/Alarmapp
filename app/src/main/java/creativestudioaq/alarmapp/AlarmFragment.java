package creativestudioaq.alarmapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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
               showTimePickerDialog();
            }
        });
        return view;
    }
    private void showTimePickerDialog(){

        long now = System.currentTimeMillis();
        SimpleDateFormat dateFormat1 = new  SimpleDateFormat("HH", java.util.Locale.getDefault());
        SimpleDateFormat dateFormat2 = new  SimpleDateFormat("mm", java.util.Locale.getDefault());
        Date date = new Date(now);
        String strDate1 = dateFormat1.format(date);
        String strDate2 = dateFormat2.format(date);
        int numInt1 = Integer.parseInt(strDate1);
        int numInt2 = Integer.parseInt(strDate2);


        Dialog.Builder builder=new TimePickerDialog.Builder(R.style.Material_App_Dialog_TimePicker_gogo,numInt1,numInt2){
            @Override public void onPositiveActionClicked(    DialogFragment fragment){
                TimePickerDialog dialog=(TimePickerDialog)fragment.getDialog();

                SimpleDateFormat dateFormat3 = new  SimpleDateFormat("aa HH시 mm분", java.util.Locale.getDefault());
                Intent intent1 = new Intent(getActivity(),MakeAlarmActivity.class);
                intent1.putExtra("time", dialog.getFormattedTime(dateFormat3));
                getActivity().startActivity(intent1);

                super.onPositiveActionClicked(fragment);
            }
            @Override public void onNegativeActionClicked(    DialogFragment fragment){
                super.onNegativeActionClicked(fragment);
            }
        }
                ;
        builder.positiveAction("저장").negativeAction("취소");
        DialogFragment fragment=DialogFragment.newInstance(builder);
        fragment.show(getFragmentManager(),null);
    }
}
