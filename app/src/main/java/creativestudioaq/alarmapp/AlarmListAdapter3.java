package creativestudioaq.alarmapp;

/**
 * Created by HosungKim on 2016-02-13.
 */

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AlarmListAdapter3 extends BaseAdapter {

    private static AlarmFragment _alarmFragment;
    private List<Alarm> alarms = new ArrayList<Alarm>();
    private static Context m_ctx;

    public static final String ALARM_FIELDS[] = { Database.COLUMN_ALARM_ACTIVE,
            Database.COLUMN_ALARM_TIME, Database.COLUMN_ALARM_DAYS };

    public AlarmListAdapter3(AlarmFragment alarmFragment,Context ctx) {
        _alarmFragment=alarmFragment;
        m_ctx=ctx;
    }

    @Override
    public int getCount() {
        return alarms.size();
    }

    @Override
    public Object getItem(int position) {
        return alarms.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (null == view)
            view = LayoutInflater.from(m_ctx).inflate(
                    R.layout.alarm_list_element_new, null);

        Alarm alarm = (Alarm)getItem(position);

        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox_alarm_active);
        checkBox.setChecked(alarm.getAlarmActive());
        checkBox.setTag(position);
        checkBox.setOnClickListener(_alarmFragment);

        TextView alarmTimeView = (TextView) view
                .findViewById(R.id.textView_alarm_time);
        TextView alarmTimeAP = (TextView)view.findViewById(R.id.textView_time_ap);

        SimpleDateFormat dateFormat1 = new SimpleDateFormat("KK:mm", java.util.Locale.getDefault());
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("HH:mm", java.util.Locale.getDefault());
        SimpleDateFormat dateFormat3 = new SimpleDateFormat("aa", Locale.US);

        Date date = new Date();

        try{
            date = dateFormat2.parse(alarm.getAlarmTimeString());
        }catch (Exception e){
            e.printStackTrace();
        }

        String time2 = dateFormat1.format(date);
        String time3 = dateFormat3.format(date);

        alarmTimeView.setText(time2);
        alarmTimeAP.setText(time3);

        TextView alarmDaysView = (TextView) view.findViewById(R.id.textView_alarm_days);
        alarmDaysView.setText(alarm.getRepeatDaysString());

        return view;
    }

    public List<Alarm> getMathAlarms() {
        return alarms;
    }

    public void setMathAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
    }

}
