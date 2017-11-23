package com.example.mapl0.db_game_project.db.Game;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.mapl0.db_game_project.R;


/**
 * Created by mapl0 on 2016-12-02.
 */

public class Input_Name extends Activity {

    Button input_button;
    EditText input_text;

    DBHelper rank_data;

    String[] name = new String[50];
    int[] score = new int[50];
    public Cursor cursor;
    SQLiteDatabase db;

    int score_intent;

    String current_name;
    int current_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀제거

        setContentView(R.layout.input_rank);

        Intent intent = getIntent();

        score_intent = intent.getIntExtra("SCORE", 0);

        input_button = (Button)findViewById(R.id.input_rank);
        input_text = (EditText) findViewById(R.id.text_rank);

        input_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(input_text.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "이름을 입력하세요!!", Toast.LENGTH_SHORT).show();
                } else {

                    rank_data = new DBHelper();

                    db = rank_data.getWritableDatabase();
                    // 여기부터 데이터베이스 기록
                    //db.execSQL
                    //db.execSQL("DROP TABLE rank_table;");

                    db.execSQL("create table if not exists rank_table (" +
                            "NAME VARCHAR(2) NOT NULL," +
                            "SCORE INTEGER);");

                    db.execSQL("insert into rank_table values('" + input_text.getText().toString() + "', " + score_intent + ");");
                    //db.execSQL("delete ");

                    current_name = input_text.getText().toString();
                    current_score = score_intent;

                    cursor = db.rawQuery("select * " +
                            "from (select * from rank_table " +
                            "order by score desc) as a limit 50;", null
                    );

                    int i = 0;
                    while(cursor.moveToNext()) {

                        name[i] = cursor.getString(0);
                        score[i] = cursor.getInt(1);

                        i++;
                    }

                    db.close();
                    cursor.close();

                    Intent intent = new Intent(Input_Name.this, RankActivity.class);
                    intent.putExtra("NAME", name);
                    intent.putExtra("SCORE", score);
                    intent.putExtra("C_NAME", current_name);
                    intent.putExtra("C_SCORE", current_score);

                    startActivity(intent);

                    finish();
                }
            }
        });

    }
}
