package com.example.mapl0.db_game_project.Framework;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;

import com.example.mapl0.db_game_project.db.Game.BackPressCloseHandler;

public class GameActivity extends Activity {

    private BackPressCloseHandler backPressCloseHandler;

    public static Activity gameActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀제거
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE); // 가로모드

        gameActivity = GameActivity.this;

        AppManager.getInstance().setGameView(new GameView(this, 0));

        setContentView(AppManager.getInstance().getGameview());

        backPressCloseHandler = new BackPressCloseHandler(this);

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        backPressCloseHandler.onBackPressed();
    }
}
