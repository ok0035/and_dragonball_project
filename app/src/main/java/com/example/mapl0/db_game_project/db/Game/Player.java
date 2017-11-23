package com.example.mapl0.db_game_project.db.Game;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.MediaPlayer;

import com.example.mapl0.db_game_project.Framework.AppManager;
import com.example.mapl0.db_game_project.Framework.GameView;
import com.example.mapl0.db_game_project.Framework.SoundManager;
import com.example.mapl0.db_game_project.R;
import com.example.mapl0.db_game_project.Framework.SpriteAnimation;

/**
 * Created by mapl0 on 2016-11-08.
 */

public class Player extends SpriteAnimation {

    //이동
    private boolean isMove;
    private int _dirX = 0;
    private int _dirY = 0;


    //애니메이션 초기화
    int width;
    int height;
    int fps;
    int iFrame;

    //캐릭터 동작
    public static final int WAIT = 0;
    public static final int FRONT_FLY = 1;
    public static final int BACK_FLY = 2;
    public static final int ATTACK = 3;
    public static final int DEATH = 4;
    public static final int KI = 5;

    //기 모으기
    private long last_ki = System.currentTimeMillis();
    private boolean isKi = false;

    private int state = 0;
    private boolean isDeath = false;

    //충돌체크
    Rect m_BoundBox = new Rect();

    private int hp = 15000;
    private int ki = 3000;
    private int damage = 5000;

    private int ki_var;

    public Player(int State) {
        super(AppManager.getInstance().getBitmap(R.drawable.goku_wait));

        width = m_bitmap.getWidth() / 3;
        height = m_bitmap.getHeight();
        fps = 5;
        iFrame = 3;

        ki_var = 10;

        this.InitSpriteData(width, height, fps, iFrame);
        this.SetPosition(300, 400);
    }

    public void Update(long GameTime) {
        super.Update(GameTime);

        if(state == ATTACK) Attack();
        if(state == DEATH) Death();

        if(isMove) {
            this.m_x += _dirX;
            this.m_y += _dirY;
        } else {
            _dirX = 0;
            _dirY = 0;
        }

        if(state != DEATH)
            m_BoundBox.set(m_x, m_y, m_x + GetSpriteWidth(), m_y + GetSpriteHeight());
        else
            m_BoundBox.setEmpty();
    }

    public int GetHP() {
        return hp;
    }

    public void AddHP(int h) {

        hp += h;

        if(hp <= 0) {
            hp = 0;
            SetPosition(m_x, m_y);
            SetState(DEATH);
            UpdateState();
            SetDeath(true);
        }
    }

    public boolean Attacked(int damage) {

        hp -= damage;

        if(hp <= 0) {
            hp = 0;
            SetPosition(m_x, m_y);
            SetState(DEATH);
            UpdateState();
            return true;

        } else return false;
    }

    public int GetDamage() {
        return damage;
    }
    public void SetDamage(int dmg) { damage = dmg; }

    public int GetKi() {
        return ki;
    }

    public int GetKi_Var() { return ki_var; }

    public void SetKi(int k) {
        ki = k;
    }

    public void AddKi(int ki) { ki_var += ki;}

    public int GetState() {
        return state;
    }

    public void SetState(int state) {
        this.state = state;
    }

    public void SetDeath(boolean death) {
        isDeath = death;
    }

    public boolean GetDeath() {
        return isDeath;
    }

    public void UpdateState() {

        if(!isDeath) {
            switch (state) {

                case WAIT:

                    ShowState();

                    mbReply = true;
                    mbEnd = false;
                    m_bitmap = AppManager.getInstance().getBitmap(R.drawable.goku_wait);
                    width = m_bitmap.getWidth() / 3;
                    height = m_bitmap.getHeight();
                    fps = 5;
                    iFrame = 3;

                    this.InitSpriteData(width, height, fps, iFrame);

                    break;

                case FRONT_FLY:

                    ShowState();

                    mbReply = true;
                    mbEnd = false;

                    m_bitmap = AppManager.getInstance().getBitmap(R.drawable.goku_front);
                    width = m_bitmap.getWidth();
                    height = m_bitmap.getHeight();
                    fps = 10;
                    iFrame = 1;

                    this.InitSpriteData(width, height, fps, iFrame);

                    break;

                case BACK_FLY:

                    ShowState();

                    mbReply = true;
                    mbEnd = false;

                    m_bitmap = AppManager.getInstance().getBitmap(R.drawable.goku_back);
                    width = m_bitmap.getWidth();
                    height = m_bitmap.getHeight();
                    fps = 10;
                    iFrame = 1;

                    this.InitSpriteData(width, height, fps, iFrame);

                    break;

                case ATTACK:

                    ShowState();

                    mbReply = false;
                    mbEnd = false;

                    m_bitmap = AppManager.getInstance().getBitmap(R.drawable.goku_attack);
                    width = m_bitmap.getWidth() / 2;
                    height = m_bitmap.getHeight();
                    fps = 7;
                    iFrame = 2;

                    this.InitSpriteData(width, height, fps, iFrame);

                    break;

                case KI:

                    ShowState();

                    mbReply = true;
                    mbEnd = false;
                    m_bitmap = AppManager.getInstance().getBitmap(R.drawable.goku_ki);
                    width = m_bitmap.getWidth() / 3;
                    height = m_bitmap.getHeight();
                    fps = 5;
                    iFrame = 3;

                    SoundManager.getInstance().play(6);

                    this.InitSpriteData(width, height, fps, iFrame);

                    break;


                case DEATH:

                    ShowState();

                    mbReply = false;
                    mbEnd = false;

                    m_bitmap = AppManager.getInstance().getBitmap(R.drawable.goku_death);
                    width = m_bitmap.getWidth() / 2;
                    height = m_bitmap.getHeight();
                    fps = 1;
                    iFrame = 2;
                    this.InitSpriteData(width, height, fps, iFrame);

                    isMove = false;

                    break;

                default:
                    break;
            }
        }
    }


    public void ki() {
        if( (System.currentTimeMillis() - last_ki >= 100) && GetState() != DEATH) {
            last_ki = System.currentTimeMillis();
            ki += ki_var;
        }
    }

    public void Attack() {

        if(getAnimationEnd() && (GetState() == ATTACK)) {

            AppManager.getInstance().m_gamestate.blast_player_list.add(new Blast_Player(GetX() + 80, GetY() - 70, GetDamage()));
            SetKi(GetKi() - 25);

            if(GetKi() < 0)
                SetKi(0);

            SetState(Player.WAIT);
            UpdateState();
        }
    }

    public void Death() {
        if (getAnimationEnd() && state == DEATH) {
            SetKi(0);
            SetDeath(true);
        }
    }



    public void startMove(int dirX, int dirY) {

        //움직임을 활성화
        isMove = true;

        if (dirX >= 0) {
            SetState(Player.FRONT_FLY);
            UpdateState();
        } else {
            SetState(Player.BACK_FLY);
            UpdateState();
        }

        //방향값을 저장
        _dirX = dirX;
        _dirY = dirY;
    }

    public void stopMove() {

        //움직임을 비활성화시켜준다.
        isMove = false;
        isKi = false;

        //방향값을 초기화시킨다.
        _dirX = 0;
        _dirY = 0;

        SetPosition(m_x, m_y);
    }

    public void ShowState() {

        switch (state) {

            case WAIT:
                System.out.println("현재 상태 : 대기 상태");
                break;

            case FRONT_FLY:
            case BACK_FLY:
                System.out.println("현재 상태 : 움직이는 상태");
                break;

            case ATTACK:
                System.out.println("현재 상태 : 공격 상태");
                break;

            case DEATH:
                System.out.println("현재 상태 : 죽음");
                break;
        }

    }


}
