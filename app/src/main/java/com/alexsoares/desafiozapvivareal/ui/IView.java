package com.alexsoares.desafiozapvivareal.ui;

import android.content.Context;

public interface IView {
    Context getContext();
    void finish();
    void showLoading();
    void hideLoading();
    void showMessageError(String message);
}
