package com.example.mapl0.db_game_project.db.Game;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.example.mapl0.db_game_project.Framework.GraphicObject;
import com.example.mapl0.db_game_project.Framework.SpriteAnimation;

/**
 * Created by mapl0 on 2016-11-13.
 */

import com.example.mapl0.db_game_project.Framework.*;

public class Blast extends SpriteAnimation {

    //화면 밖으로 나갔는지 체크
    public static final int STATE_NORMAL = 0;
    public static final int STATE_OUT = 1;

    //충돌체크
    Rect m_BoundBox = new Rect();

    public int state = STATE_NORMAL;

    public Blast(Bitmap bitmap) {
        super(bitmap);
    }

}
