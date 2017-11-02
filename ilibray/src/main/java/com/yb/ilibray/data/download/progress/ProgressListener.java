package com.yb.ilibray.data.download.progress;

public interface ProgressListener {
    void progressIndeterminate();
    void onProgress(long contentLength, long downloadLength,boolean isDone);
}
