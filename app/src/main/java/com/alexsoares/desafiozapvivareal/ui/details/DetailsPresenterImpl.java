package com.alexsoares.desafiozapvivareal.ui.details;

import android.content.Intent;

import com.alexsoares.desafiozapvivareal.database.dao.EntityFavoritesDAO;
import com.alexsoares.desafiozapvivareal.database.domain.EntityFavorites;
import com.alexsoares.desafiozapvivareal.model.Top;
import com.google.gson.Gson;

import java.sql.SQLException;

public class DetailsPresenterImpl implements IDetailsPresenter {
    private Top gameDetails;
    private IDetailsView mView;
    private EntityFavoritesDAO dao;

    @Override
    public void setView(IDetailsView view) {
        this.mView = view;
    }

    @Override
    public void getGameExtra(Intent intent) {
        gameDetails = (Top) intent.getExtras().getSerializable("dataGame");
        mView.setGameDetails(gameDetails);
    }

    @Override
    public void saveFavoriteBD() throws Exception {
        dao = new EntityFavoritesDAO(mView.getContext());
        EntityFavorites entityFavorites = new EntityFavorites();
        entityFavorites.setGameID(gameDetails.getGame().getId());
        entityFavorites.setGameTop(new Gson().toJson(gameDetails));
        dao.insert(entityFavorites);
        dao.close();
    }

    @Override
    public void deleteFavoriteBD() throws SQLException {
        dao = new EntityFavoritesDAO(mView.getContext());
        EntityFavorites entityFavorites = new EntityFavorites();
        entityFavorites.setGameID(gameDetails.getGame().getId());
        dao.delete(entityFavorites);
        dao.close();
    }

    @Override
    public boolean isItemFavorite() {
        dao = new EntityFavoritesDAO(mView.getContext());
        EntityFavorites favorite = dao.getById(gameDetails.getGame().getId());
        dao.close();
        return favorite != null;
    }
}
