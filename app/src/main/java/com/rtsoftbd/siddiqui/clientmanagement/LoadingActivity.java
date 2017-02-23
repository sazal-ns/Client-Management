package com.rtsoftbd.siddiqui.clientmanagement;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rtsoftbd.siddiqui.clientmanagement.adapter.FillablePagesAdapter;
import com.rtsoftbd.siddiqui.clientmanagement.helper.ResettableView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadingActivity extends AppCompatActivity {

    @BindView(R.id.pager) ViewPager pager;

    private static int SPLASH_TIME_OUT = 8000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        ButterKnife.bind(this);

        setupPagination();
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
                finish();
            }
        }, SPLASH_TIME_OUT);


    }

    private void setupPagination() {
        pager = (ViewPager) findViewById(R.id.pager);
        final FillablePagesAdapter adapter = new FillablePagesAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override public void onPageSelected(int position) {
                super.onPageSelected(position);
                ((ResettableView) adapter.getItem(position)).reset();
            }
        });

        pager.post(new Runnable() {
            @Override public void run() {
                ((ResettableView) adapter.getItem(1)).reset();
            }
        });
    }
    }

