package com.example.mapl0.db_game_project.db.Game;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.example.mapl0.db_game_project.Framework.AppManager;
import com.example.mapl0.db_game_project.Framework.GameView;
import com.example.mapl0.db_game_project.Framework.GraphicObject;
import com.example.mapl0.db_game_project.Framework.IState;
import com.example.mapl0.db_game_project.R;

/**
 * Created by mapl0 on 2016-11-28.
 */

public class GameStart extends Activity implements IState {

    Bitmap b_start = AppManager.getInstance().getBitmap(R.drawable.start_menu);

    GraphicObject start_scene = new GraphicObject(AppManager.getInstance().getBitmap(R.drawable.game_start_));
    GraphicObject start_menu = new GraphicObject(b_start);

    public static GameStart gameStart;

    MediaPlayer m_Sound_BackGround;

    GameView gameView = AppManager.getInstance().getGameview();
    Context context = gameView.context;

    public GameStart() {

        GameView.m_Sound_BackGround = MediaPlayer.create(context, R.raw.start_sound);
        GameView.m_Sound_BackGround.setLooping(true);
        GameView.m_Sound_BackGround.setVolume(25, 25);
        //GameView.m_Sound_BackGround.start();
    }

    @Override
    public void Destroy() {

    }

    @Override
    public void Init() {


        start_menu.SetPosition(30, 55);

    }

    @Override
    public void Renderer(Canvas canvas) {

        start_scene.Draw(canvas);
        start_menu.Draw(canvas);
    }

    @Override
    public boolean onKeyDown(int KeyCode, KeyEvent event) {

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int px;
        int py;

        switch(event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_MOVE:

                px = (int)event.getX();
                py = (int)event.getY();

                if(px >= 30 && px <= (30 + b_start.getWidth())
                && py >= 50 && py <= (100 + b_start.getHeight())) {
                    start_menu.SetBitmap(AppManager.getInstance().getBitmap(R.drawable.start_menu_down));
                }
                break;

            case MotionEvent.ACTION_UP:

                px = (int)event.getX();
                py = (int)event.getY();

                if(px >= 30 && px <= (30 + b_start.getWidth())
                        && py >= 50 && py <= (100 + b_start.getHeight())) {

                    start_menu.SetBitmap(AppManager.getInstance().getBitmap(R.drawable.start_menu));

                    GameView.m_Sound_BackGround.pause();
                    GameView.m_Sound_BackGround.stop();
                    GameView.m_Sound_BackGround.release();


                    AppManager.getInstance().getGameview().ChangeGameState(new GameState());

                } else {
                    start_menu.SetBitmap(AppManager.getInstance().getBitmap(R.drawable.start_menu));
                }
                break;

        }

        return true;
    }

    @Override
    public void Update() {

    }
}
