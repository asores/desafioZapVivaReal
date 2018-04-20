package com.alexsoares.desafiozapvivareal.ui.details;

import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexsoares.desafiozapvivareal.BaseActivity;
import com.alexsoares.desafiozapvivareal.R;
import com.alexsoares.desafiozapvivareal.api.ApiService;
import com.alexsoares.desafiozapvivareal.model.Top;
import com.squareup.picasso.Picasso;

import butterknife.BindView;

public class DetailsGameActivity extends BaseActivity implements IDetailsView, View.OnClickListener {
    @BindView(R.id.imv_Game)
    ImageView imvGame;
    @BindView(R.id.info_title)
    TextView txtInfoTilte;
    @BindView(R.id.info_subtitle_popularity)
    TextView txtInfoSubtitlePopularity;
    @BindView(R.id.info_subtitle_viewers)
    TextView txtInfoSubtitleViewers;
    @BindView(R.id.swc_favorite_details)
    SwitchCompat swcFavoriteDetails;

    private IDetailsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_game);
        initToolbar(this);
        initViews();
    }

    private void initViews() {
        presenter = new DetailsPresenterImpl();
        presenter.setView(this);
        presenter.getGameExtra(getIntent());
    }



    @Override
    public void setGameDetails(Top gameDetails) {
        new Picasso.Builder(this).downloader(ApiService.getClientPicasso(this)).build()
                .load(gameDetails.getGame().getLogo().getLarge())
                .placeholder(R.mipmap.ic_launcher_zap)
                .into(imvGame);

        txtInfoTilte.setText(gameDetails.getGame().getName());
        txtInfoSubtitlePopularity.setText(getString(R.string.info_subtitle_popularity, String.valueOf(gameDetails.getGame().getPopularity())));
        txtInfoSubtitleViewers.setText(getString(R.string.info_subtitle_viewers, String.valueOf(gameDetails.getViewers())));
        swcFavoriteDetails.setOnClickListener(this);
        if(presenter.isItemFavorite())
            swcFavoriteDetails.setChecked(true);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.swc_favorite_details:
                try {
                    if (swcFavoriteDetails.isChecked()) {
                        presenter.saveFavoriteBD();
                    } else {
                        presenter.deleteFavoriteBD();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }
}
