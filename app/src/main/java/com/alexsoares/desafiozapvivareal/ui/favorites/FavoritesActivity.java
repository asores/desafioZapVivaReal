package com.alexsoares.desafiozapvivareal.ui.favorites;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.alexsoares.desafiozapvivareal.BaseActivity;
import com.alexsoares.desafiozapvivareal.R;
import com.alexsoares.desafiozapvivareal.adapter.FavoritesAdapter;
import com.alexsoares.desafiozapvivareal.model.GamesTopDTO;
import com.alexsoares.desafiozapvivareal.model.Top;
import com.alexsoares.desafiozapvivareal.ui.details.DetailsGameActivity;

import java.io.Serializable;

import butterknife.BindView;

public class FavoritesActivity extends BaseActivity implements IFavoritesView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_games_favorites)
    RecyclerView rvGames;

    private IFavoritesPresenter presenter;
    private FavoritesAdapter favoritesAdapter;
    private LinearLayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        initToolbar(this);
        initViews();
    }

    private void initViews(){
        presenter = new FavoritesPresenterImpl();
        presenter.setView(this);
        presenter.getGamesFavoteritesDB();
    }

    @Override
    public void setListGamesFavorites(GamesTopDTO listGamesTop) {
        mLayoutManager = new LinearLayoutManager(this);
        rvGames.setHasFixedSize(true);
        rvGames.setLayoutManager(mLayoutManager);
        rvGames.setItemAnimator(new DefaultItemAnimator());
        favoritesAdapter = new FavoritesAdapter(listGamesTop, this);
        rvGames.setAdapter(favoritesAdapter);
        favoritesAdapter.notifyDataSetChanged();
        getClickGame();
    }

    private void getClickGame(){
        favoritesAdapter.setmClickGameListener(new FavoritesAdapter.ClickGameListener() {
            @Override
            public void onClick(Top topGame) {
                Intent mIntent = new Intent(FavoritesActivity.this, DetailsGameActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("dataGame", (Serializable) topGame);
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
                overridePendingTransition(R.anim.slide_in_rigth_to_left, R.anim.slide_out_rigth_to_left);
            }
        });
    }
}
