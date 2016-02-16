package creativestudioaq.alarmapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.TimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by honggyu on 2016-01-31.
 */
public class AlarmFragment extends android.support.v4.app.Fragment implements View.OnClickListener {



    AlarmListAdapter3 alarmListAdapter;

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       view = inflater.inflate(R.layout.fragment_alarm, container, false);


        ListView alarmlist = (ListView) view.findViewById(R.id.alarmlist);
        Button plusbutton = (Button)view.findViewById(R.id.plusbutton);
        plusbutton.setOnClickListener(this);


        alarmlist.setLongClickable(true);
        alarmlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
                view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                final Alarm alarm = (Alarm) alarmListAdapter.getItem(position);
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle("Delete");
                dialog.setMessage("Delete this alarm?");
                dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Database.init(getActivity());
                        Database.deleteEntry(alarm);

                        Intent mathAlarmServiceIntent = new Intent(getActivity(), AlarmServiceBroadcastReciever.class);
                        getActivity().sendBroadcast(mathAlarmServiceIntent, null);

                        updateAlarmList();
                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                dialog.show();

                return true;
            }
        });


        Intent mathAlarmServiceIntent = new Intent(getActivity(), AlarmServiceBroadcastReciever.class);
        getActivity().sendBroadcast(mathAlarmServiceIntent, null);

        alarmListAdapter = new AlarmListAdapter3(this, getActivity());
        alarmlist.setAdapter(alarmListAdapter);
        alarmlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
                v.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                Alarm alarm = (Alarm) alarmListAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), MakeAlarmActivity.class);
                intent.putExtra("alarm", alarm);
                startActivity(intent);
            }

        });



        return view;
    }



    @Override
    public void onPause() {
        // setListAdapter(null);
        Database.deactivate();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        updateAlarmList();
    }

    public void updateAlarmList(){
        Database.init(getActivity());
        final List<Alarm> alarms = Database.getAll();
        alarmListAdapter.setMathAlarms(alarms);

        getActivity().runOnUiThread(new Runnable() {
            public void run() {
                // reload content
                alarmListAdapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.checkBox_alarm_active) {
            CheckBox checkBox = (CheckBox) v;
            Alarm alarm = (Alarm) alarmListAdapter.getItem((Integer) checkBox.getTag());
            alarm.setAlarmActive(checkBox.isChecked());
            Database.update(alarm);
            Intent mathAlarmServiceIntent = new Intent(getActivity(), AlarmServiceBroadcastReciever.class);
            getActivity().sendBroadcast(mathAlarmServiceIntent, null);
            if (checkBox.isChecked()) {
                Toast.makeText(getActivity(), alarm.getTimeUntilNextAlarmMessage(), Toast.LENGTH_LONG).show();
            }
        }else if(v.getId()==R.id.plusbutton){

            Intent intent = new Intent(getActivity(),AlarmPreferencesActivity.class);
            startActivity(intent);

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

                SimpleDateFormat dateFormat3 = new SimpleDateFormat("aa HH시 mm분", java.util.Locale.getDefault());
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

}
