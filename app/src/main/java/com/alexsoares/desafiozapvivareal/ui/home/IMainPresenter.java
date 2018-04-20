package com.alexsoares.desafiozapvivareal.ui.home;

import com.alexsoares.desafiozapvivareal.model.GamesTopDTO;

public interface IMainPresenter {
    void setView(IMainView view);
    void getGamesTop(boolean isPushUpdate);
    void setPage(int page);
    GamesTopDTO getGamesTopDTO();
}
