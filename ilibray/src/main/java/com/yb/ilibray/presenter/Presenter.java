package com.yb.ilibray.presenter;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class Presenter {
 private CompositeDisposable mCompositeDisposable=new CompositeDisposable();
  /**
   * Method that control the lifecycle of the view. It should be called in the view's
   * (Activity or Fragment) onResume() method.
   */
 public void resume(){

 }

  /**
   * Method that control the lifecycle of the view. It should be called in the view's
   * (Activity or Fragment) onPause() method.
   */
  public void pause(){

  }

  /**
   * Method that control the lifecycle of the view. It should be called in the view's
   * (Activity or Fragment) onDestroy() method.
   */
  public abstract void destroy();

  public void addDisposable(Disposable disposable){
     mCompositeDisposable.add(disposable);
  }

  public void dispose() {
    if (!mCompositeDisposable.isDisposed()) {
      mCompositeDisposable.dispose();
    }
  }
}