package com.example.mapl0.db_game_project.db.Game;

import android.graphics.Bitmap;

import com.example.mapl0.db_game_project.Framework.AppManager;
import com.example.mapl0.db_game_project.R;

/**
 * Created by mapl0 on 2016-11-29.
 */

public class Blast_Ball extends Blast {

    private int speed;
    private int hp;

    public Blast_Ball(int x, int y, int life) {
        super(AppManager.getInstance().getBitmap(R.drawable.kid_boo_blast));

        speed = 7;
        hp = life;

        InitSpriteData(m_bitmap.getWidth(), m_bitmap.getHeight(), 5, 1);
        this.SetPosition(x, y);
    }

    @Override
    public void Update(long GameTime) {
        super.Update(GameTime);

        if(m_x <= -400 || m_y < -400 || m_y >= 1000) {
            state = STATE_OUT;
        }

        m_x -= speed;

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
        int h = hp;
        hp = 0;

        return h;
    }
}
