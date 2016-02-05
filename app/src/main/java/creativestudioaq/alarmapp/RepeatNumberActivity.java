package creativestudioaq.alarmapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by honggyu on 2016-02-01.
 */
public class RepeatNumberActivity extends Activity implements View.OnClickListener {


    ArrayList<Repeatlist> minutelist = new ArrayList<Repeatlist>();
    ArrayList<Repeatlist> repeatlist = new ArrayList<Repeatlist>();
    MinutelistAdapter adapter1;
    RepeatlistAdapter adapter2;
    ListView minutelistview;
    ListView repeatlistview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_repeatnumber);

        Button cancelbutton = (Button) findViewById(R.id.cancelbutton);
        Button savebutton = (Button) findViewById(R.id.savebutton);
        minutelistview = (ListView) findViewById(R.id.minutelist);
        repeatlistview = (ListView) findViewById(R.id.repeatlist);


        //예시 생성
        makeListContent();


        //어댑터생성

        adapter1 = new MinutelistAdapter(this, R.layout.repeatlist_sub, minutelist);
        minutelistview.setAdapter(adapter1);


        adapter2 = new RepeatlistAdapter(this, R.layout.repeatlist_sub, repeatlist);
        repeatlistview.setAdapter(adapter2);


        repeatlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int i = 0;
                String content = "";
                Repeatlist s = new Repeatlist(content, false);

                Log.v("1", "1");

                for (i = 0; i < 5; i++) {
                    content = repeatlist.get(i).gettime();
                    s = new Repeatlist(content, false);
                    repeatlist.set(i, s);
                    if (i == position) {
                        s = new Repeatlist(content, true);
                        repeatlist.set(i, s);
                    }

                }

                adapter2.notifyDataSetChanged();


            }
        });
        minutelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                int i = 0;
                String content = "";
                Repeatlist s = new Repeatlist(content, false);

                Log.v("2", "2");

                for (i = 0; i < 5; i++) {
                    content = minutelist.get(i).gettime();
                    s = new Repeatlist(content, false);
                    minutelist.set(i, s);

                    if (i == position) {
                        s = new Repeatlist(content, true);
                        minutelist.set(i, s);
                    }

                }

                adapter1.notifyDataSetChanged();


            }
        });

        cancelbutton.setText("취소");
        savebutton.setText("저장");

        cancelbutton.setOnClickListener(this);
        savebutton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancelbutton:
                finish();
                break;
            case R.id.savebutton:
                finish();
                break;
        }
    }

    private void makeListContent() {

        //minutelist 채우기
        Repeatlist s = new Repeatlist("3분", true);
        minutelist.add(s);
        s = new Repeatlist("5분", false);
        minutelist.add(s);
        s = new Repeatlist("10분", false);
        minutelist.add(s);
        s = new Repeatlist("15분", false);
        minutelist.add(s);
        s = new Repeatlist("30분", false);
        minutelist.add(s);

        //repeatlist 채우기
        s = new Repeatlist("1회", true);
        repeatlist.add(s);
        s = new Repeatlist("2회", false);
        repeatlist.add(s);
        s = new Repeatlist("3회", false);
        repeatlist.add(s);
        s = new Repeatlist("5회", false);
        repeatlist.add(s);
        s = new Repeatlist("10회", false);
        repeatlist.add(s);

    }
/*

    private class RepeatListViewItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {




                int i = 0;
                String content = "";
                Repeatlist s = new Repeatlist(content, false);


                for (i = 0; i < 5; i++) {
                    content = repeatlist.get(i).gettime();
                    s = new Repeatlist(content, false);
                    repeatlist.set(i, s);
                    if (i == position) {
                        s = new Repeatlist(content, true);
                        repeatlist.set(i, s);
                    }

                }

                adapter2.notifyDataSetChanged();



        }

    }
    */
}
