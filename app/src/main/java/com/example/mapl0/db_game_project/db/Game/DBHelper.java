package com.example.mapl0.db_game_project.db.Game;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.mapl0.db_game_project.Framework.AppManager;

/**
 * Created by mapl0 on 2016-11-30.
 */

public class DBHelper extends SQLiteOpenHelper {


    public DBHelper() {
        super(AppManager.getInstance().getGameview().getContext(), "rank.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table if not exists rank_table (" +
                "NAME VARCHAR(2) NOT NULL," +
                "SCORE INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
