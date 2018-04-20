package com.alexsoares.desafiozapvivareal.ui.home;

import com.alexsoares.desafiozapvivareal.R;
import com.alexsoares.desafiozapvivareal.api.ApiService;
import com.alexsoares.desafiozapvivareal.model.GamesTopDTO;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class MainPresenterImpl implements IMainPresenter {
    private IMainView mView;
    private int limitData = 20;
    private int page;
    private GamesTopDTO gamesTopDTO;

    @Override
    public void setView(IMainView view) {
        this.mView = view;

    }

    @Override
    public void getGamesTop(boolean isPushUpdate) {
        mView.showLoading();
        if (isPushUpdate)
            gamesTopDTO = null;

        ApiService.IDesafioGamesTop service = ApiService.getApi(mView.getContext());
        Call<GamesTopDTO> call = service.getGameTop(limitData, page);
        System.out.println("PAGE ALE => "+page);
        call.enqueue(new Callback<GamesTopDTO>() {
            @Override
            public void onResponse(Response<GamesTopDTO> response, Retrofit retrofit) {
                if (response.code() >= 200 && response.code() < 300) {
                    mView.setListGamesTop(response.body());
                    mView.hideLoading();
                    setGamesTopDTO(response.body());
                } else if (response.code() == 401) {
                    mView.hideLoading();
                    mView.showMessageError(mView.getContext().getString(R.string.message_erro));
                } else {
                    mView.hideLoading();
                    mView.showMessageError(mView.getContext().getString(R.string.message_erro));
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mView.hideLoading();
                mView.showMessageError(mView.getContext().getString(R.string.message_erro));
            }
        });

    }

    @Override
    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public GamesTopDTO getGamesTopDTO() {
        return gamesTopDTO;
    }

    public void setGamesTopDTO(GamesTopDTO gamesTopDTO) {
        if(this.gamesTopDTO == null){
            this.gamesTopDTO = new GamesTopDTO();
            this.gamesTopDTO.setTop(gamesTopDTO.getTop());
        }else{
            this.gamesTopDTO.getTop().addAll(gamesTopDTO.getTop());
        }

    }
}
