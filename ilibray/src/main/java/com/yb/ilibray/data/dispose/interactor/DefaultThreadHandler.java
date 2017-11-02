package com.yb.ilibray.data.dispose.interactor;


import com.yb.ilibray.data.dispose.executor.JobExecutor;
import com.yb.ilibray.data.dispose.executor.PostExecutionThread;
import com.yb.ilibray.data.dispose.executor.ThreadExecutor;
import com.yb.ilibray.data.dispose.executor.UIThread;

/**
 * Created by ericYang on 2017/5/19.
 * Email:eric.yang@huanmedia.com
 * what?
 */
public  class DefaultThreadHandler {
    private static DefaultThreadHandler instance;
    private JobExecutor mJobExecutor;
    private UIThread mUIThread;
    private DefaultThreadHandler() {
        this.mJobExecutor=new JobExecutor();
        this.mUIThread=new UIThread();
    }
    public static DefaultThreadHandler getInstance() {
        if (instance==null){
            synchronized (DefaultThreadHandler.class)
            {
                if (instance==null){
                    instance = new DefaultThreadHandler();
                }
            }
        }
        return instance;
    }

    public ThreadExecutor getThreadExecutor() {
        return mJobExecutor;
    }

    public PostExecutionThread getPostExcution() {
        return mUIThread;
    }
}
