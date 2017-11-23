package com.example.mapl0.db_game_project.db.Game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;

import com.example.mapl0.db_game_project.Framework.AppManager;
import com.example.mapl0.db_game_project.Framework.GameActivity;
import com.example.mapl0.db_game_project.Framework.GameView;
import com.example.mapl0.db_game_project.Framework.GraphicObject;
import com.example.mapl0.db_game_project.Framework.IState;
import com.example.mapl0.db_game_project.R;

/**
 * Created by mapl0 on 2016-11-30.
 */

public class Rank_View extends AppCompatActivity implements IState {

    SQLiteDatabase db;

    GraphicObject BackGround= new GraphicObject(AppManager.getInstance().getBitmap(R.drawable.rank_background));

    public Rank_View(int score) {

        GameView gameView = AppManager.getInstance().getGameview();
        Context context = gameView.context;

        Intent intent = new Intent(context, Input_Name.class);

        intent.putExtra("SCORE", score);
        context.startActivity(intent);

    }

    @Override
    public void Init() {



    }

    @Override
    public void Destroy() {

    }

    @Override
    public void Update() {

    }

    @Override
    public void Renderer(Canvas canvas) {

    }

    @Override
    public boolean onKeyDown(int KeyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch(event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:

                break;

            case MotionEvent.ACTION_UP:

                break;
        }


        return true;

    }


}
