package com.example.mapl0.db_game_project.db.Game;

import com.example.mapl0.db_game_project.Framework.AppManager;
import com.example.mapl0.db_game_project.R;

/**
 * Created by mapl0 on 2016-11-13.
 */

public class FatBoo extends Enemy {

    public FatBoo(int life) {
        super(AppManager.getInstance().getBitmap(R.drawable.fatboo_wait),
                AppManager.getInstance().getBitmap(R.drawable.fatboo_wait),
                AppManager.getInstance().getBitmap(R.drawable.fatboo_wait),
                AppManager.getInstance().getBitmap(R.drawable.fatboo_wait));


        this.InitSpriteData(m_bitmap.getWidth()/3, m_bitmap.getHeight(), 3, 3);

        hp = life;
        speed = 7f;
    }

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
            */
            m_x -= speed * 2;
        }

        SetPosition(m_x, m_y);
    }

    public void Update(long GameTime) {
        super.Update(GameTime);

        m_BoundBox.set(m_x, m_y, m_x + GetSpriteWidth(), m_y + GetSpriteHeight());
    }

    public void Attack() {
        //공격하는 로직

        if(System.currentTimeMillis() - LastShoot >= 550) {
            LastShoot = System.currentTimeMillis();
            AppManager.getInstance().m_gamestate.blast_enemy_list.add( new Blast_Enemy( m_x - 30, m_y + (m_bitmap.getHeight()/3), hp/20 ) );
        }
    }
}
