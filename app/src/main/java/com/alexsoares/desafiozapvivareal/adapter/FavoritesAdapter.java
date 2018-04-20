package com.alexsoares.desafiozapvivareal.adapter;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexsoares.desafiozapvivareal.R;
import com.alexsoares.desafiozapvivareal.api.ApiService;
import com.alexsoares.desafiozapvivareal.model.GamesTopDTO;
import com.alexsoares.desafiozapvivareal.model.Top;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by alex.soares on 17/04/2018.
 */
public class FavoritesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private GamesTopDTO mGameTop;
    private Activity mActivity;
    private FavoritesAdapter.ClickGameListener clickGameListener;
    private Picasso picasso;
    private List<Top> gameList;

    public FavoritesAdapter(GamesTopDTO gamesTop, Activity activity) {
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
        return new FavoritesAdapter.ItemViewHolder(headerView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FavoritesAdapter.ItemViewHolder itemViewHolder = (FavoritesAdapter.ItemViewHolder) holder;
        final Top gameItem = mGameTop.getTop().get(position);
        gameList.add(gameItem);

        itemViewHolder.swcFavorites.setVisibility(View.INVISIBLE);
        itemViewHolder.txtInfoTitle.setText(gameItem.getGame().getName());
        picasso.load(gameItem.getGame().getBox().getLarge())
                .placeholder(R.mipmap.ic_launcher_zap)
                .into(itemViewHolder.imvGame);

        if (clickGameListener != null) {
            ((FavoritesAdapter.ItemViewHolder) holder).cardViewItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickGameListener.onClick(gameItem);
                }
            });
        }
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

    public void setmClickGameListener(final FavoritesAdapter.ClickGameListener mClickGameListener) {
        this.clickGameListener = mClickGameListener;
    }
}
