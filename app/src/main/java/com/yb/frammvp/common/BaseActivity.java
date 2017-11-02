package com.yb.frammvp.common;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.yb.frammvp.common.manager.ActivitManager;
import com.yb.frammvp.common.manager.ResourceManager;
import com.yb.frammvp.common.navigation.Navigator;
import com.yb.ilibray.presenter.Presenter;


/**
 * Base {@link android.app.Activity} class for every Activity in this application.
 */
public abstract class BaseActivity extends AppCompatActivity {

  Navigator navigator= ResourceManager.getInstance().getNavigator();
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivitManager.getAppManager().addActivity(this);
  }

  public Navigator getNavigator() {
    return navigator;
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    ActivitManager.getAppManager().removeActivity(this);
    if (getPresenter()!=null){
      getPresenter().destroy();
    }
  }

  @Override
  protected void onResume() {
    super.onResume();
    if (getPresenter()!=null){
      getPresenter().resume();
    }
  }
  @Override
  protected void onPause() {
    super.onPause();
    if (getPresenter()!=null){
      getPresenter().pause();
    }
  }

  protected abstract Presenter getPresenter();
}
