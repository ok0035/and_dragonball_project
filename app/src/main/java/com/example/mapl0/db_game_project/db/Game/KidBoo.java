package com.example.mapl0.db_game_project.db.Game;

import com.example.mapl0.db_game_project.Framework.AppManager;
import com.example.mapl0.db_game_project.Framework.SoundManager;
import com.example.mapl0.db_game_project.R;

/**
 * Created by mapl0 on 2016-11-12.
 */

public class KidBoo extends Enemy {

    public KidBoo(int life) {
        super(AppManager.getInstance().getBitmap(R.drawable.kid_boo_wait),
                AppManager.getInstance().getBitmap(R.drawable.kid_boo_fly),
                AppManager.getInstance().getBitmap(R.drawable.kid_boo_attack),
                AppManager.getInstance().getBitmap(R.drawable.kid_boo_fly));

        SetState(WAIT);
        UpdateState();

        hp = life;
        speed = 8f;
    }

    public void Update(long GameTime) {
        super.Update(GameTime);
        m_BoundBox.set(m_x, m_y, m_x + GetSpriteWidth(), m_y + GetSpriteHeight());
    }

    int move_sum = 0;
    @Override
    public void Move() {

        if(m_x <= - 100) dis_state = STATE_OUT;

        if(GetState() == WAIT && move_sum < 400) {
            m_x -= speed;
            move_sum += speed;
        }

        if(GetState() != ATTACK && move_sum >= 400) {
            SetState(ATTACK);
            UpdateState();
        }

        if(GetState() == FLY){
            m_x -= speed / 2;
        }

        SetPosition(m_x, m_y);
    }

    public void UpdateState() {

        switch(state) {

            case WAIT:

                mbReply = true;
                mbEnd = false;
                m_bitmap = b_wait;

                width = m_bitmap.getWidth();
                height = m_bitmap.getHeight();
                fps = 4;
                iFrame = 1;

                this.InitSpriteData(width, height, fps, iFrame);

                break;

            case FLY:

                mbReply = true;
                mbEnd = false;
                m_bitmap = b_fly;

                width = m_bitmap.getWidth() / 2;
                height = m_bitmap.getHeight();
                fps = 5;
                iFrame = 2;

                this.InitSpriteData(width, height, fps, iFrame);

                break;

            case ATTACK:
                mbReply = true;
                mbEnd = false;

                m_bitmap = b_attack;

                width = m_bitmap.getWidth() / 5;
                height = m_bitmap.getHeight();
                fps = 4;
                iFrame = 5;

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

    @Override
    public void Attack() {
        //공격하는 로직

        if( (GetState() == ATTACK) && (GetCurrentFrame() >= (iFrame-1)) &&
                System.currentTimeMillis() - LastShoot >= 1000 ) {
            LastShoot = System.currentTimeMillis();
            AppManager.getInstance().m_gamestate.blast_kidboo_list.add( new Blast_Ball(m_x - 250, m_y - 100, 3000 + hp/2) );

            SoundManager.getInstance().play(3);

            move_sum = 0;
            SetState(FLY);
            UpdateState();
        }
    }
}
