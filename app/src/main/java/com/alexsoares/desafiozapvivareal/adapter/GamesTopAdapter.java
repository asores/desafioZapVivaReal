package com.alexsoares.desafiozapvivareal.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexsoares.desafiozapvivareal.R;
import com.alexsoares.desafiozapvivareal.api.ApiService;
import com.alexsoares.desafiozapvivareal.database.dao.EntityFavoritesDAO;
import com.alexsoares.desafiozapvivareal.database.domain.EntityFavorites;
import com.alexsoares.desafiozapvivareal.model.GamesTopDTO;
import com.alexsoares.desafiozapvivareal.model.Top;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by alex.soares on 17/04/2018.
 */

public class GamesTopAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {
    private GamesTopDTO mGameTop;
    private Activity mActivity;
    private GamesTopAdapter.ClickGameListener clickGameListener;
    private Picasso picasso;
    private List<Top> gameList;
    private List<Top> contactListFiltered;
    private EntityFavoritesDAO dao;

    public GamesTopAdapter(GamesTopDTO gamesTop, Activity activity) {
        this.mGameTop = gamesTop;
        this.mActivity = activity;
        picasso = new Picasso.Builder(mActivity).downloader(ApiService.getClientPicasso(mActivity)).build();
        gameList = new ArrayList<>();
    }


    public class ItemViewHolder extends RecyclerView.ViewHolder {
        CardView cardViewItem;
        ImageView imvGame;
        SwitchCompat swcFavorites;
        TextView txtInfoTitle;

        public ItemViewHolder(View view) {
            super(view);
            cardViewItem = (CardView) view.findViewById(R.id.card_view_item);
            imvGame = (ImageView) view.findViewById(R.id.imvGame);
            swcFavorites = (SwitchCompat) view.findViewById(R.id.swcFavorite);
            txtInfoTitle = (TextView) view.findViewById(R.id.info_title);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View headerView = LayoutInflater.from(parent.getContext()).inflate(R.layout.game_top_adapter, parent, false);
        return new GamesTopAdapter.ItemViewHolder(headerView);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        GamesTopAdapter.ItemViewHolder itemViewHolder = (GamesTopAdapter.ItemViewHolder) holder;
        final Top gameItem = mGameTop.getTop().get(position);
        gameList.add(gameItem);

        itemViewHolder.txtInfoTitle.setText(gameItem.getGame().getName());
//        itemViewHolder.txtInfoSubTitlePopularity.setText(mActivity.getString(R.string.info_subtitle_popularity, String.valueOf(gameItem.getGame().getPopularity())));
//        itemViewHolder.txtInfoSubTitleViewers.setText(mActivity.getString(R.string.info_subtitle_viewers, String.valueOf(gameItem.getViewers())));
        itemViewHolder.swcFavorites.setChecked(isItemFavorite(gameItem.getGame().getId()));
        picasso.load(gameItem.getGame().getBox().getLarge())
                .placeholder(R.mipmap.ic_launcher_zap)
                .into(itemViewHolder.imvGame);

        if (clickGameListener != null) {
            ((ItemViewHolder) holder).cardViewItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickGameListener.onClick(gameItem);
                }
            });

            ((ItemViewHolder) holder).swcFavorites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (((ItemViewHolder) holder).swcFavorites.isChecked()) {
                            saveFavoriteBD(gameItem);
                        } else {
                            deleteFavoriteBD(gameItem);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                List<Top> filteredList = new ArrayList<>();
                for (Top row : gameList) {
                    if (row.getGame().getName().toLowerCase().contains(charString.toLowerCase())) {
                        if (!filteredList.contains(row)) {
                            filteredList.add(row);
                        }
                    }
                }

                contactListFiltered = filteredList;
                FilterResults filterResults = new FilterResults();
                filterResults.values = contactListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                contactListFiltered = (ArrayList<Top>) filterResults.values;
                mGameTop.setTop(contactListFiltered);
                fillItems();
            }
        };
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        if (mGameTop != null) {
            return mGameTop.getTop().size();
        } else {
            return 0;
        }
    }

    public GamesTopDTO getItems() {
        return mGameTop;
    }

    public interface ClickGameListener {
        void onClick(Top topGame);
    }

    public void setmClickGameListener(final GamesTopAdapter.ClickGameListener mClickGameListener) {
        this.clickGameListener = mClickGameListener;
    }


    public void setGamesTopDTO(final GamesTopDTO gamesTop) {
        this.mGameTop.getTop().addAll(gamesTop.getTop());
        fillItems();
    }

    public void setGamesTopNoFilter(final GamesTopDTO gamesTop) {
        this.mGameTop.setTop(gamesTop.getTop());
        fillItems();
    }

    private void fillItems() {
        notifyDataSetChanged();
    }

    private void saveFavoriteBD(Top game) throws Exception {
        dao = new EntityFavoritesDAO(mActivity);
        EntityFavorites entityFavorites = new EntityFavorites();
        entityFavorites.setGameID(game.getGame().getId());
        entityFavorites.setGameTop(new Gson().toJson(game));
        dao.insert(entityFavorites);
        dao.close();
    }

    private void deleteFavoriteBD(Top game) throws SQLException {
        dao = new EntityFavoritesDAO(mActivity);
        EntityFavorites entityFavorites = new EntityFavorites();
        entityFavorites.setGameID(game.getGame().getId());
        dao.delete(entityFavorites);
        dao.close();
    }

    private boolean isItemFavorite(int idGame) {
        dao = new EntityFavoritesDAO(mActivity);
        EntityFavorites favorite = dao.getById(idGame);
        dao.close();

        return favorite != null;
    }
}
