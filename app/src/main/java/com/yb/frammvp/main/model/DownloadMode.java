package com.yb.frammvp.main.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.yb.ilibray.data.download.down.DownloadStatus;
import com.yb.ilibray.data.download.down.entity.DownloadEntity;

/**
 * Created by ericYang on 2017/6/23.
 * Email:eric.yang@huanmedia.com
 * what?
 */

public class DownloadMode implements Parcelable {
    private String fixName;
    private DownloadStatus mDownloadStatus;

    public String getFixName() {
        return fixName;
    }

    public DownloadMode setFixName(String fixName) {
        this.fixName = fixName;
        return this;
    }
    public DownloadEntity toDownloadEntity(){
      return new DownloadEntity()
                .setDownloadSize(getDownloadStatus().getDownloadSize())
                .setContextSize(getDownloadStatus().getTotalSize())
                .setStatus(getDownloadStatus().getState())
                .setFixName(getFixName())
                .setId(getDownloadStatus().getId())
                .setUrl(getDownloadStatus().getUrl());
    }

    public DownloadStatus getDownloadStatus() {
        return mDownloadStatus;
    }

    public DownloadMode setDownloadStatus(DownloadStatus downloadStatus) {
        mDownloadStatus = downloadStatus;
        return this;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.fixName);
        dest.writeParcelable(this.mDownloadStatus, flags);
    }

    public DownloadMode() {
    }

    protected DownloadMode(Parcel in) {
        this.fixName = in.readString();
        this.mDownloadStatus = in.readParcelable(DownloadStatus.class.getClassLoader());
    }

    public static final Parcelable.Creator<DownloadMode> CREATOR = new Parcelable.Creator<DownloadMode>() {
        @Override
        public DownloadMode createFromParcel(Parcel source) {
            return new DownloadMode(source);
        }

        @Override
        public DownloadMode[] newArray(int size) {
            return new DownloadMode[size];
        }
    };
}
