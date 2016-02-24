package creativestudioaq.alarmapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by honggyu on 2016-01-31.
 */
public class TutorialActivity extends Activity {

    int page = 0;
    int factor=0;

    TextView firsttalk;
    TextView secondtalk;
    EditText tutorialinput;
    Button sendbutton;
    String name;
    Button yesbutton;
    Button nobutton;
    SharedPreferences setting;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_tutorial);

        RelativeLayout clickthis = (RelativeLayout) findViewById(R.id.clickthis);
        firsttalk = (TextView) findViewById(R.id.tutorialtalk1);
        secondtalk = (TextView) findViewById(R.id.tutorialtalk2);
        tutorialinput = (EditText)findViewById(R.id.tutorialinput);
        sendbutton = (Button)findViewById(R.id.sendbutton);
        yesbutton = (Button)findViewById(R.id.yes);
        nobutton = (Button)findViewById(R.id.no);
        setting = getSharedPreferences("setting",0);



        tutorialinput.setVisibility(View.INVISIBLE);
        yesbutton.setVisibility(View.INVISIBLE);
        nobutton.setVisibility(View.INVISIBLE);


        firsttalk.setText("오, 이런이런! 늦겠어!");
        secondtalk.setText("* 내이름은...");
        secondtalk.setVisibility(View.INVISIBLE);
        sendbutton.setVisibility(View.INVISIBLE);

        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = tutorialinput.getText().toString();
                secondtalk.setText("* 내 이름은 " + name + " 야.");
                secondtalk.setVisibility(View.VISIBLE);
                sendbutton.setVisibility(View.INVISIBLE);
                tutorialinput.setVisibility(View.INVISIBLE);
                firsttalk.setVisibility(View.INVISIBLE);
                factor = 0;
                page++;

            }
        });

        yesbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yesbutton.setVisibility(View.INVISIBLE);
                nobutton.setVisibility(View.INVISIBLE);
                firsttalk.setText("좋았어, 앞으로 잘 부탁해! " + name + "!");
                editor = setting.edit();
                editor.putString("name",name);
                editor.putBoolean("tutorial", false);
                editor.apply();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(TutorialActivity.this, MainActivity.class);
                startActivity(intent);
                finish();


            }
        });

        nobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firsttalk.setText("아직 마음의 준비가 안되었니\n난 언제든지 기다려줄게.");
                nobutton.setVisibility(View.INVISIBLE);
                yesbutton.setText("* 준비됐어!");
                yesbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        yesbutton.setVisibility(View.INVISIBLE);

                        firsttalk.setText("좋았어, 앞으로 잘 부탁해! " + name + "!");
                        editor = setting.edit();
                        editor.putString("name",name);
                        editor.putBoolean("tutorial",false);
                        editor.apply();
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Intent intent = new Intent(TutorialActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    }
                });

            }
        });


        clickthis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (page == 0) {
                    firsttalk.setText("안녕! 내가 늦진 않았지?");
                }else if(page ==1){
                    firsttalk.setText("난 항상 약속을 중요하게 생각하고 있지.");
                }else if(page ==2){
                    firsttalk.setText("아참, 내 소개가 늦었네!");
                }else if(page==3){
                    firsttalk.setText("나는 시계토끼야!\n니 이름은 뭐니?");
                }else if(page==4) {
                    secondtalk.setVisibility(View.VISIBLE);

                }else if(page==5) {
                    secondtalk.setVisibility(View.INVISIBLE);
                    tutorialinput.setVisibility(View.VISIBLE);
                    sendbutton.setVisibility(View.VISIBLE);
                    factor = 1;

                }else if(page==6){
                    secondtalk.setVisibility(View.INVISIBLE);
                    firsttalk.setVisibility(View.VISIBLE);
                    firsttalk.setText(name +"! 알려줘서 고마워~");
                }else if(page==7){
                    firsttalk.setText("나랑 함께할 준비는 되었니?");
                }else if(page==8){
                    yesbutton.setVisibility(View.VISIBLE);
                    nobutton.setVisibility(View.VISIBLE);
                    yesbutton.setText("* 응.");
                    nobutton.setText("* 아니.");

                    factor=1;
                }

                if(factor==0)
                page++;

            }
        });
    }
}
