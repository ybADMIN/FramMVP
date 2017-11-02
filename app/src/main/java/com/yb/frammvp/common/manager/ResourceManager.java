package com.yb.frammvp.common.manager;

import android.content.Context;

import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.yb.frammvp.BuildConfig;
import com.yb.frammvp.common.FApplication;
import com.yb.frammvp.common.navigation.Navigator;
import com.yb.frammvp.common.net.CacheProviders;
import com.yb.frammvp.common.net.RemoteApiService;
import com.yb.ilibray.data.dispose.interactor.DefaultThreadHandler;

/**
 * Created by ericYang on 2017/5/24.
 * Email:eric.yang@huanmedia.com
 * what?
 */

public class ResourceManager {
    private static ResourceManager instance;
    private DefaultThreadHandler mDefaultThreadHandler;
    private RemoteApiService mApiService;
    private Navigator navigator;
    private CacheProviders mCacheProviders;

    private ResourceManager(Context context) {
        init(context);
    }

    private void init(Context context) {
        mDefaultThreadHandler =DefaultThreadHandler.getInstance();
        mApiService = RemoteApiService.Factory.createService();
        mCacheProviders = CacheProviders.Factory.createProviders(context);
        navigator = new Navigator();
        Logger.init(ResourceManager.class.getSimpleName()).logLevel(BuildConfig.DEBUG?LogLevel.FULL:LogLevel.NONE).hideThreadInfo().methodCount(0);
    }

    public static ResourceManager getInstance() {
        if (instance == null) {
            synchronized (ResourceManager.class) {
                if (instance == null) {
                    instance = new ResourceManager(FApplication.getApplication());
                }
            }
        }
        return instance;
    }

    public Navigator getNavigator() {
        return navigator;
    }

    public DefaultThreadHandler getDefaultThreadHandler() {
        return mDefaultThreadHandler;
    }

    public RemoteApiService getApiService() {
        return mApiService;
    }

    public CacheProviders getCacheProviders() {
        return mCacheProviders;
    }
}
