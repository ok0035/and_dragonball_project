package com.example.mapl0.db_game_project.db.Game;

import android.graphics.Bitmap;

import com.example.mapl0.db_game_project.Framework.AppManager;
import com.example.mapl0.db_game_project.R;

import java.util.Random;

/**
 * Created by mapl0 on 2016-11-14.
 */

public class Blast_Enemy extends Blast {

    private int hp;

    Random blast_rand = new Random();
    boolean isRandom = true;

    int speed = 0;

    public Blast_Enemy(int x, int y, int life) {
        super(AppManager.getInstance().getBitmap(R.drawable.blast_enemy));

        hp = life;

        InitSpriteData(m_bitmap.getWidth(), m_bitmap.getHeight(), 1, 1);
        this.SetPosition(x, y);
    }

    private double atan = Math.atan2(new Random().nextInt() % 22,
            new Random().nextInt(100));

    private int dx = (int)(Math.cos( atan ) * 12);
    private int dy = (int)(Math.sin( atan ) * 12);

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
