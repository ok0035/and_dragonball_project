package com.example.mapl0.db_game_project.db.Game;

import android.graphics.Bitmap;

import com.example.mapl0.db_game_project.Framework.AppManager;
import com.example.mapl0.db_game_project.R;

/**
 * Created by mapl0 on 2016-11-13.
 */

public class Blast_Player extends Blast {

    private int hp;
    private int speed;

    public Blast_Player(int x, int y, int dmg) {
        super(AppManager.getInstance().getBitmap(R.drawable.blast_base_2));
        this.InitSpriteData(m_bitmap.getWidth()/3, m_bitmap.getHeight(), 7, 3);

        hp = dmg;
        speed = 30;

        this.SetPosition(x, y);
    }

    public void Update(long GameTime) {
        super.Update(GameTime);

        m_x += speed;

        if(m_x >= 2000)
            state = STATE_OUT;

        m_BoundBox.left = m_x + 100;
        m_BoundBox.top = m_y + 50;
        m_BoundBox.right = m_x + m_bitmap.getWidth()/3 - 100;
        m_BoundBox.bottom = m_y + m_bitmap.getHeight() - 50;
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
