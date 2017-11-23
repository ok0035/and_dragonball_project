package com.example.mapl0.db_game_project.Framework;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by mapl0 on 2016-11-05.
 */

//그림을 관리하는 클래스
public class GameViewThread extends Thread {

    //접근을 위한 멤버 변수
    private SurfaceHolder m_surfaceHolder;
    private GameView m_gameview;

    //스레드 실행 상태 멤버 변수
    private boolean m_run = false;

    public GameViewThread(SurfaceHolder surfaceHolder, GameView gameView) {
        m_surfaceHolder = surfaceHolder;
        m_gameview = gameView;
    }

    public void setRunning(boolean run) {

        m_run= run;
    }

    @Override
    public void run() {

        Canvas _canvas;

        while(m_run) {
            _canvas = null;
            try {

                m_gameview.Update();

                //SurfaceHolder를 통해 Surface에 접근해서 가져옴
                _canvas = m_surfaceHolder.lockCanvas(null);
                synchronized (m_surfaceHolder) {
                    m_gameview.onDraw(_canvas); //그림을 그림
                }

            } finally {
                if(_canvas != null) {
                    //Surface를 화면에 표시함
                    m_surfaceHolder.unlockCanvasAndPost(_canvas);
                }
            }
        }
    }
}
