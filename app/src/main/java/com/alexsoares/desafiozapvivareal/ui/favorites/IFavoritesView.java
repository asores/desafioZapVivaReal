package com.alexsoares.desafiozapvivareal.ui.favorites;

import com.alexsoares.desafiozapvivareal.model.GamesTopDTO;
import com.alexsoares.desafiozapvivareal.ui.IView;

public interface IFavoritesView extends IView {
    void setListGamesFavorites(GamesTopDTO listGamesTop);
}
