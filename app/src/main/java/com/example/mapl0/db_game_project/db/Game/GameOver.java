package com.example.mapl0.db_game_project.db.Game;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.TextView;

import com.example.mapl0.db_game_project.Framework.AppManager;
import com.example.mapl0.db_game_project.Framework.GraphicObject;
import com.example.mapl0.db_game_project.Framework.IState;
import com.example.mapl0.db_game_project.R;

/**
 * Created by mapl0 on 2016-11-28.
 */

public class GameOver implements IState {

    GraphicObject game_over;
    int Score;
    String name;
    public Context context;

    public GameOver(int s) {
        game_over = new GraphicObject(AppManager.getInstance().getBitmap(R.drawable.game_over));
        Score = s;
    }

    @Override
    public void Destroy() {

    }

    @Override
    public void Init() {

        game_over.SetPosition(0,0);
    }

    @Override
    public boolean onKeyDown(int KeyCode, KeyEvent event) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:

                AppManager.getInstance().getGameview().ChangeGameState(new Rank_View(Score));
                break;
        }

        return true;
    }

    @Override
    public void Renderer(Canvas canvas) {
        game_over.Draw(canvas);

        Paint p = new Paint();
        p.setTextSize(200);
        p.setColor(Color.RED);

        canvas.drawText("GAME OVER", 100, 300, p);

        p.setTextSize(50);
        p.setColor(Color.BLACK);
        canvas.drawText("화면을 터치하면 랭킹을 볼 수 있어요!", 100, 500, p);
    }

    @Override
    public void Update() {

    }
}
