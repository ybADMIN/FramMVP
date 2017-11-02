package com.yb.frammvp.repository.local;

import android.content.Context;

import com.yb.frammvp.repository.UseDataSource;
import com.yb.frammvp.repository.bean.User;
import com.yb.ilibray.data.download.down.entity.DownloadEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by ericYang on 2017/5/18.
 * Email:eric.yang@huanmedia.com
 * 用户本地资源库 // FIXME: 2017/8/4 数据库功能没有加入
 */
public class LocalUserDataSource implements UseDataSource{
    private final Context mContext;

    public LocalUserDataSource(Context context) {
        this.mContext=context;
    }

    public Observable<List<User>> users() {
        return null;
    }

    public Observable<User> user(int userId) {
        return null;
    }

    public Observable<List<DownloadEntity>> getFilelist() {
        //no use
        return null;
    }
}
