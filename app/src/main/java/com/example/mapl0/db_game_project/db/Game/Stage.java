package com.example.mapl0.db_game_project.db.Game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.example.mapl0.db_game_project.Framework.AppManager;
import com.example.mapl0.db_game_project.Framework.GraphicObject;
import com.example.mapl0.db_game_project.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mapl0 on 2016-11-08.
 */

public class Stage extends GraphicObject {

    //Stage 구현
    static final float SCROLL_SPEED = 1f;
    private float m_scroll;

    //Stage List
    ArrayList<Bitmap> stageList = new ArrayList<Bitmap>();

    //이미지 오버랩
    private int m_over;
    private int m_overlap;

    //무한 스크롤링을 위한 변수
    private float m_scroll_2;

    //시차 스크롤링을 위한 변수
    static final float SCROLL_SPEED_2 = 1.5f;

    //구름
    private Bitmap m_cloud;
    private float m_scroll_3_1;
    private float m_scroll_3_2;


    public Stage(int backType) {
        super(null);

        //스테이지 초기화
        stageList.add(AppManager.getInstance().getBitmap(R.drawable.stage_1));

        //스테이지 변경
        if(backType == 0) {
            m_bitmap = AppManager.getInstance().getBitmap(R.drawable.stage_1);
        }

        //구름
        m_cloud = AppManager.getInstance().getBitmap(R.drawable.cloud);
        m_scroll_3_1 = 0;
        m_scroll_3_2 = m_cloud.getWidth() - m_overlap;

        //이미지와 이미지 사이를 매끄럽게 하기 위해 오버랩 시킴
        m_over = 0;
        m_overlap = 9;

        //Stage 무한 스크롤링을 위한 이미지 위치 지정
        m_scroll = 0;
        m_scroll_2 = m_bitmap.getWidth() - m_overlap;

    }

    public void AddState(Bitmap Stage) {

        stageList.add(Stage);
    }

    public void ChangeStage(int backType) {

        try {
            m_bitmap = stageList.get(backType);
        } catch(NullPointerException e) {
            System.out.println("ChangeState NULL Pointer Error");
        }
    }

    public int getBitmap() {

        return m_bitmap.getWidth();
    }

    @Override
    public void Draw(Canvas canvas) {

        //Stage
        canvas.drawBitmap(m_bitmap, m_scroll, m_y, null);
        canvas.drawBitmap(m_bitmap, m_scroll_2, m_y, null);

        //Cloud
        canvas.drawBitmap(m_cloud, m_scroll_3_1, m_y, null);
        canvas.drawBitmap(m_cloud, m_scroll_3_2, m_y, null);

    }

    void Update(long GameTime) {

        //Stage 이동속도
        m_scroll -= SCROLL_SPEED;
        m_scroll_2 -= SCROLL_SPEED;

        //Cloud 이동속도
        m_scroll_3_1 -= SCROLL_SPEED_2;
        m_scroll_3_2 -= SCROLL_SPEED_2;

        //Stage 붙이기
        if(m_scroll <= -(m_bitmap.getWidth() + m_over)) {
            m_over += 9;
            m_scroll = m_bitmap.getWidth() - m_over - m_overlap;
        }

        if(m_scroll_2 <= -(m_bitmap.getWidth() + m_over)) {
            m_over += 9;
            m_scroll_2 = m_bitmap.getWidth() - m_over - m_overlap;
        }

        //---------------------------------------------------------------
        // 구름 붙이기
        if(m_scroll_3_1 <= -(m_cloud.getWidth() + m_over)) {
            m_over += 9;
            m_scroll_3_1 = m_cloud.getWidth() - m_over - m_overlap;
        }

        if(m_scroll_3_2 <= -(m_cloud.getWidth() + m_over)) {
            m_over += 9;
            m_scroll_3_2 = m_cloud.getWidth() - m_over - m_overlap;
        }

        SetPosition((int)m_scroll, 0);
    }
}
