package com.example.mapl0.db_game_project.db.Game;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.content.Context;

import com.example.mapl0.db_game_project.Framework.*;
import com.example.mapl0.db_game_project.R;

import java.util.ArrayList;
import java.util.Random;


/**
 * Created by mapl0 on 2016-11-08.
 */

public class GameState extends Activity implements IState {

    //스테이지
    private Stage m_stage;

    //플레이어
    private Player m_player;

    //좌표
    private double atan;
    private double r;

    private int px = 0;
    private int py = 0;
    private int px2 = 0;
    private int py2 = 0;

    private int dx = 0;
    private int dy = 0;

    //랭크 기록
    Rank_View rank_view;

    /*리스트*/

    //기공파
    ArrayList<Blast_Player> blast_player_list = new ArrayList<Blast_Player>();

    //Enemy List
    ArrayList<Enemy> enemyList = new ArrayList<Enemy>();

    //적 기공파
    ArrayList<Blast_Enemy> blast_enemy_list = new ArrayList<Blast_Enemy>();
    ArrayList<Blast_FinalFresh> blast_vegita_list = new ArrayList<Blast_FinalFresh>();
    ArrayList<Blast_Ball> blast_kidboo_list = new ArrayList<Blast_Ball>();
    ArrayList<Blast_Cell_Jr> blast_cell_list = new ArrayList<Blast_Cell_Jr>();

    //Effect
    ArrayList<Effect_Explosion> eft_list = new ArrayList<Effect_Explosion>();

    //적 타입
    Random randEnemy = new Random();

    //적 나오는 속도
    private int make_speed;

    //적 체력
    private int enemy_hp;

    //지난 프레임
    private long LastRegenEnemy =  System.currentTimeMillis();
    private long last_frame = System.currentTimeMillis();
    private long atk_last_frame = System.currentTimeMillis();

    //스킬들
    private GraphicObject blast_icon = new GraphicObject(AppManager.getInstance().getBitmap(R.drawable.blast_icon));

    //스킬을 사용할 기가 부족할때
    private boolean isLack = false;

    //D-PAD
    private GraphicObject d_pad_center = new GraphicObject(AppManager.getInstance().getBitmap(R.drawable.center));
    private GraphicObject d_pad_circle = new GraphicObject(AppManager.getInstance().getBitmap(R.drawable.pad));

    //SCORE
    private int score = 0;
    private int addScore = 0;

    //스코어바
    GraphicObject bar = new GraphicObject(AppManager.getInstance().getBitmap(R.drawable.bar));

    //Time Bar
    private int time = 0;

    //플레이어 기 증가
    private int kx = 5;

    //플레이어 죽음 판정
    private boolean is_player_death = false;

    GameView gameView = AppManager.getInstance().getGameview();
    Context context = gameView.context;

    //public MediaPlayer m_Sound_BackGround;

    public GameState() {
        AppManager.getInstance().m_gamestate = this;

        GameView.m_Sound_BackGround = MediaPlayer.create(context, R.raw.main_sound);
        GameView.m_Sound_BackGround.setLooping(true);
        GameView.m_Sound_BackGround.setVolume(25, 25);
        GameView.m_Sound_BackGround.start();
    }

    @Override
    public void Init() {

        m_player = new Player(0);
        m_stage = new Stage(0);

        blast_icon.SetPosition(1600, 850);
        d_pad_circle.SetPosition(100, 600);
        d_pad_center.SetPosition(245, 750);
        bar.SetPosition(0,0);

        make_speed = 1500;
        enemy_hp = 1000;

    }

    @Override
    public void Destroy() {

    }

    @Override
    public void Update() {

        long GameTime = System.currentTimeMillis();

        m_stage.Update(GameTime);
        m_player.Update(GameTime);

        for(int i = enemyList.size()-1; i>=0; i--) {
            Enemy enemy = enemyList.get(i);
            enemy.Update(GameTime);
            if(enemy.dis_state == Enemy.STATE_OUT) {
                enemyList.remove(i);
                m_player.Attacked(300 + addScore / 5);
                if(score < 0) score = 0;
            }
        }

        for(int i = blast_player_list.size()-1; i >= 0; i--) {
            Blast_Player blast_player = blast_player_list.get(i);
            blast_player.Update(GameTime);
            if(blast_player.state == Blast.STATE_OUT) {
                blast_player_list.remove(i);
            }
        }

        // 여기부터 적 블래스트 처리 ----------------------------

        //기본
        for(int i = blast_enemy_list.size()-1; i >= 0; i--) {
            Blast_Enemy blast_enemy = blast_enemy_list.get(i);
            blast_enemy.Update(GameTime);
            if(blast_enemy.state == Blast.STATE_OUT) {
                blast_enemy_list.remove(i);
            }
        }

        //베지터
        for(int i = blast_vegita_list.size()-1; i >= 0; i--) {
            Blast_FinalFresh blast_vegita = blast_vegita_list.get(i);
            blast_vegita.Update(GameTime);
            if(blast_vegita.state == Blast.STATE_OUT) {
                blast_vegita_list.remove(i);
            }
        }

        //키드부우
        for(int i = blast_kidboo_list.size()-1; i >= 0; i--) {
            Blast_Ball ball = blast_kidboo_list.get(i);
            ball.Update(GameTime);
            if(ball.state == Blast.STATE_OUT) {
                blast_kidboo_list.remove(i);
            }
        }

        //셀
        for(int i = blast_cell_list.size()-1; i >= 0; i--) {
            Blast_Cell_Jr cell = blast_cell_list.get(i);
            cell.Update(GameTime);
            if(cell.state == Blast.STATE_OUT) {
                blast_cell_list.remove(i);
            }
        }

        // --------------------- 여기까지 ------------------------

        for(int i = eft_list.size() - 1; i >= 0; i--) {
            Effect_Explosion exp = eft_list.get(i);
            exp.Update(GameTime);
            if(exp.getAnimationEnd()) {
                if(m_player.GetState() == Player.ATTACK) {
                    eft_list.remove(i);
                }
            }
        }

        if(System.currentTimeMillis() - last_frame >= 1000 ) {
            last_frame = System.currentTimeMillis();

            if(time != 0 && time % 60 == 0) {

                SoundManager.getInstance().play(5);

                if(m_player.GetKi_Var() < 40)
                    m_player.AddKi(5);

                m_player.AddHP(8000 + (addScore * 5));

                score += (10000 + (addScore * 10) );

                addScore += 250;

                if(make_speed > 750)
                    make_speed -= 250;

                enemy_hp += (2000 + addScore);

            }

            time ++;
        }

        //점수에 따른 전투력 계산
        m_player.SetDamage(5000 + (score * 1/65));

        //죽음 조건
        if(m_player.GetDeath() == true) {
            m_player.SetKi(0);

            AppManager.getInstance().getGameview().ChangeGameState(new GameOver(score));

            GameView.m_Sound_BackGround.stop();
            GameView.m_Sound_BackGround.release();

            GameView.m_Sound_BackGround = MediaPlayer.create(context, R.raw.game_over_sound);
            GameView.m_Sound_BackGround.setLooping(true);
            GameView.m_Sound_BackGround.start();
        }

        if(m_player.GetState() == Player.DEATH && is_player_death == false) {
            is_player_death = true;
            SoundManager.getInstance().play(2);
        }

        if(m_player.GetState() == Player.KI) {
            m_player.ki();
        }

        BoundaryCheck();
        MakeEnemy();
        CheckCollision();
    }

    public void BoundaryCheck() {

        if( ( m_player.GetX() < 0) )
            m_player.SetPosition(0, m_player.GetY());

        if( m_player.GetX() > 1750 )
            m_player.SetPosition(1750, m_player.GetY());

        if( (m_player.GetY() < 20) )
            m_player.SetPosition(m_player.GetX(), 20);

        if(m_player.GetY() > 700)
            m_player.SetPosition(m_player.GetX(), 700);

    }

    @Override
    public void Renderer(Canvas canvas) {

        //스테이지 Draw
        m_stage.Draw(canvas);

        //적 리스트 Draw
        for(Enemy enemy : enemyList) {
            enemy.Draw(canvas);
        }

        //플레이어 Draw
        m_player.Draw(canvas);

        //플레이어 Blast Draw
        for(int i = blast_player_list.size() - 1; i >= 0; i--) {
            if(blast_player_list.get(i) != null)
                blast_player_list.get(i).Draw(canvas);
        }


        //적 Blast ------------------------------------------------

        //기본
        for(int i = blast_enemy_list.size() - 1; i >= 0; i--) {
            if(blast_enemy_list.get(i) != null)
                blast_enemy_list.get(i).Draw(canvas);
        }

        //셀
        for(int i = blast_cell_list.size() - 1; i >= 0; i--) {
            if(blast_cell_list.get(i) != null)
                blast_cell_list.get(i).Draw(canvas);
        }

        //베지터
        for(int i = blast_vegita_list.size() - 1; i >= 0; i--) {
            if(blast_vegita_list.get(i) != null)
                blast_vegita_list.get(i).Draw(canvas);
        }

        //키드부우
        for(int i = blast_kidboo_list.size() - 1; i >= 0; i--) {
            if(blast_kidboo_list.get(i) != null)
                blast_kidboo_list.get(i).Draw(canvas);
        }

        // ---------------------------------------------

        for(int i = eft_list.size() - 1; i >= 0; i--) {
            if(eft_list.get(i) != null)
                eft_list.get(i).Draw(canvas);
        }

        //스킬들
        blast_icon.Draw(canvas);

        //방향키 D-PAD
        d_pad_circle.Draw(canvas);
        d_pad_center.Draw(canvas);

        bar.Draw(canvas);

        //체력 표시
        Paint p = new Paint();

        p.setTextSize(50);
        p.setColor(Color.WHITE);

        canvas.drawText("HP : " + String.valueOf(m_player.GetHP()) +
                "        KI : " + String.valueOf(m_player.GetKi()) +
                "        POWER : " + m_player.GetDamage() +
                "        SCORE : " + score +
                "        TIME : " + time, 0, 40, p);

        p.setTextSize(50);

        if(isLack == true) {
            canvas.drawText("스킬을 사용할 기가 부족합니다.", 650, 500, p);
        } else p = null;
    }

    public void MakeEnemy() {

        if(System.currentTimeMillis() - LastRegenEnemy >= make_speed ) {
            LastRegenEnemy = System.currentTimeMillis();
            int enemyType = randEnemy.nextInt(5);
            Enemy enemy;

            switch(enemyType) {
                case 0:
                    enemy = new Vegita(enemy_hp + 900);
                    break;

                case 1:
                    enemy = new KidBoo(enemy_hp + 1500);
                    break;

                case 2:
                    enemy = new Frieza(enemy_hp + 500);
                    break;

                case 3:
                    enemy = new Cell(enemy_hp + 700);
                    break;

                case 4:
                    enemy = new FatBoo(enemy_hp + 1200);
                    break;

                default:
                    enemy = new Frieza(enemy_hp + 500);
                    break;

            }

            enemy.SetPosition(1920, (300 * new Random().nextInt(3)) + 100);
            enemyList.add(enemy);
        }
    }

    public void CheckCollision() {

        //플레이어 기공파와 적 캐릭터가 닿았을때
        for(int i = blast_player_list.size() - 1; i >= 0; i--) {
            for(int j = enemyList.size() - 1; j >= 0; j--) {
                if(CollisionManager.CheckBoxToBox(blast_player_list.get(i).
                        m_BoundBox, enemyList.get(j).m_BoundBox)) {

                    Blast_Player player_blast = blast_player_list.get(i);
                    Enemy enemy = enemyList.get(j);

                    int player_damage = player_blast.GetHP();
                    int blast_damage = enemy.GetHP();

                    boolean player_flag = player_blast.Attacked(blast_damage);
                    boolean enemy_flag = enemy.Attacked(player_damage);

                    if(player_flag) {
                        eft_list.add(new Effect_Explosion(player_blast.GetX() + 120, player_blast.GetY() + 55));
                        blast_player_list.remove(i);
                    }

                    if(enemy_flag) {
                        eft_list.add(new Effect_Explosion(enemy.GetX(), enemy.GetY()));
                        enemyList.remove(j);
                        score += (300 + addScore / 5);
                    }

                    SoundManager.getInstance().play(1);

                    return;
                }
            }
        }

        //플레이어 기공파와 적 기공파(기본)가 닿았을 때
        for(int i = blast_player_list.size() - 1; i >= 0; i--) {
            for(int j = blast_enemy_list.size() - 1; j >= 0; j--) {
                if(CollisionManager.CheckBoxToBox(blast_player_list.get(i).
                        m_BoundBox, blast_enemy_list.get(j).m_BoundBox)) {

                    Blast_Player player_blast = blast_player_list.get(i);
                    Blast_Enemy enemy_blast = blast_enemy_list.get(j);

                    int player_damage = player_blast.GetHP();
                    int blast_damage = enemy_blast.GetHP();

                    boolean player_flag = player_blast.Attacked(blast_damage);
                    boolean enemy_flag = enemy_blast.Attacked(player_damage);

                    if(player_flag) {
                        eft_list.add(new Effect_Explosion(player_blast.GetX() + 120, player_blast.GetY() + 55));
                        blast_player_list.remove(i);
                    }

                    if(enemy_flag) {
                        eft_list.add(new Effect_Explosion(enemy_blast.GetX(), enemy_blast.GetY()));
                        blast_enemy_list.remove(j);
                        score += 100 + (addScore / 20);
                    }
                    return;
                }
            }
        }

        //플레이어 기공파와 적 기공파(파이널 프레쉬)가 닿았을 때
        for(int i = blast_player_list.size() - 1; i >= 0; i--) {
            for(int j = blast_vegita_list.size() - 1; j >= 0; j--) {
                if(CollisionManager.CheckBoxToBox(blast_player_list.get(i).
                        m_BoundBox, blast_vegita_list.get(j).m_BoundBox)) {

                    Blast_Player player_blast = blast_player_list.get(i);
                    Blast_FinalFresh vegita_blast = blast_vegita_list.get(j);

                    int player_damage = player_blast.GetHP();
                    int blast_damage = vegita_blast.GetHP();

                    boolean player_flag = player_blast.Attacked(blast_damage);
                    boolean vegita_flag = vegita_blast.Attacked(player_damage);

                    if(player_flag) {
                        eft_list.add(new Effect_Explosion(player_blast.GetX() + 120, player_blast.GetY() + 55));
                        blast_player_list.remove(i);
                    }

                    if(vegita_flag) {
                        eft_list.add(new Effect_Explosion(vegita_blast.GetX(), vegita_blast.GetY()));
                        score += 500 + addScore/5;
                        blast_vegita_list.remove(j);
                    }

                    SoundManager.getInstance().play(1);

                    return;
                }
            }
        }

        //플레이어 기공파와 적 기공파(셀 주니어)가 닿았을 때
        for(int i = blast_player_list.size() - 1; i >= 0; i--) {
            for(int j = blast_cell_list.size() - 1; j >= 0; j--) {
                if(CollisionManager.CheckBoxToBox(blast_player_list.get(i).
                        m_BoundBox, blast_cell_list.get(j).m_BoundBox)) {

                    Blast_Player player_blast = blast_player_list.get(i);
                    Blast_Cell_Jr cell_blast = blast_cell_list.get(j);

                    int player_damage = player_blast.GetHP();
                    int blast_damage = cell_blast.GetHP();

                    boolean player_flag = player_blast.Attacked(blast_damage);
                    boolean cell_flag = cell_blast.Attacked(player_damage);

                    if(player_flag) {
                        eft_list.add(new Effect_Explosion(player_blast.GetX() + 120, player_blast.GetY() + 55));
                        blast_player_list.remove(i);
                    }

                    if(cell_flag) {
                        eft_list.add(new Effect_Explosion(cell_blast.GetX(), cell_blast.GetY()));
                        blast_cell_list.remove(j);
                        score += 150 + addScore/10;
                    }

                    return;
                }
            }
        }

        //플레이어 기공파와 적 기공파(키드부우)가 닿았을 때
        for(int i = blast_player_list.size() - 1; i >= 0; i--) {
            for(int j = blast_kidboo_list.size() - 1; j >= 0; j--) {
                if(CollisionManager.CheckBoxToBox(blast_player_list.get(i).
                        m_BoundBox, blast_kidboo_list.get(j).m_BoundBox)) {

                    Blast_Player player_blast = blast_player_list.get(i);
                    Blast_Ball kid_blast = blast_kidboo_list.get(j);

                    int player_damage = player_blast.GetHP();
                    int blast_damage = kid_blast.GetHP();

                    boolean player_flag = player_blast.Attacked(blast_damage);
                    boolean kid_flag = kid_blast.Attacked(player_damage);

                    if(player_flag) {
                        eft_list.add(new Effect_Explosion(player_blast.GetX() + 120, player_blast.GetY() + 55));
                        blast_player_list.remove(i);
                    }

                    if(kid_flag) {
                        eft_list.add(new Effect_Explosion(kid_blast.GetX(), kid_blast.GetY()));
                        blast_kidboo_list.remove(j);
                        score += 700 + addScore/5;
                    }

                    SoundManager.getInstance().play(1);

                    return;
                }
            }
        }

        //플레이어와 적이 닿았을 때
        for(int i = enemyList.size() - 1; i>= 0; i--) {

            if(CollisionManager.CheckBoxToBox(m_player.m_BoundBox, enemyList.get(i).m_BoundBox)) {
                eft_list.add(new Effect_Explosion(enemyList.get(i).GetX(), enemyList.get(i).GetY()));
                m_player.Attacked(1000);
                enemyList.get(i).Attacked(m_player.GetDamage());
                if(enemyList.get(i).GetHP() <= 0)
                    enemyList.remove(i);

                SoundManager.getInstance().play(1);

            }

        }


        //플레이어가 적의 기공파(기본)에 맞았을 때
        for(int i = blast_enemy_list.size() - 1; i >= 0; i--) {

            if(CollisionManager.CheckBoxToBox(m_player.m_BoundBox, blast_enemy_list.get(i).m_BoundBox)) {

                Blast_Enemy enemy_blast = blast_enemy_list.get(i);

                int blast_damage = enemy_blast.GetHP();

                boolean player_flag = m_player.Attacked(blast_damage);
                boolean enemy_flag = enemy_blast.Attacked(m_player.GetDamage());

                if(player_flag) {
                    eft_list.add(new Effect_Explosion(m_player.GetX() + 20, m_player.GetY()));
                }

                if(enemy_flag) {
                    eft_list.add(new Effect_Explosion(enemy_blast.GetX(), enemy_blast.GetY()));
                    blast_enemy_list.remove(i);
                }

                SoundManager.getInstance().play(1);
            }
        }

        //플레이어가 적의 기공파(베지터)에 맞았을 때
        for(int i = blast_vegita_list.size() - 1; i >= 0; i--) {

            if(CollisionManager.CheckBoxToBox(m_player.m_BoundBox, blast_vegita_list.get(i).m_BoundBox)) {

                Blast_FinalFresh vegita_blast = blast_vegita_list.get(i);

                int blast_damage = vegita_blast.GetHP();

                boolean player_flag = m_player.Attacked(blast_damage);
                boolean enemy_flag = vegita_blast.Attacked(m_player.GetDamage());

                if(player_flag)
                    eft_list.add(new Effect_Explosion(m_player.GetX() + 20, m_player.GetY()));

                if(enemy_flag) {
                    eft_list.add(new Effect_Explosion(vegita_blast.GetX(), vegita_blast.GetY()));
                    blast_vegita_list.remove(i);
                }

                SoundManager.getInstance().play(1);
            }
        }

        //플레이어가 적의 기공파(셀)에 맞았을 때
        for(int i = blast_cell_list.size() - 1; i >= 0; i--) {

            if(CollisionManager.CheckBoxToBox(m_player.m_BoundBox, blast_cell_list.get(i).m_BoundBox)) {

                Blast_Cell_Jr cell_blast = blast_cell_list.get(i);

                int blast_damage = cell_blast.GetHP();

                boolean player_flag = m_player.Attacked(blast_damage);
                boolean cell_flag = cell_blast.Attacked(m_player.GetDamage());

                if(player_flag)
                    eft_list.add(new Effect_Explosion(m_player.GetX(), m_player.GetY()));

                if(cell_flag) {
                    eft_list.add(new Effect_Explosion(cell_blast.GetX(), cell_blast.GetY()));
                    blast_cell_list.remove(i);
                }

                SoundManager.getInstance().play(1);
            }
        }


        //플레이어가 적의 기공파(키드부우)에 맞았을 때
        for(int i = blast_kidboo_list.size() - 1; i >= 0; i--) {

            if(CollisionManager.CheckBoxToBox(m_player.m_BoundBox, blast_kidboo_list.get(i).m_BoundBox)) {

                Blast_Ball kid_blast = blast_kidboo_list.get(i);

                int blast_damage = kid_blast.GetHP();

                boolean player_flag = m_player.Attacked(blast_damage);
                boolean kid_flag = kid_blast.Attacked(m_player.GetDamage());

                if(player_flag)
                    eft_list.add(new Effect_Explosion(m_player.GetX(), m_player.GetY()));

                if(kid_flag) {
                    eft_list.add(new Effect_Explosion(kid_blast.GetX(), kid_blast.GetY()));
                    blast_kidboo_list.remove(i);
                }

                SoundManager.getInstance().play(1);
            }
        }
    }

    @Override
    public boolean onKeyDown(int KeyCode, KeyEvent event) {
        int x = m_player.GetX();
        int y = m_player.GetY();

        if(KeyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            m_player.SetState(Player.BACK_FLY);
            m_player.UpdateState();
            m_player.SetPosition(x - 10, y);
        }

        else if(KeyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            m_player.SetState(Player.FRONT_FLY);
            m_player.UpdateState();
            m_player.SetPosition(x + 10, y);
        }

        else if(KeyCode == KeyEvent.KEYCODE_DPAD_UP) {
            if(m_player.GetState() != Player.WAIT) {
                m_player.SetState(Player.WAIT);
                m_player.UpdateState();

            }
            m_player.SetPosition(x, y - 10);
        }

        else if(KeyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            if(m_player.GetState() != Player.WAIT) {
                m_player.SetState(Player.WAIT);
                m_player.UpdateState();
            }
            m_player.SetPosition(x, y + 10);
        }

        else if(KeyCode == KeyEvent.KEYCODE_SPACE) {
            if(m_player.GetState() != Player.ATTACK) {
                m_player.SetState(Player.ATTACK);
                m_player.UpdateState();
            }
        }

        else {
            if(m_player.GetState() != Player.WAIT) {
                m_player.SetState(Player.WAIT);
                m_player.UpdateState();
            }
        }
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(m_player.GetState() == Player.DEATH) {
            dx = 0;
            dy = 0;
            return false;
        }

        int act = event.getAction();

        switch (act & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_POINTER_DOWN: //두번째 손가락 터치(손가락 2개를 인식하였기 때문에 핀치 줌으로 판별)
            case MotionEvent.ACTION_DOWN:    //첫번째 손가락 터치(드래그 용도)

                dx = 0;
                dy = 0;

                break;

            case MotionEvent.ACTION_POINTER_UP:// 두번째 손가락을 떼었을 경우
            case MotionEvent.ACTION_UP: // 첫번째 손가락을 떼었을 경우

                if(m_player.GetState() == Player.ATTACK || m_player.GetState() == Player.DEATH) {
                    return false;
                }

                if( event.getPointerCount() <= 1) {

                    px = (int) event.getX();
                    py = (int) event.getY();

                    if (((blast_icon.GetX() - 40) <= px && (blast_icon.GetX() + blast_icon.GetWidth() + 40) >= px)
                            && ((blast_icon.GetY() - 40) <= py && (blast_icon.GetY() + blast_icon.GetHeight() + 40) >= py)) {

                        if (m_player.GetState() != Player.ATTACK && m_player.GetKi() >= 25) {
                            isLack = false;
                            m_player.SetState(Player.ATTACK);
                            m_player.UpdateState();
                        } else if (m_player.GetKi() < 25) isLack = true;

                    } else {

                        //상태초기화
                        m_player.SetState(Player.WAIT);
                        m_player.UpdateState();
                    }

                    m_player.stopMove();

                    dx = 0;
                    dy = 0;

                    m_player.SetPosition(m_player.GetX(), m_player.GetY());

                    d_pad_center.SetPosition(245, 750);

                } else if(event.getPointerCount() > 1) {

                    System.out.println("Multi touch");

                    px = (int) event.getX(0);
                    py = (int) event.getY(0);

                    px2 = (int) event.getX(1);
                    py2 = (int) event.getY(1);

                    if (((blast_icon.GetX() - 40) <= px2 && (blast_icon.GetX() + blast_icon.GetWidth() + 40) >= px2)
                            && ((blast_icon.GetY() - 40) <= py2 && (blast_icon.GetY() + blast_icon.GetHeight() + 40) >= py2)) {

                        if (m_player.GetState() != Player.ATTACK && m_player.GetKi() >= 25) {
                            isLack = false;
                            m_player.SetState(Player.ATTACK);
                            m_player.UpdateState();
                        } else if (m_player.GetKi() < 25) isLack = true;

                    }

                    if (((blast_icon.GetX() - 40) <= px && (blast_icon.GetX() + blast_icon.GetWidth() + 40) >= px)
                            && ((blast_icon.GetY() - 40) <= py && (blast_icon.GetY() + blast_icon.GetHeight() + 40) >= py)) {

                        if (m_player.GetState() != Player.ATTACK && m_player.GetKi() >= 25) {
                            isLack = false;
                            m_player.SetState(Player.ATTACK);
                            m_player.UpdateState();
                        } else if (m_player.GetKi() < 25) isLack = true;

                    }

                    m_player.stopMove();

                    dx = 0;
                    dy = 0;

                    m_player.SetPosition(m_player.GetX(), m_player.GetY());

                    d_pad_center.SetPosition(245, 750);
                }

                break;

            case MotionEvent.ACTION_MOVE:

                if(m_player.GetState() == Player.ATTACK || m_player.GetState() == Player.DEATH) {
                    return false;
                }

                if(event.getPointerCount() <= 1) {

                    px = (int) event.getX();
                    py = (int) event.getY();

                    if (((blast_icon.GetX() - 40) <= px && (blast_icon.GetX() + blast_icon.GetWidth() + 40) >= px)
                            && ((blast_icon.GetY() - 40) <= py && (blast_icon.GetY() + blast_icon.GetHeight() + 40) >= py)) {

                    } else if ((event.getX() >= 0 && event.getX() <= (200 + d_pad_circle.GetWidth()))
                            && (event.getY() >= 500 && event.getY() <= (700 + d_pad_circle.GetHeight()))) {

                        int pad_x = d_pad_circle.GetX() + 144;
                        int pad_y = d_pad_circle.GetY() + 144;
                        int center_x = d_pad_center.GetX();
                        int center_y = d_pad_center.GetY();

                        atan = Math.atan2(center_y - pad_y, center_x - pad_x);
                        r = Math.sqrt(Math.pow(pad_x - center_x, 2) + Math.pow(pad_y - center_y, 2));

                        dx = (int) (Math.cos(atan) * r * 0.15);
                        dy = (int) (Math.sin(atan) * r * 0.15);

                        d_pad_center.SetPosition((int) event.getX(), (int) event.getY());

                        m_player.startMove(dx, dy);

                    } else if (event.getX() >= 400) {

                        if (m_player.GetKi() >= 25) isLack = false;
                        m_player.stopMove();

                        if (m_player.GetState() != Player.KI) {
                            m_player.SetState(Player.KI);
                            m_player.UpdateState();
                        }
                    }
                } else {

                    px = (int)event.getX(0);
                    py = (int)event.getY(0);
                    px2 = (int)event.getX(1);
                    py2 = (int)event.getY(1);

                    if (((blast_icon.GetX() - 40) <= px && (blast_icon.GetX() + blast_icon.GetWidth() + 40) >= px)
                            && ((blast_icon.GetY() - 40) <= py && (blast_icon.GetY() + blast_icon.GetHeight() + 40) >= py)) {

                    } else if ((px >= 0 && px <= (200 + d_pad_circle.GetWidth()))
                            && (py >= 500 && py <= (700 + d_pad_circle.GetHeight()))) {

                        int pad_x = d_pad_circle.GetX() + 144;
                        int pad_y = d_pad_circle.GetY() + 144;
                        int center_x = d_pad_center.GetX();
                        int center_y = d_pad_center.GetY();

                        atan = Math.atan2(center_y - pad_y, center_x - pad_x);
                        r = Math.sqrt(Math.pow(pad_x - center_x, 2) + Math.pow(pad_y - center_y, 2));

                        dx = (int) (Math.cos(atan) * r * 0.15);
                        dy = (int) (Math.sin(atan) * r * 0.15);

                        d_pad_center.SetPosition((int) event.getX(), (int) event.getY());

                        m_player.startMove(dx, dy);

                    }
                }

                break;

            case MotionEvent.ACTION_CANCEL:

                m_player.SetState(Player.WAIT);
                m_player.UpdateState();

                break;

            default:

                break;

        }
        return true;
    }

}
