package com.alexsoares.desafiozapvivareal.ui.details;

import android.content.Intent;

import java.sql.SQLException;

public interface IDetailsPresenter {
    void setView(IDetailsView view);
    void getGameExtra(Intent intent);
    void saveFavoriteBD() throws Exception;
    void deleteFavoriteBD() throws SQLException;
    boolean isItemFavorite();
}
