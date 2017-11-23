package com.example.mapl0.db_game_project.db.Game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mapl0.db_game_project.Framework.AppManager;
import com.example.mapl0.db_game_project.Framework.GameActivity;
import com.example.mapl0.db_game_project.Framework.GameView;
import com.example.mapl0.db_game_project.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by mapl0 on 2016-12-01.
 */

public class RankActivity extends Activity {

    private Activity activity;
    public static Context context;
    private ArrayList<Item> m_arr;
    private List_Adapter adapter;


    private ListView lv;
    Button reStart;
    TextView rank_tv;

    String[] name;
    int[] score;

    String c_name;
    int c_score;

    GameActivity gameActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀제거

        setContentView(R.layout.activity_rank);

        Intent intent = getIntent();

        lv = (ListView)findViewById(R.id.listView1);
        reStart = (Button) findViewById(R.id.re_button);
        rank_tv = (TextView)findViewById(R.id.rank_tv);

        name = intent.getStringArrayExtra("NAME");
        score = intent.getIntArrayExtra("SCORE");
        c_name = intent.getStringExtra("C_NAME");
        c_score = intent.getIntExtra("C_SCORE", 0);

        rank_tv.setText(c_name + " 님의 스코어는 " + c_score + "점 입니다!!");

        setList();

        reStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GameView.m_Sound_BackGround.pause();
                GameView.m_Sound_BackGround.stop();
                GameView.m_Sound_BackGround.release();


                gameActivity = (GameActivity)GameActivity.gameActivity;

                GameView gameView = AppManager.getInstance().getGameview();
                Context context = gameView.context;

                AppManager.getInstance().setGameView(new GameView(gameActivity, 1));

                Intent intent = new Intent(getApplicationContext(), gameActivity.getClass());
                startActivity(intent);

                finish();
            }
        });
    }
    private void setList(){

        m_arr = new ArrayList<Item>();

        for(int i=0; i<name.length; i++) {
            m_arr.add(new Item("@drawable/pad", (i+1) , name[i], score[i]));
            if(name[i].toString().equals(c_name) && score[i] == c_score) {
                rank_tv.setText(c_name + " 님의 스코어는 " + c_score + "점으로 " + (i+1) + "등 입니다!!");
            }
        }

        adapter = new List_Adapter(RankActivity.this, m_arr);
        lv.setAdapter(adapter);

        //lv.setDivider(null); 구분선을 없에고 싶으면 null 값을 set합니다.
        //lv.setDividerHeight(5); 구분선의 굵기를 좀 더 크게 하고싶으면 숫자로 높이 지정가능.
    }

    public void listUpdate(){
        adapter.notifyDataSetChanged();
    }

}
