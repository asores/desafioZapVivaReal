package com.alexsoares.desafiozapvivareal;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexsoares.desafiozapvivareal.ui.IView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity implements IView, View.OnClickListener {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.imv_icon_toolbar)
    ImageView imvIconToolbar;
    @BindView(R.id.toolbar_title)
    TextView tvToolbarTitle;

    private ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void initToolbar(Activity activity) {
        ButterKnife.bind(activity);

        //setSupportActionBar(toolbar);
        tvToolbarTitle.setText(getCurrentActivityName());

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setHomeButtonEnabled(true);
        }
    }

    private String getCurrentActivityName() {
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        if (am.getRunningTasks(1).size() > 0) {
            ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
            switch (cn.getClassName()) {
                case "com.alexsoares.desafiozapvivareal.ui.home.MainActivity":
                    return getString(R.string.app_name_title);
                case "com.alexsoares.desafiozapvivareal.ui.favorites.FavoritesActivity":
                    imvIconToolbar.setImageDrawable(getResources().getDrawable(R.drawable.ic_back_app));
                    imvIconToolbar.setOnClickListener(this);
                    return getString(R.string.activity_name_title);

                case "com.alexsoares.desafiozapvivareal.ui.details.DetailsGameActivity":
                    imvIconToolbar.setImageDrawable(getResources().getDrawable(R.drawable.ic_back_app));
                    imvIconToolbar.setOnClickListener(this);
                    return getString(R.string.app_name_title);
                default:
                    return cn.getClassName();

            }
        } else {
            return getString(R.string.app_name);
        }
    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void finish() {
        super.finish();

        //Identificação do comportamento da transição entre as activity
        overridePendingTransition(R.anim.slide_in_left_to_rigth, R.anim.slide_out_left_to_rigth);
    }

    @Override
    public void showLoading() {
        if (!isFinishing()) {
            loading = new ProgressDialog(BaseActivity.this);
            loading.setCancelable(false);
            loading.setMessage(getString(R.string.loading));
            loading.show();
        }

    }

    @Override
    public void hideLoading() {
        if (!isFinishing()) {
            if (loading.isShowing())
                loading.dismiss();
        }
    }

    @Override
    public void showMessageError(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Alerta")
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).create().show();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imv_icon_toolbar:
                finish();
        }
    }
}
