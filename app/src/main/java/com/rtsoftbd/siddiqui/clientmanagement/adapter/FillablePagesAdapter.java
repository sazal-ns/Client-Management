package com.rtsoftbd.siddiqui.clientmanagement.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.rtsoftbd.siddiqui.clientmanagement.helper.FillableLoaderPage;


public class FillablePagesAdapter extends FragmentStatePagerAdapter {

  private FillableLoaderPage animPage;

  public FillablePagesAdapter(FragmentManager fm) {
    super(fm);
    animPage = FillableLoaderPage.newInstance();
  }

  @Override public Fragment getItem(int position) {
    return animPage;
  }

  @Override public int getCount() {
    return 1;
  }

}