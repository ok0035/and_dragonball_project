package com.example.mapl0.db_game_project.db.Game;

import android.graphics.Rect;

/**
 * Created by mapl0 on 2016-11-13.
 */

public class CollisionManager {

    //Bound Box 충돌 방식
    public static boolean CheckBoxToBox(Rect _rt1, Rect _rt2) {
        if(
                _rt1.right > _rt2.left &&
                _rt1.left < _rt2.right &&
                _rt1.top < _rt2.bottom &&
                _rt1.bottom > _rt2.top ) return true;
        return false;
    }
}
