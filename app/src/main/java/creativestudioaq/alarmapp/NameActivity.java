package creativestudioaq.alarmapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by honggyu on 2016-02-01.
 */
public class NameActivity extends Activity implements View.OnClickListener {


    SharedPreferences setting;
    SharedPreferences.Editor editor;
    com.rey.material.widget.EditText nameinput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_namesetting);

        setting = getSharedPreferences("setting",0);
        editor = setting.edit();

        Button cancelbutton = (Button) findViewById(R.id.cancelbutton);
        Button savebutton = (Button) findViewById(R.id.savebutton);
        nameinput = (com.rey.material.widget.EditText)findViewById(R.id.nameinput);
        nameinput.setText(setting.getString("name",""));
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
                editor.putString("name",nameinput.getText().toString());
                editor.apply();
                finish();
                break;
        }
    }
}
