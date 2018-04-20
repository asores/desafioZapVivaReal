package com.alexsoares.desafiozapvivareal.ui.favorites;

import com.alexsoares.desafiozapvivareal.database.dao.EntityFavoritesDAO;
import com.alexsoares.desafiozapvivareal.model.GamesTopDTO;
import com.alexsoares.desafiozapvivareal.model.Top;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class FavoritesPresenterImpl implements IFavoritesPresenter {
    private IFavoritesView mView;

    @Override
    public void setView(IFavoritesView view) {
        this.mView = view;
    }

    @Override
    public void getGamesFavoteritesDB() {
        EntityFavoritesDAO dao = new EntityFavoritesDAO(mView.getContext());
        List<Top> listGamesDB = new ArrayList<>();
        for (int x=0; x < dao.getAll().size(); x++){
            listGamesDB.add(new Gson().fromJson(dao.getAll().get(x).getGameTop(), Top.class));
        }
        GamesTopDTO gamesTopFavorites = new GamesTopDTO();
        gamesTopFavorites.setTop(listGamesDB);
        mView.setListGamesFavorites(gamesTopFavorites);
    }
}
