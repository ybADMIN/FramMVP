package com.yb.frammvp.repository.remote;


import com.yb.frammvp.common.net.RemoteApiService;
import com.yb.frammvp.common.net.entity.mapper.FileEntityDataMapper;
import com.yb.frammvp.common.net.entity.mapper.UserEntityDataMapper;
import com.yb.frammvp.repository.UseDataSource;
import com.yb.frammvp.repository.bean.User;
import com.yb.ilibray.data.dispose.exception.UserNotFoundException;
import com.yb.ilibray.data.download.down.entity.DownloadEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by ericYang on 2017/5/18.
 * Email:eric.yang@huanmedia.com
 * what?
 */
public class RemoteUserDataSource implements UseDataSource {
    private final RemoteApiService mRemoteApiService;
    private final UserEntityDataMapper mMapper;
    private final FileEntityDataMapper mFileMapper;

    public RemoteUserDataSource(RemoteApiService remoteApiService) {
        this.mRemoteApiService=remoteApiService;
        this.mMapper = new UserEntityDataMapper();
        this.mFileMapper = new FileEntityDataMapper();
    }

    public Observable<List<User>> users() {
        return mRemoteApiService.getUsers().map(listDataResponse -> {
            if (listDataResponse==null || listDataResponse.getResult()==null){
                throw new UserNotFoundException();
            }else {
               return mMapper.transform(listDataResponse.getResult());
            }
        });
    }

    public Observable<User> user(int userId) {
        return mRemoteApiService.getUser(userId).map(userEntityDataResponse -> {
            if (userEntityDataResponse==null || userEntityDataResponse.getResult()==null){
                throw  new UserNotFoundException();
            }else {
               return mMapper.transform(userEntityDataResponse.getResult());
            }
        });
    }
    public Observable<List<DownloadEntity>> getFilelist() {
        return mRemoteApiService.getFileList().map(listDataResponse -> {
            if (listDataResponse==null || listDataResponse.getResult()==null){
                throw  new UserNotFoundException();
            }else {
                return mFileMapper.transform(listDataResponse.getResult());
            }
        });
    }
}
