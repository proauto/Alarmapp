package creativestudioaq.alarmapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rey.material.widget.Switch;

import java.util.ArrayList;

/**
 * Created by honggyu on 2016-01-31.
 */


//List Adapter Class
public class AlarmlistAdapter extends BaseAdapter {


    private LayoutInflater _inflater;
    private static ArrayList<Alarmlist> _lists;
    private int _layout;
    private static Context m_ctx;


    public AlarmlistAdapter(Context context, int layout, ArrayList<Alarmlist> lists) {
        _inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        _lists = lists;
        _layout = layout;
        m_ctx = context;

    }

    @Override
    public int getCount() {
        return _lists.size();
    }

    @Override
    public String getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = _inflater.inflate(_layout, parent, false);


        TextView alarmtime = (TextView) convertView.findViewById(R.id.alarmtime);
        TextView alarmday = (TextView) convertView.findViewById(R.id.alarmday);
        Switch toggle = (Switch) convertView.findViewById(R.id.toggle);

        alarmtime.setText(_lists.get(position).gettime());
        alarmday.setText(_lists.get(position).getday());
        toggle.setChecked(_lists.get(position).getcheck());


        return convertView;
    }

}

