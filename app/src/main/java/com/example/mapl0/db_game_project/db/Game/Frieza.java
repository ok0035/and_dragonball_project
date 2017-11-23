package com.example.mapl0.db_game_project.db.Game;

import com.example.mapl0.db_game_project.Framework.AppManager;
import com.example.mapl0.db_game_project.R;

/**
 * Created by mapl0 on 2016-11-12.
 */

public class Frieza extends Enemy {

    public Frieza(int life) {
        super(AppManager.getInstance().getBitmap(R.drawable.frieza_wait),
                AppManager.getInstance().getBitmap(R.drawable.frieza_fly),
                AppManager.getInstance().getBitmap(R.drawable.frieza_attack2),
                AppManager.getInstance().getBitmap(R.drawable.frieza_wait));

        SetState(FLY);
        UpdateState();

        this.InitSpriteData(m_bitmap.getWidth()/3, m_bitmap.getHeight(), 6, 3);

        hp = life;
        speed = 7f;
    }

    public void Update(long GameTime) {
        super.Update(GameTime);

        m_BoundBox.set(m_x, m_y, m_x + GetSpriteWidth(), m_y + GetSpriteHeight());
    }

    @Override
    public void Move() {
        //움직이는 로직

        if(m_x <= - 100) dis_state = STATE_OUT;

        if(m_x >= 1200) {
            m_x -= speed;
        } else {
            if(GetState() != ATTACK) {
                SetState(ATTACK);
                UpdateState();
            }
            m_x -= speed * 3;
        }

        SetPosition(m_x, m_y);
    }

    public void UpdateState() {

        switch(state) {

            case WAIT:

                mbReply = true;
                mbEnd = false;
                m_bitmap = b_wait;

                width = m_bitmap.getWidth() / 5;
                height = m_bitmap.getHeight();
                fps = 5;
                iFrame = 5;

                this.InitSpriteData(width, height, fps, iFrame);

                break;

            case FLY:

                mbReply = true;
                mbEnd = false;

                m_bitmap = b_fly;
                width = m_bitmap.getWidth()/3;
                height = m_bitmap.getHeight();
                fps = 5;
                iFrame = 3;

                this.InitSpriteData(width, height, fps, iFrame);

                break;

            case ATTACK:
                mbReply = true;
                //mbEnd = false;

                m_bitmap = b_attack;
                width = m_bitmap.getWidth() / 2;
                height = m_bitmap.getHeight();
                fps = 6;
                iFrame = 2;

                this.InitSpriteData(width, height, fps, iFrame);

                break;

            case DEATH:

                mbReply = false;
                m_bitmap = b_death;
                width = m_bitmap.getWidth() / 2;
                height = m_bitmap.getHeight();
                fps = 2;
                iFrame = 2;
                this.InitSpriteData(width, height, fps, iFrame);

                break;

            default:
                break;
        }
    }

    public void Attack() {
        //공격하는 로직

        if(System.currentTimeMillis() - LastShoot >= 550) {
            LastShoot = System.currentTimeMillis();
            AppManager.getInstance().m_gamestate.blast_enemy_list.add( new Blast_Enemy( m_x - 30, m_y + (m_bitmap.getHeight()/3), 250 + hp/20 ) );
        }
    }
}
