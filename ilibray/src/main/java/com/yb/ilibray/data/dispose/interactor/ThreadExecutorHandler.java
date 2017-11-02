package com.yb.ilibray.data.dispose.interactor;


import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.rx_cache2.Reply;

/**
 * Created by ericYang on 2017/5/19.
 * Email:eric.yang@huanmedia.com
 * what?
 */
public class  ThreadExecutorHandler<T>{
    private final DefaultThreadHandler mThreadHandler;

    public ThreadExecutorHandler(DefaultThreadHandler handler) {
        this.mThreadHandler = handler;
    }

    public Observable<T> defulThread2Data(Observable<Reply<T>> observable){
        return observable
                .observeOn(mThreadHandler.getPostExcution().getScheduler())
                .subscribeOn(Schedulers.from(mThreadHandler.getThreadExecutor()))
                .map(Reply::getData);
    }
    public Observable<T> defulThread(Observable<T> observable){
        return observable
                .observeOn(mThreadHandler.getPostExcution().getScheduler())
                .subscribeOn(Schedulers.from(mThreadHandler.getThreadExecutor()));
    }
}
