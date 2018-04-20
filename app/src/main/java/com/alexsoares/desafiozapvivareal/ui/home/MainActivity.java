package com.alexsoares.desafiozapvivareal.ui.home;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.support.v7.widget.SearchView;

import com.alexsoares.desafiozapvivareal.BaseActivity;
import com.alexsoares.desafiozapvivareal.R;
import com.alexsoares.desafiozapvivareal.adapter.GamesTopAdapter;
import com.alexsoares.desafiozapvivareal.model.GamesTopDTO;
import com.alexsoares.desafiozapvivareal.model.Top;
import com.alexsoares.desafiozapvivareal.ui.details.DetailsGameActivity;
import com.alexsoares.desafiozapvivareal.ui.favorites.FavoritesActivity;
import com.alexsoares.desafiozapvivareal.util.Utils;
import com.github.clans.fab.FloatingActionButton;
import com.github.pwittchen.infinitescroll.library.InfiniteScrollListener;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;

import java.io.Serializable;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements IMainView {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_band_off)
    LinearLayout llBandOff;
    @BindView(R.id.rv_games)
    RecyclerView rvGames;
    @BindView(R.id.main_activity_swipe_to_refresh)
    SwipeRefreshLayout swipeLayout;
    @BindView(R.id.fab_list_favorites)
    FloatingActionButton fabListFavorites;


    private IMainPresenter presenter;
    private GamesTopAdapter adapterGameTop;
    private LinearLayoutManager mLayoutManager;
    private static int POINTER_START_SCROLLVIEW = 20;
    private static int VALUE_ADDES = 20;
    private int COUNT_PAGE = 20;
    private SearchView searchView;
    private boolean isUpdateScrollInfinite = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar(this);
        initViews();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        adapterGameTop.notifyDataSetChanged();
    }

    private void initViews() {
        onCreateOptionsMenu(toolbar.getMenu());
        checkConnection();
        initSwipeRefresh();
        presenter = new MainPresenterImpl();
        presenter.setView(this);
        presenter.setPage(COUNT_PAGE);
        presenter.getGamesTop(false);

        fabListFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FavoritesActivity.class));
                overridePendingTransition(R.anim.slide_in_rigth_to_left, R.anim.slide_out_rigth_to_left);
            }
        });
    }

    private void checkConnection() {
        if (Utils.checkInternetConnection(this)) {
            llBandOff.setVisibility(View.GONE);
        } else {
            llBandOff.setVisibility(View.VISIBLE);
        }
    }

    private void initSwipeRefresh() {
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing(true);
                presenter.setPage(VALUE_ADDES);
                presenter.getGamesTop(true);
            }
        });
        swipeLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimaryDark));
    }

    @Override
    public void setListGamesTop(GamesTopDTO gamesTop) {
        if (COUNT_PAGE == 20) {
            mLayoutManager = new LinearLayoutManager(this);
            rvGames.setHasFixedSize(true);
            rvGames.setLayoutManager(mLayoutManager);
            rvGames.setItemAnimator(new DefaultItemAnimator());
            adapterGameTop = new GamesTopAdapter(gamesTop, this);
            rvGames.setAdapter(adapterGameTop);
            adapterGameTop.notifyDataSetChanged();
            rvGames.addOnScrollListener(createInfiniteScrollListener());
            getClickGame();
        } else {
            adapterGameTop.setGamesTopDTO(gamesTop);
        }

        swipeLayout.setRefreshing(false);
        isUpdateScrollInfinite = true;
    }

    private void getClickGame(){
        adapterGameTop.setmClickGameListener(new GamesTopAdapter.ClickGameListener() {
            @Override
            public void onClick(Top topGame) {
                Intent mIntent = new Intent(MainActivity.this, DetailsGameActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("dataGame", (Serializable) topGame);
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
                overridePendingTransition(R.anim.slide_in_rigth_to_left, R.anim.slide_out_rigth_to_left);
            }
        });
    }

    private InfiniteScrollListener createInfiniteScrollListener() {
        return new InfiniteScrollListener(POINTER_START_SCROLLVIEW, mLayoutManager) {
            @Override
            public void onScrolledToEnd(final int firstVisibleItemPosition) {
                if (isUpdateScrollInfinite) {
                    COUNT_PAGE = COUNT_PAGE + VALUE_ADDES;
                    presenter.setPage(COUNT_PAGE);
                    presenter.getGamesTop(false);
                    checkConnection();
                    isUpdateScrollInfinite = false;
                }
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.isEmpty()) {
                    adapterGameTop.setGamesTopNoFilter(presenter.getGamesTopDTO());
                } else {
                    adapterGameTop.getFilter().filter(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (query.isEmpty()) {
                    adapterGameTop.setGamesTopNoFilter(presenter.getGamesTopDTO());
                } else {
                    adapterGameTop.getFilter().filter(query);
                }
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_search) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (searchView != null)
            if (!searchView.isIconified()) {
                toolbar.collapseActionView();
                return;
            }
        super.onBackPressed();
    }
}
