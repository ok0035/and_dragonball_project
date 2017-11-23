package com.example.mapl0.db_game_project.db.Game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import com.example.mapl0.db_game_project.Framework.AppManager;
import com.example.mapl0.db_game_project.Framework.SpriteAnimation;
import com.example.mapl0.db_game_project.R;

import java.util.Random;

import static android.R.attr.maxWidth;

/**
 * Created by mapl0 on 2016-11-09.
 */

public class Enemy extends SpriteAnimation {

    protected int hp = 1000;
    protected int atk_damage = 1000;
    protected float speed;

    //상태에 따른 이미지 비트맵
    protected Bitmap b_wait;
    protected Bitmap b_fly;
    protected Bitmap b_attack;
    protected Bitmap b_death;

    //적 캐릭터 동작
    public static final int WAIT = 0;
    public static final int FLY = 1;
    public static final int ATTACK = 2;
    public static final int DEATH = 3;

    protected int state = FLY;

    //적 캐릭터 움직임 패턴
    public static final int MOVE_PATTERN_1 = 0;
    public static final int MOVE_PATTERN_2 = 1;
    public static final int MOVE_PATTERN_3 = 2;


    //움직임별 비트맵 이미지 설정
    protected int width;
    protected int height;
    protected int iFrame;
    protected int fps;

    //화면 밖으로 나갔는지 체크
    public static final int STATE_NORMAL = 0;
    public static final int STATE_OUT = 1;

    public int dis_state = STATE_NORMAL;

    //적 기공파 설정
    long LastShoot = System.currentTimeMillis();

    //충돌체크
    Rect m_BoundBox = new Rect();

    public Enemy(Bitmap wait, Bitmap fly, Bitmap attack, Bitmap death) {
        super(wait);

        b_wait = wait;
        b_fly = fly;
        b_attack = attack;
        b_death = death;
    }

    @Override
    public void Update(long GameTime) {
        super.Update(GameTime);
        Move();
        Attack();
    }

    public void SetState(int state) {
        this.state = state;
    }

    public int GetState() {
        return state;
    }

    public int GetHP() {
        return hp;
    }

    public int GetDamage() {
        return atk_damage;
    }

    public boolean Attacked(int damage) {

        hp -= damage;

        if(hp <= 0) return true;
        else return false;

    }

    public void Move() {
        //움직이는 로직

        if(m_x <= - 100) dis_state = STATE_OUT;

    }

    public void UpdateState() {

    }

    public void Attack() {
        //공격하는 로직

    }


}
