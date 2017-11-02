package com.yb.frammvp.common.net;

import android.content.Context;

import com.yb.ilibray.data.cache.FilePathManager;
import com.yb.frammvp.repository.bean.User;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EncryptKey;
import io.rx_cache2.EvictDynamicKey;
import io.rx_cache2.EvictProvider;
import io.rx_cache2.LifeCache;
import io.rx_cache2.Reply;
import io.rx_cache2.internal.RxCache;
import io.victoralbertos.jolyglot.GsonSpeaker;

/**
 * Created by ericYang on 2017/5/25.
 * Email:eric.yang@huanmedia.com
 * what?
 */
@EncryptKey("A@B$$5aa")
public interface CacheProviders {
    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<User>> getUser(Observable<User> oUsers, DynamicKey uid, EvictDynamicKey evictDynamicKey);

    @LifeCache(duration = 2, timeUnit = TimeUnit.MINUTES)
    Observable<Reply<List<User>>> getUsers(Observable<List<User>> oUsers, EvictProvider evictProvider);

    public static class Factory {
        private Factory() {
        }

        public static CacheProviders createProviders(Context context) {

            return new RxCache.Builder()
                    .setMaxMBPersistenceCache(50)//Max Cache 50 MB
                    .persistence(
                            FilePathManager.getHttpCacheDir(context)
                            , new GsonSpeaker())
                    .using(CacheProviders.class);
        }
    }
}
