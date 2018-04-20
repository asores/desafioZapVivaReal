package com.alexsoares.desafiozapvivareal.api;

import android.content.Context;

import com.alexsoares.desafiozapvivareal.model.GamesTopDTO;
import com.alexsoares.desafiozapvivareal.util.Utils;
import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.OkHttpDownloader;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import retrofit.Call;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by alex.soares on 17/04/2018.
 */

public class ApiService {
    private static IDesafioGamesTop iDesafioGamesTop;
    private static String URL_BASE = "https://api.twitch.tv";

    public static IDesafioGamesTop getApi(final Context context) {
        int cacheSize = 10 * 1024 * 1024; // 10 MB
        File httpCacheDirectory = new File(context.getCacheDir(), "responses");
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        OkHttpClient okClient = new OkHttpClient();
        okClient.setWriteTimeout(120, TimeUnit.SECONDS);
        okClient.setConnectTimeout(120, TimeUnit.SECONDS);
        okClient.setReadTimeout(120, TimeUnit.SECONDS);
        okClient.setCache(cache);
        okClient.interceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                String cacheHeaderValue = Utils.checkInternetConnection(context)
                        ? "public, max-age=2419200"
                        : "public, only-if-cached, max-stale=2419200" ;
                Request original = chain.request();
                Request.Builder requestBuilder = chain.request().newBuilder();
                requestBuilder.header("Client-ID", "63slyzm0k4dzo6dq53owt15ea1pylx");
                requestBuilder.header("Accept", "application/vnd.twitchtv.v5+json");
                requestBuilder.method(original.method(), original.body());

                Response response = chain.proceed(requestBuilder.build());
                return response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", cacheHeaderValue)
                        .build();
            }
        });

        okClient.networkInterceptors().add(
                new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        String cacheHeaderValue = Utils.checkInternetConnection(context)
                                ? "public, max-age=2419200"
                                : "public, only-if-cached, max-stale=2419200" ;
                        Request request = originalRequest.newBuilder().build();
                        Response response = chain.proceed(request);
                        return response.newBuilder()
                                .removeHeader("Pragma")
                                .removeHeader("Cache-Control")
                                .header("Cache-Control", cacheHeaderValue)
                                .build();
                    }
                }
        );

        Retrofit client = new Retrofit.Builder()
                .baseUrl(URL_BASE)
                .client(okClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        iDesafioGamesTop = client.create(IDesafioGamesTop.class);
        return iDesafioGamesTop;
    }


    public static OkHttpDownloader getClientPicasso(Context context){
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder().header("Cache-Control", "max-age=" + (60 * 60 * 24 * 365)).build();
            }
        });

        okHttpClient.setCache(new Cache(context.getCacheDir(), Integer.MAX_VALUE));
        OkHttpDownloader okHttpDownloader = new OkHttpDownloader(okHttpClient);

        return okHttpDownloader;
    }

    public interface IDesafioGamesTop {
        @GET("/kraken/games/top")
        Call<GamesTopDTO> getGameTop(
                @Query("limit") int limite,
                @Query("offset") int pagina
        );

    }

}
