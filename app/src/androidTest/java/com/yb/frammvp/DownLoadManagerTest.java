package com.yb.frammvp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import com.yb.ilibray.data.download.down.DownLoadManager;
import com.yb.ilibray.data.download.down.entity.DownloadEntity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by ericYang on 2017/6/15.
 * Email:eric.yang@huanmedia.com
 * what?
 */
@RunWith(AndroidJUnit4.class)
@MediumTest
public class DownLoadManagerTest {
    private Context instrumentationCtx;

    @Before
    public void setup() {
        instrumentationCtx = InstrumentationRegistry.getContext();
    }
    @Test
    public void download(){
        String url = "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk";
        DownloadEntity downloadEntity = new DownloadEntity().setUrl(url).setMethod("GET");
        DownLoadManager.getInstance(instrumentationCtx).addDownLoad(downloadEntity);
        try {
            Thread.sleep(2000*10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}