package creativestudioaq.alarmapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MIN on 2016. 2. 20..
 */
public class SimpleAdapter extends BaseAdapter {

    private List<Alarm> alarms = new ArrayList<Alarm>();
    private static Context context;

    public SimpleAdapter(Context context){
        this.context = context;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        if( convertView == null ){
            convertView = LayoutInflater.from(context).inflate(R.layout.alarm_list_element_simple, null);
        }

        Alarm alarm = (Alarm)getItem(position);

        TextView simpleTime = (TextView)convertView.findViewById(R.id.tv_simple_alarm_time);
        simpleTime.setText(alarm.getAlarmTimeString());

        return convertView;
    }

    public List<Alarm> getMathAlarms() {
        return alarms;
    }

    public void setMathAlarms(List<Alarm> alarms) {
        this.alarms = alarms;
    }
}
