package com.rtsoftbd.siddiqui.clientmanagement.helper;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.jorgecastillo.FillableLoader;
import com.github.jorgecastillo.listener.OnStateChangeListener;

import com.rtsoftbd.siddiqui.clientmanagement.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FillableLoaderPage extends Fragment implements OnStateChangeListener, ResettableView {

  @BindView(R.id.fillableLoader) @Nullable
  FillableLoader fillableLoader;
  private View rootView;

  public static FillableLoaderPage newInstance() {
    FillableLoaderPage page = new FillableLoaderPage();
    Bundle bundle = new Bundle();
    page.setArguments(bundle);

    return page;
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    rootView = inflater.inflate(R.layout.animation_page, container, false);

    return rootView;
  }

  @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    ButterKnife.bind(this, rootView);
    setupFillableLoader();
  }

  private void setupFillableLoader() {

      fillableLoader.setSvgPath(Paths.SVG_LOGO);

    fillableLoader.setOnStateChangeListener(this);
  }



  @Override public void reset() {
    fillableLoader.reset();

    fillableLoader.postDelayed(new Runnable() {
      @Override public void run() {
        fillableLoader.start();
      }
    }, 250);
  }

  @Override
  public void onStateChange(int state) {

  }
}
