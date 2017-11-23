package com.example.mapl0.db_game_project.Framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by mapl0 on 2016-11-06.
 */

public class SpriteAnimation extends GraphicObject {

    private Rect m_Rect; // 그려줄 사각 영역
    private int m_fps; // 초당 프레임
    private int mNoOfFrames; // 프레임 개수
    private long m_FrameTimer; // 이전 시간 정보를 담을 변수

    private int m_CurrentFrame; // 최근 프레임;
    private int m_SpriteWidth;
    private int m_SpriteHeight;

    //애니메이션 조작
    protected boolean mbReply= true;
    protected boolean mbEnd = false;

    public SpriteAnimation(Bitmap bitmap) {
        super(bitmap);

        m_Rect = new Rect(0,0,0,0);
        m_CurrentFrame = 0;
        m_FrameTimer = 0;

    }

    public void InitSpriteData(int _width, int _height, int _fps, int theFrameCount) {

        m_SpriteWidth = _width;
        m_SpriteHeight = _height;

        m_Rect.top = 0;
        m_Rect.bottom =  m_SpriteHeight;
        m_Rect.left = 0;
        m_Rect.right = m_SpriteWidth;

        m_fps = 1000/_fps;
        mNoOfFrames = theFrameCount;

        m_CurrentFrame = 0;
    }

    public void Update(long GameTime) {

        if(!mbEnd) {

            if (GameTime > m_FrameTimer + m_fps) {
                m_FrameTimer = GameTime;
                m_CurrentFrame += 1;

                if (m_CurrentFrame >= mNoOfFrames) {
                    if (mbReply)
                        m_CurrentFrame = 0;
                    else {
                        mbEnd = true;
                    }
                }
            }
            m_Rect.left = m_CurrentFrame * m_SpriteWidth;
            m_Rect.right = m_Rect.left + m_SpriteWidth;
        }
    }

    public void SetCurrentFrame(int frame) {
        if(mNoOfFrames <= frame) m_CurrentFrame = mNoOfFrames;
        else mNoOfFrames = frame;
    }


    @Override
    public void Draw(Canvas canvas) {
        Rect dest = new Rect(m_x, m_y, m_x + m_SpriteWidth, m_y + m_SpriteHeight);
        canvas.drawBitmap(m_bitmap, m_Rect, dest, null);
    }

    public int GetSpriteWidth() {
        return m_SpriteWidth;
    }

    public int GetSpriteHeight() { return m_SpriteHeight; }

    public boolean getAnimationEnd() {
        return mbEnd;
    }

    public int GetCurrentFrame() {
        return m_CurrentFrame;
    }
}
