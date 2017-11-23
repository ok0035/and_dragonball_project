package com.example.mapl0.db_game_project.Framework;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;

import com.example.mapl0.db_game_project.R;
import com.example.mapl0.db_game_project.db.Game.GameStart;
import com.example.mapl0.db_game_project.db.Game.GameState;
import com.example.mapl0.db_game_project.db.Game.Rank_View;

/**
 * Created by mapl0 on 2016-11-05.
 */

//화면을 보여줄 View 클래스
public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    private GameViewThread m_thread;
    public Context context;
    private IState m_state;

    public static MediaPlayer m_Sound_BackGround;

    public GameView(Context context, int flag) {
        super(context);

        if(flag == 1) {
            System.exit(0);
        }

        this.context = context;

        setFocusable(true);
        setClickable(true);

        getHolder().addCallback(this);
        m_thread = new GameViewThread(getHolder(), this);

        SoundManager.getInstance().Init(this.context);
        SoundManager.getInstance().addSound(1, R.raw.collision_sound);
        SoundManager.getInstance().addSound(2, R.raw.goku_death_sound);
        SoundManager.getInstance().addSound(3, R.raw.blast_atk_sound);
        SoundManager.getInstance().addSound(4, R.raw.cell_attack_sound);
        SoundManager.getInstance().addSound(5, R.raw.goku_thank_you);
        SoundManager.getInstance().addSound(6, R.raw.goku_ki_sound);

        AppManager.getInstance().setGameView(this);
        AppManager.getInstance().setResources(getResources());
        ChangeGameState(new GameStart());
    }

    public void ChangeGameState(IState _state) {

        if (m_state != null)
            m_state.Destroy();

        _state.Init();
        m_state = _state;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        //스레드를실행 상태로 만듬
        m_thread.setRunning(true);
        //스레드 실행
        try {
            m_thread.start();
        } catch (IllegalThreadStateException e) {}
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        boolean retry = true;
        m_thread.setRunning(false);

        while(retry) {
            try {

                //스레드를 중지
                m_thread.join();
                retry = false;

            } catch (InterruptedException e) {

            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if(canvas != null) {
            canvas.drawColor(Color.BLACK);
            m_state.Renderer(canvas);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        m_state.onKeyDown(keyCode, event);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        m_state.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    public void Update() {

        m_state.Update();
    }
}
