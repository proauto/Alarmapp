package creativestudioaq.alarmapp.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.ArrayList;

import creativestudioaq.alarmapp.R;
import creativestudioaq.alarmapp.data.Repeatlist;

/**
 * Created by honggyu on 2016-02-05.
 */
public class RepeatlistAdapter extends BaseAdapter {


    private LayoutInflater _inflater;
    private static ArrayList<Repeatlist> _lists;
    private int _layout;
    private static Context m_ctx;


    public RepeatlistAdapter(Context context, int layout, ArrayList<Repeatlist> lists) {
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


        TextView textlist = (TextView) convertView.findViewById(R.id.textlist);
        RadioButton buttonlist = (RadioButton) convertView.findViewById(R.id.buttonlist);

        textlist.setText(_lists.get(position).gettime());
        buttonlist.setChecked(_lists.get(position).getcheck());


        return convertView;
    }

}
