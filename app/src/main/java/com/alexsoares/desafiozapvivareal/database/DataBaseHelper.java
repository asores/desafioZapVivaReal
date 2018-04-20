package com.alexsoares.desafiozapvivareal.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.alexsoares.desafiozapvivareal.database.domain.EntityFavorites;
import com.alexsoares.desafiozapvivareal.util.Constants;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
/**
 * Created by alex.soares on 19/04/2018.
 */
public class DataBaseHelper<E> extends OrmLiteSqliteOpenHelper {

    //Construtor
    public DataBaseHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, EntityFavorites.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            for (int i = oldVersion + 1; i <= newVersion; i++) {
                switch (i) {
                    case 2:
                        TableUtils.dropTable(connectionSource, EntityFavorites.class, true);
                        TableUtils.createTable(connectionSource, EntityFavorites.class);
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        super.close();
    }
}
