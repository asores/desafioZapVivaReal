package com.alexsoares.desafiozapvivareal.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by alexsoares on 17/04/2018.
 */

public class Utils {

    //Método responsável por verificar se esta habilitado os serviços Wifi ou Dados Movél
    public static Boolean checkInternetConnection(Context context) {
        Boolean isConnected = false;

        try {
            //Inicializa verificação da conectividade
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            //Verifica se esta conectado
            if (networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable()) {
                isConnected = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return isConnected;
    }
}
