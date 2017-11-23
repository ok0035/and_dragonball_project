package com.example.mapl0.db_game_project.db.Game;

import android.graphics.Bitmap;

import com.example.mapl0.db_game_project.Framework.AppManager;
import com.example.mapl0.db_game_project.Framework.SpriteAnimation;
import com.example.mapl0.db_game_project.R;

/**
 * Created by mapl0 on 2016-11-14.
 */

public class Effect_Explosion extends SpriteAnimation {
    public Effect_Explosion(int x, int y) {
        super(AppManager.getInstance().getBitmap(R.drawable.effect_explosion_2));
        this.InitSpriteData(m_bitmap.getWidth()/3, m_bitmap.getHeight(), 4, 3);

        m_x = x - 200;
        m_y = y - 160;

        mbReply = false;
    }
}
