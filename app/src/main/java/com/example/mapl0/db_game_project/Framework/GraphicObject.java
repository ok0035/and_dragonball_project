package com.example.mapl0.db_game_project.Framework;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by mapl0 on 2016-11-06.
 */

public class GraphicObject {

    protected Bitmap    m_bitmap;
    protected int       m_x;
    protected int       m_y;

    public GraphicObject(Bitmap bitmap) {
        m_bitmap = bitmap;
        m_x = 0;
        m_y = 0;
    }

    public void SetPosition(int x, int y) {

        m_x = x;
        m_y = y;
    }

    public void SetBitmap(Bitmap bitmap) {
        m_bitmap = bitmap;
    }

    public Bitmap GetBitmap() {
        return m_bitmap;
    }

    public int GetX() { return m_x; }
    public int GetY() { return m_y; }

    public void Draw(Canvas canvas) {

        canvas.drawBitmap(m_bitmap, m_x, m_y, null);
    }

    public int GetWidth() {
        return m_bitmap.getWidth();
    }

    public int GetHeight() {
        return m_bitmap.getHeight();
    }
}
