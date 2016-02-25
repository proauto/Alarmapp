package creativestudioaq.alarmapp.ui_page;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import creativestudioaq.alarmapp.R;

/**
 * Created by honggyu on 2016-02-01.
 */
public class FeelTalkActivity extends Activity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeltalk);

        Button cancelbutton = (Button) findViewById(R.id.cancelbutton);
        Button savebutton = (Button) findViewById(R.id.savebutton);
        Button loadbutton = (Button) findViewById(R.id.loadbutton);
        Button previewbutton = (Button) findViewById(R.id.previewbutton);
        EditText firsttalk = (EditText) findViewById(R.id.firsttalk);
        EditText secondtalk = (EditText) findViewById(R.id.secondtalk);

        cancelbutton.setText("취소");
        savebutton.setText("저장");
        loadbutton.setText("다른 대화 불러오기");
        previewbutton.setText("미리보기");


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
}
