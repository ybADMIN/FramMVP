package com.yb.frammvp.repository;


import android.content.Context;

import com.yb.frammvp.common.net.CacheProviders;
import com.yb.frammvp.common.net.RemoteApiService;
import com.yb.frammvp.repository.bean.User;
import com.yb.frammvp.repository.local.LocalUserDataSource;
import com.yb.frammvp.repository.remote.RemoteUserDataSource;
import com.yb.ilibray.data.dispose.interactor.DefaultThreadHandler;
import com.yb.ilibray.data.dispose.interactor.ThreadExecutorHandler;
import com.yb.ilibray.data.download.down.entity.DownloadEntity;

import java.util.List;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.Reply;

/**
 * Created by ericYang on 2017/5/18.
 * Email:eric.yang@huanmedia.com
 * what?
 */
@SuppressWarnings("unchecked")
public class UserRepository implements UseDataSource {
    private final DefaultThreadHandler mThreadHandler;
    private final CacheProviders mCacheProviders;
    private UseDataSource mRemoteDataSource;
    private UseDataSource mLocalDataSource;

    /**
     * create user repository
     *
     * @param context        context
     * @param apiService     remote api
     * @param cacheProviders cache providers
     * @param threadHandler  default thread dispatch
     */
    public UserRepository(Context context, RemoteApiService apiService, CacheProviders cacheProviders, DefaultThreadHandler threadHandler) {
        this.mThreadHandler = threadHandler;
        this.mRemoteDataSource = new RemoteUserDataSource(apiService);
        this.mLocalDataSource = new LocalUserDataSource(context);
        this.mCacheProviders = cacheProviders;
    }

    public Observable<List<User>> users() {
        Observable<Reply<List<User>>> observable = mCacheProviders.getUsers(mRemoteDataSource.users(), new EvictProvider(false));
        return new ThreadExecutorHandler<Reply<List<User>>>(mThreadHandler).defulThread(observable)
                .map(Reply::getData);
    }

    public Observable<User> user(int userId) {
        Observable<Reply<User>> observable = mCacheProviders.getUser(mRemoteDataSource.user(userId), new DynamicKey(userId), new EvictDynamicKey(false));
        return new ThreadExecutorHandler<User>(mThreadHandler).defulThread2Data(observable);
    }
    public Observable<List<DownloadEntity>> getFilelist() {
        return new ThreadExecutorHandler<List<DownloadEntity>>(mThreadHandler).defulThread(mRemoteDataSource.getFilelist());
    }
}
