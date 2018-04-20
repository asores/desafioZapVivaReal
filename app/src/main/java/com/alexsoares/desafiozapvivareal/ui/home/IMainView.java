package com.alexsoares.desafiozapvivareal.ui.home;

import com.alexsoares.desafiozapvivareal.model.GamesTopDTO;
import com.alexsoares.desafiozapvivareal.ui.IView;


public interface IMainView extends IView {
    void setListGamesTop(GamesTopDTO listGamesTop);
}
