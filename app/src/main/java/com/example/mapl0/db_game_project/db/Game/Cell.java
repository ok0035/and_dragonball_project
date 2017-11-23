package com.example.mapl0.db_game_project.db.Game;

import com.example.mapl0.db_game_project.Framework.AppManager;
import com.example.mapl0.db_game_project.Framework.SoundManager;
import com.example.mapl0.db_game_project.R;

/**
 * Created by mapl0 on 2016-11-12.
 */

public class Cell extends Enemy {

    public Cell(int life) {
        super(AppManager.getInstance().getBitmap(R.drawable.cell_wait),
                AppManager.getInstance().getBitmap(R.drawable.cell_fly),
                        AppManager.getInstance().getBitmap(R.drawable.cell_attack_2),
                                AppManager.getInstance().getBitmap(R.drawable.cell_wait));

        SetState(WAIT);
        UpdateState();

        hp = life;
        speed = 6f;
    }

    /*
    @Override
    public void Move() {
        //움직이는 로직

        if(m_x <= - 100) dis_state = STATE_OUT;

        if(m_x >= 800) {
            m_x -= speed;
        } else {

            /*
            if(GetState() != ATTACK) {
                SetState(ATTACK);
                UpdateState();
            }

            m_x -= speed * 2;
        }

        SetPosition(m_x, m_y);
    }
*/

    public void Update(long GameTime) {
        super.Update(GameTime);

        m_BoundBox.set(m_x, m_y, m_x + GetSpriteWidth(), m_y + GetSpriteHeight());
    }

    int move_sum = 0;
    @Override
    public void Move() {
        if(GetState() == WAIT && move_sum < 400) {
            m_x -= speed;
            move_sum += speed;
        }

        if(GetState() != ATTACK && move_sum >= 400) {
            SetState(ATTACK);
            UpdateState();
        }

        if(GetState() == FLY){
            m_x -= speed * 2;
        }

        SetPosition(m_x, m_y);
    }

    public void UpdateState() {

        switch(state) {

            case WAIT:

                mbReply = true;
                mbEnd = false;
                m_bitmap = b_wait;

                width = m_bitmap.getWidth() / 3;
                height = m_bitmap.getHeight();
                fps = 3;
                iFrame = 3;

                this.InitSpriteData(width, height, fps, iFrame);

                break;

            case FLY:

                mbReply = true;
                mbEnd = false;
                m_bitmap = b_fly;

                width = m_bitmap.getWidth();
                height = m_bitmap.getHeight();
                fps = 5;
                iFrame = 1;

                this.InitSpriteData(width, height, fps, iFrame);

                break;

            case ATTACK:
                mbReply = true;
                mbEnd = false;

                m_bitmap = b_attack;
                width = m_bitmap.getWidth() / 7;
                height = m_bitmap.getHeight();
                fps = 9;
                iFrame = 7;

                SoundManager.getInstance().play(4);

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

    int blast_count = 0;
    @Override
    public void Attack() {
        //공격하는 로직

        if( (GetState() == ATTACK) && (GetCurrentFrame() >= (iFrame-1)) ) {
            LastShoot = System.currentTimeMillis();
            AppManager.getInstance().m_gamestate.blast_cell_list.add( new Blast_Cell_Jr(m_x - 50, m_y + 50, 500 + hp/10) );
            blast_count ++;
        }

        if(blast_count == 5) {
            move_sum = 0;
            SetState(FLY);
            UpdateState();
        }
    }
}