package com.yb.ilibray.data.download.progress;

import okhttp3.Request;

public interface RequestFilter {
    boolean listensFor(Request request);
}
