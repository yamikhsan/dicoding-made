package com.studio.yami.moviecataloguefinal.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.studio.yami.moviecataloguefinal.Const;
import com.studio.yami.moviecataloguefinal.R;
import com.studio.yami.moviecataloguefinal.databinding.ActivityMainBinding;
import com.studio.yami.moviecataloguefinal.fragment.MovieFragment;
import com.studio.yami.moviecataloguefinal.fragment.SearchFragment;
import com.studio.yami.moviecataloguefinal.fragment.TvFragment;
import com.studio.yami.moviecataloguefinal.fragment.favorite.FavoriteFragment;
import com.studio.yami.moviecataloguefinal.model.Favorite;
import com.studio.yami.moviecataloguefinal.vm.MainViewModel;
import com.studio.yami.moviecataloguefinal.widget.FavoriteWidget;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private MovieFragment movie = new MovieFragment();
    private TvFragment tv = new TvFragment();
    private FavoriteFragment favorite = new FavoriteFragment();
    private SearchFragment search = new SearchFragment();

    private MainViewModel viewModel;
    private LocalBroadcastManager localBroadcastManager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_movies:
                    startFragment(movie);
                    return true;

                case R.id.nav_tv:
                    startFragment(tv);
                    return true;

                case R.id.nav_favorite:
                    startFragment(favorite);
                    return true;
                case R.id.nav_search:
                    startFragment(search);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        Toolbar toolbar = mainBinding.toolbar;
        setSupportActionBar(toolbar);

        BottomNavigationView navigation = mainBinding.navView;
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if (savedInstanceState == null) {
            navigation.setSelectedItemId(R.id.nav_movies);
        }

        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        IntentFilter filter = new IntentFilter(Const.DELETE);
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(mMessageReceiver, filter);

    }

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            Favorite fav = intent.getParcelableExtra(Const.FAVORITE);

            if (Objects.requireNonNull(intent.getAction()).matches(Const.DELETE)) {
                delDialog(fav);
            }
        }
    };

    private void delDialog(final Favorite favorite) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setMessage(getResources().getString(R.string.dialog_msg))
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        viewModel.deleteFav(favorite);
                        FavoriteWidget.updateFavoriteWidget(getApplicationContext());
                    }
                })
                .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

        AlertDialog build = builder.create();
        build.show();
    }

    private void startFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fr_container, fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.settings:
                Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(intent);
                break;
            case R.id.reminder:
                Intent reminder = new Intent(this, ReminderActivity.class);
                startActivity(reminder);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        localBroadcastManager.unregisterReceiver(mMessageReceiver);
    }

}
