package com.example.mapl0.db_game_project.db.Game;

import android.graphics.Bitmap;

import com.example.mapl0.db_game_project.Framework.AppManager;
import com.example.mapl0.db_game_project.R;

/**
 * Created by mapl0 on 2016-11-29.
 */

public class Blast_FinalFresh extends Blast {

    private int speed;
    private int hp;

    public Blast_FinalFresh(int x, int y, int life) {
        super(AppManager.getInstance().getBitmap(R.drawable.vegita_blast));

        speed = 15;
        hp = life;

        InitSpriteData(m_bitmap.getWidth()/3, m_bitmap.getHeight(), 7, 3);
        this.SetPosition(x, y);

    }

    @Override
    public void Update(long GameTime) {
        super.Update(GameTime);


        if(m_x <= -400 || m_y < -400 || m_y >= 1000) {
            state = STATE_OUT;
        }

        m_x -= speed;

        m_BoundBox.set(m_x + 50, m_y + 50, m_x - 50 + GetSpriteWidth(), m_y - 50 + GetSpriteHeight());
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
}
