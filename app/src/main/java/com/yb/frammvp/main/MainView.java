package com.yb.frammvp.main;

import com.yb.frammvp.main.model.UserModel;
import com.yb.ilibray.view.LoadDataView;

import java.util.List;

/**
 * Created by ericYang on 2017/5/19.
 * Email:eric.yang@huanmedia.com
 * what?
 */

public interface MainView extends LoadDataView {
    void showUserList(List<UserModel> models);
    void showUser(UserModel models);
}
