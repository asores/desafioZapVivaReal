
package com.alexsoares.desafiozapvivareal.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Top implements Serializable {

    @SerializedName("game")
    @Expose
    private Game game;
    @SerializedName("viewers")
    @Expose
    private Integer viewers;
    @SerializedName("channels")
    @Expose
    private Integer channels;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Integer getViewers() {
        return viewers;
    }

    public void setViewers(Integer viewers) {
        this.viewers = viewers;
    }

    public Integer getChannels() {
        return channels;
    }

    public void setChannels(Integer channels) {
        this.channels = channels;
    }

}
