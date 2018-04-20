package com.alexsoares.desafiozapvivareal.database.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
/**
 * Created by alex.soares on 19/04/2018.
 */
@Entity(name = "favorites")
public class EntityFavorites {
    @Id
    private int gameID;

    @Column(name = "gameTop", nullable = true, length = 5000)
    private String gameTop;

    public int getGameID() {
        return gameID;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public String getGameTop() {
        return gameTop;
    }

    public void setGameTop(String gameTop) {
        this.gameTop = gameTop;
    }
}
