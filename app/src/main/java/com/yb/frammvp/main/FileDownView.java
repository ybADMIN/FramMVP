package com.yb.frammvp.main;

import android.content.Context;

import com.yb.frammvp.main.model.DownloadMode;

import java.util.List;

/**
 * Created by ericYang on 2017/6/2.
 * Email:eric.yang@huanmedia.com
 * 文件下载的View
 */

interface FileDownView {
    Context context();

    void showDownLoadList(List<DownloadMode> downloadTasks);
}
