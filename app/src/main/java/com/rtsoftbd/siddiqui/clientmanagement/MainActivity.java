package com.rtsoftbd.siddiqui.clientmanagement;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.rtsoftbd.siddiqui.clientmanagement.model.User;

public class MainActivity extends AppCompatActivity{

    private static final String TAG_DASHBOARD       = "DASHBOARD";
    private static final String TAG_ADD_CLIENT      = "ADD CLIENT";
    private static final String TAG_CLIENT_LIST     = "CLIENT LIST";
    private static final String TAG_CREDIT_PAYMENT  = "CREDIT PAYMENT";
    private static final String TAG_PAID_PAYMENT    = "PAID PAYMENT";
    private static final String TAG_CREDIT_HISTORY  = "CREDIT HISTORY";
    private static final String TAG_PAID_HISTORY    = "PAID HISTORY";
    private static final String TAG_TOTAL_HISTORY   = "TOTAL HISTORY";
    private static final String TAG_CHANGE_PASSWORD = "CHANGE PASSWORD";
    private static final String TAG_PROFILE_UPDATE  = "PROFILE UPDATE";

    private static String CURRENT_TAG = TAG_DASHBOARD;

    public static int navItemIndex = 0;

    private String[] activityTitles;

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private ActionBarDrawerToggle toggle;

    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (User.getPermission()!=1) {
            Menu m = navigationView.getMenu();
            m.findItem(R.id.addClient).setVisible(false);
            m.findItem(R.id.clientList).setVisible(false);
            m.findItem(R.id.creditPayment).setVisible(false);
            m.findItem(R.id.debitPayment).setVisible(false);
            m.findItem(R.id.creditHistory).setVisible(false);
            m.findItem(R.id.paidHistory).setVisible(false);
        }
        if (User.getPermission()==1) {
            activityTitles = getResources().getStringArray(R.array.nav_item_activity_title);
            setUpNavigationView();
        }
        else {
            activityTitles = getResources().getStringArray(R.array.nav_item_activity_title_client);
            setUpNavigationViewClient();
        }



        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_DASHBOARD;
            loadHomeFragment();
        }
    }

    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dashboard:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_DASHBOARD;
                        break;
                    case R.id.addClient:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_ADD_CLIENT;
                        break;
                    case R.id.clientList:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_CLIENT_LIST;
                        break;
                    case R.id.creditPayment:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_CREDIT_PAYMENT;
                        break;
                    case R.id.debitPayment:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_PAID_PAYMENT;
                        break;
                    case R.id.creditHistory:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_CREDIT_HISTORY;
                        break;
                    case R.id.paidHistory:
                        navItemIndex = 6;
                        CURRENT_TAG = TAG_PAID_HISTORY;
                        break;
                    case R.id.totalHistory:
                        navItemIndex = 7;
                        CURRENT_TAG = TAG_TOTAL_HISTORY;
                        break;
                    case R.id.changePassword:
                        navItemIndex = 8;
                        CURRENT_TAG = TAG_CHANGE_PASSWORD;
                        break;
                    case R.id.profileUpdate:
                        navItemIndex = 9;
                        CURRENT_TAG = TAG_PROFILE_UPDATE;
                        break;
                    case R.id.logout:
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                        break;
                    default:
                        navItemIndex =0;
                }

                loadHomeFragment();
                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    private void setUpNavigationViewClient() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.dashboard:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_DASHBOARD;
                        break;
                    case R.id.totalHistory:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_TOTAL_HISTORY;
                        break;
                    case R.id.changePassword:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_CHANGE_PASSWORD;
                        break;
                    case R.id.profileUpdate:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_PROFILE_UPDATE;
                        break;
                    case R.id.logout:
                        startActivity(new Intent(MainActivity.this, LoginActivity.class));
                        finish();
                        break;
                    default:
                        navItemIndex =0;
                }

                loadHomeFragment();
                return true;
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }
            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    private void loadHomeFragment() {

        // set toolbar title
        setToolbarTitle();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = null;
                if (User.getPermission()==1) fragment = getHomeFragment();
                else fragment = getHomeFragmentClient();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        if (runnable != null) {
            mHandler.post(runnable);
        }

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 1:
                return new AddClientFragment();
            case 2:
                return new ClientListFragment();
            case 3:
                return new CreditPaymentFragment();
            case 4:
                return new CreditPaymentFragment();
            case 5:
                return new CreditHistoryFragment();
            case 6:
                return new PaidHistoryFragment();
            case 7:
                return new TotalHistoryFragment();
            case 8:
                return new ChangePasswordFragment();
            case 9:
                return new EditClientFragment();
            default:
                return new DashboardFragment();
        }
    }

    private Fragment getHomeFragmentClient() {
        switch (navItemIndex) {
            case 1:
                return new ClientFragment();
            case 2:
                return new ChangePasswordFragment();
            case 3:
                return new EditClientFragment();
            default:
                return new DashboardFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }
        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_DASHBOARD;
                loadHomeFragment();
                return;
            }

            new AlertDialog.Builder(MainActivity.this)
                    .setIcon(getResources().getDrawable(R.drawable.ic_power_settings_new_red_a700_36dp))
                    .setTitle(getResources().getString(R.string.exitApp))
                    .setMessage(getResources().getString(R.string.areYouSure))
                    .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton(getResources().getString(R.string.no), null)
                    .show();
        }
        //super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
