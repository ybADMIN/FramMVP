package com.yb.frammvp.main;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.yb.frammvp.repository.RepositoryManager;
import com.yb.frammvp.common.exception.ErrorMessageFactory;
import com.yb.frammvp.main.model.mapper.UserModelDataMapper;
import com.yb.frammvp.main.model.UserModel;
import com.yb.frammvp.repository.UserRepository;
import com.yb.ilibray.presenter.Presenter;

import java.util.List;


/**
 * Created by ericYang on 2017/5/18.
 * Email:eric.yang@huanmedia.com
 * what?
 */
public class UserPresenter extends Presenter {
    private final UserRepository mRepository;
    private MainView mView;
    private UserModelDataMapper mUserModelDataMapper=new UserModelDataMapper();
    public UserPresenter() {
        this.mRepository= (UserRepository) RepositoryManager.getInstance().getRepositorys(RepositoryManager.USERREPOSITORY);
    }

    public void setView(@NonNull MainView view){
        this.mView = view;
    }

    public void getUserList() {
        addDisposable(mRepository.users().subscribe(users -> {
            List<UserModel> userModels = (List<UserModel>) mUserModelDataMapper.transform(users);
            long time = System.currentTimeMillis();
            mView.showUserList(userModels);
            Logger.d("消耗时间：%d",+(System.currentTimeMillis()-time));
        },throwable -> Toast.makeText(mView.context(), ErrorMessageFactory.create(mView.context(),throwable), Toast.LENGTH_SHORT).show()));
    }
    public void getUser(int uid) {
        addDisposable(mRepository.user(uid).subscribe(users -> {
            UserModel userModel = mUserModelDataMapper.transform(users);
            long time = System.currentTimeMillis();
            mView.showUser(userModel);
            Logger.d("消耗时间：%d",+(System.currentTimeMillis()-time));
        },throwable -> Toast.makeText(mView.context(), ErrorMessageFactory.create(mView.context(),throwable), Toast.LENGTH_SHORT).show()));
    }


    @Override
    public void resume() {
        getUserList();
    }

    @Override
    public void destroy() {
        dispose();
    }
}
