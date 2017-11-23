package com.example.mapl0.db_game_project.db.Game;

/**
 * Created by mapl0 on 2016-12-01.
 */

public class Item {

    public String Picture;
    public int Rank;
    public String Name;
    public int Score;

    public Item(String p, int r, String t, int c){
        Picture = p;
        Rank = r;
        Name = t;
        Score = c;
    }

}
