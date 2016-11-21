package com.example.nicolas.shootemup;

/**
 * Created by Nicolas on 12/11/2016.
 */

public class ScoreBoard {

    private long id;
    private String name;
    private long score;

    public ScoreBoard() {
        super();
    }

    public ScoreBoard(long id, String name, long score) {
        super();
        this.id = id;
        this.name = name;
        this.score = score;
    }


    public long getId() {
        return id;
    }


    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public long getScore() {
        return score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(long score) {
        this.score = score;
    }
}
