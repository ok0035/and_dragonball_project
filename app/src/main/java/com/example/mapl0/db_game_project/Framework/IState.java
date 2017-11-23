package com.example.mapl0.db_game_project.Framework;

import android.graphics.Canvas;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * Created by mapl0 on 2016-11-06.
 */

public interface IState {

    //이 상태로 바뀌었을 때 실행할 것들
    public void Init();

    //다른 상태로 바뀔 때 실행할 것들
    public void Destroy();

    //지속적으로 수행할 것들
    public void Update();

    //그려야 할 것들
    public void Renderer(Canvas canvas);

    //키 입력 처리
    public boolean onKeyDown(int KeyCode, KeyEvent event);

    //터치 입력 처리
    public boolean onTouchEvent(MotionEvent event);
}
