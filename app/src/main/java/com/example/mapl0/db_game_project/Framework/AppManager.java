package com.example.mapl0.db_game_project.Framework;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;

import com.example.mapl0.db_game_project.db.Game.GameState;

/**
 * Created by mapl0 on 2016-11-05.
 */

public class AppManager {

    private GameView m_gameview;
    public GameActivity m_game_activity;
    private Resources m_resources;
    public GameState m_gamestate;

    public void setGameView(GameView _gameview) {
        m_gameview = _gameview;
    }

    public void setResources(Resources _resources) {
        m_resources = _resources;
    }

    public GameView getGameview() {
        return m_gameview;
    }

    public GameActivity getGameActivity() {return m_game_activity;}

    public Resources getResources() {
        return m_resources;
    }

    private static AppManager s_instance;

    public static AppManager getInstance() {

        if(s_instance == null) {
            s_instance = new AppManager();
        }
        return s_instance;
    }

    public Bitmap getBitmap(int r) {
        return BitmapFactory.decodeResource(m_resources, r);
    }
}
