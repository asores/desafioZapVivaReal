package com.alexsoares.desafiozapvivareal.database.dao;

import android.content.Context;
import com.alexsoares.desafiozapvivareal.database.domain.EntityFavorites;

/**
 * Created by alex.soares on 19/04/2018.
 */
public class EntityFavoritesDAO extends GenericDAO<EntityFavorites> {
    //Contrutor
    public EntityFavoritesDAO(Context context) {
        super(context, EntityFavorites.class);
    }
}
