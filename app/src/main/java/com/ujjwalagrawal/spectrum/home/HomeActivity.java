package com.ujjwalagrawal.spectrum.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.VideoView;

import com.crashlytics.android.Crashlytics;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.ujjwalagrawal.spectrum.R;

import com.ujjwalagrawal.spectrum.home.view.HomeFragment;

import com.ujjwalagrawal.spectrum.events.event_list.view.EventTitleListFragment;
import com.ujjwalagrawal.spectrum.helper.SharedPrefs;
import com.ujjwalagrawal.spectrum.login.view.LoginActivity;
import com.ujjwalagrawal.spectrum.profile.view.ProfileFragment;
import com.ujjwalagrawal.spectrum.teams.TeamsFragment;

//import com.ujjwalagrawal.spectrum.team.view.TeamFragment;


import io.fabric.sdk.android.Fabric;

public class HomeActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    Context context;
    HomeActivity homeActivity;
    SharedPrefs sharedPrefs;
    private VideoView video1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_home);
        context = this;
        homeActivity = this;

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_profile) {

                    // The tab with id R.id.tab_favorites was selected,
                    // change your content accordingly.
                    ProfileFragment profile = new ProfileFragment();
                    setFragment(profile);
                } else if (tabId == R.id.tab_events) {
                    EventTitleListFragment eventTitleListFragment = new EventTitleListFragment();
                    setFragment(eventTitleListFragment);
                } else if (tabId == R.id.tab_home) {
                    HomeFragment homeFragment = new HomeFragment();
                    setFragment(homeFragment);

                } else if (tabId == R.id.tab_logout) {
//                    ChatFragment sponsorsFragment = new SponsorsFragment();
//                    setFragment(sponsorsFragment);
                    final AlertDialog ad = new AlertDialog.Builder(context)
                            .create();
                    ad.setCancelable(false);
                    ad.setTitle("Logout");
                    ad.setMessage("Do you really want to logout");
                    ad.setButton(DialogInterface.BUTTON_POSITIVE, "Logout", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ad.cancel();
                            sharedPrefs = new SharedPrefs(context);
                            sharedPrefs.setAccessToken("");
                            sharedPrefs.setMobile("");
                            sharedPrefs.setUsername("");
                            Intent intent = new Intent(homeActivity, LoginActivity.class);
                            startActivity(intent);
                        }
                    });
                    ad.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ad.cancel();

                        }
                    });
                    ad.show();


                } else if (tabId == R.id.tab_aboutus) {

                    TeamsFragment teamFragment = new TeamsFragment();
                    setFragment(teamFragment);
                }

            }
        });
    }

    public void setFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.contentContainer, fragment);
            fragmentTransaction.commit();
        }
    }
}