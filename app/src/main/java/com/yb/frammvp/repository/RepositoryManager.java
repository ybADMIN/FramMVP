package com.yb.frammvp.repository;

import android.support.annotation.IntDef;

import com.yb.frammvp.common.FApplication;
import com.yb.frammvp.common.manager.ResourceManager;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ericYang on 2017/5/24.
 * Email:eric.yang@huanmedia.com
 * what?
 */

public class RepositoryManager {
    public static final int USERREPOSITORY=1;

    /**
     * defined Repository Type
     */
    @IntDef({USERREPOSITORY})
    @Retention(RetentionPolicy.SOURCE)
    public  @interface  RepositoryTpye {
    }


    private Map<Integer,DataSource> repositorys =new HashMap<>();
    private static RepositoryManager instance;

    private RepositoryManager() {
        initRepository();
    }

    private void initRepository() {
        repositorys.put(USERREPOSITORY
                ,new UserRepository(FApplication.getApplication()
                ,ResourceManager.getInstance().getApiService()
                ,ResourceManager.getInstance().getCacheProviders()
                ,ResourceManager.getInstance().getDefaultThreadHandler()));
    }

    /**
     * get instance for type
     * @param type
     * @return
     */
    public DataSource getRepositorys(@RepositoryTpye int type) {
        return repositorys.get(type);
    }

    /**
     * RepositoryManager be use for manager Repository instance
     * @return
     */
    public static RepositoryManager getInstance() {
        if (instance==null){
            synchronized (RepositoryManager.class){
                if (instance==null){
                    instance=new RepositoryManager();
                }
            }
        }
        return instance;
    }

}
