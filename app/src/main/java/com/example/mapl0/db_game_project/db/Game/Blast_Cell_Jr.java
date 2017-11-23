package com.example.mapl0.db_game_project.db.Game;

import android.graphics.Bitmap;

import com.example.mapl0.db_game_project.Framework.AppManager;
import com.example.mapl0.db_game_project.R;

import java.util.Random;

/**
 * Created by mapl0 on 2016-11-29.
 */

public class Blast_Cell_Jr extends Blast {

    private int speed;
    private int hp;

    private double atan;

    private int dx;
    private int dy;

    public Blast_Cell_Jr(int x, int y, int life) {
        super(AppManager.getInstance().getBitmap(R.drawable.cell_blast));

        atan = Math.atan2(new Random().nextInt() % 20,
                new Random().nextInt(100));

        speed = 10;
        hp = life;

        dx = (int)(Math.cos( atan ) * speed);
        dy = (int)(Math.sin( atan ) * speed);

        InitSpriteData(m_bitmap.getWidth()/2, m_bitmap.getHeight(), 5, 2);
        this.SetPosition(x, y);

    }

    @Override
    public void Update(long GameTime) {
        super.Update(GameTime);

        if(m_x <= -400 || m_y < -400 || m_y >= 1000) {
            state = STATE_OUT;
        }

        if (dx < 0) dx = -dx;

        m_x -= dx;
        m_y += dy;

        m_BoundBox.set(m_x, m_y, m_x + GetSpriteWidth(), m_y + GetSpriteHeight());
    }

    public int GetHP() {
        return hp;
    }

    public void SetHP(int life) {
        hp = life;
    }

    public boolean Attacked(int damage) {
        hp -= damage;

        if(hp <= 0) return true;
        else return false;
    }

    public int GetDamage() {
        int h =hp;
        hp = 0;

        return h;
    }
}
