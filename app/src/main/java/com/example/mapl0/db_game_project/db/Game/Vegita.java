package com.example.mapl0.db_game_project.db.Game;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.mapl0.db_game_project.Framework.AppManager;
import com.example.mapl0.db_game_project.Framework.GameView;
import com.example.mapl0.db_game_project.Framework.SoundManager;
import com.example.mapl0.db_game_project.R;

/**
 * Created by mapl0 on 2016-11-09.
 */

public class Vegita extends Enemy {

    public Vegita(int life) {
        super(AppManager.getInstance().getBitmap(R.drawable.vegita_wait),
                AppManager.getInstance().getBitmap(R.drawable.vegita_fly),
                AppManager.getInstance().getBitmap(R.drawable.vegita_attack),
                AppManager.getInstance().getBitmap(R.drawable.vegita_wait));

        SetState(WAIT);
        UpdateState();

        hp = life;
        speed = 6f;
        atk_damage = 3000;

        //GameView gameView = AppManager.getInstance().getGameview();
        //Context context = gameView.context;

        //SoundManager.getInstance().Init(context);
        //SoundManager.getInstance().addSound(3, R.raw.vegita_atk_sound);
    }

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

                width = m_bitmap.getWidth() / 4;
                height = m_bitmap.getHeight();
                fps = 7;
                iFrame = 4;

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
                width = m_bitmap.getWidth() / 3;
                height = m_bitmap.getHeight();
                fps = 2;
                iFrame = 3;

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
            AppManager.getInstance().m_gamestate.blast_vegita_list.add( new Blast_FinalFresh(m_x - 450, m_y - 80, 1500 + hp/3) );

            SoundManager.getInstance().play(3);

            move_sum = 0;
            SetState(FLY);
            UpdateState();
        }
    }
}
