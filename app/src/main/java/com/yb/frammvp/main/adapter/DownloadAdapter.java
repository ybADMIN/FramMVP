package com.yb.frammvp.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yb.frammvp.R;
import com.yb.frammvp.main.model.DownloadMode;
import com.yb.ilibray.data.download.down.DownloadStatus;
import com.yb.ilibray.data.download.down.DownloadTask;
import com.yb.ilibray.data.download.down.entity.DownloadEntity;
import com.yb.ilibray.utils.data.assist.Check;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ericYang on 2017/6/2.
 * Email:eric.yang@huanmedia.com
 * what?
 */

public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.DownViewHolder> {
    private List<DownloadMode> mDownloadModes = new ArrayList<>();
    private DownloadListener mDownloadListener;
    private int mHolderId;
    Map<Integer, Integer> positions = new HashMap<>();

    public DownloadAdapter() {
    }

    public void setDownloadListener(DownloadListener downloadListener) {
        mDownloadListener = downloadListener;
    }

    public void setDownloadModes(List<DownloadMode> downloadModes) {
        mDownloadModes = downloadModes;
    }

    public List<DownloadMode> getDownloadModes() {
        return mDownloadModes;
    }

    @Override
    public DownViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dashborad, null);
        mHolderId++;
        DownViewHolder holder = new DownViewHolder(view, mHolderId);
        return holder;
    }

    @Override
    public void onBindViewHolder(DownViewHolder holder, int position) {
        DownloadMode downloadTask = mDownloadModes.get(position);
        holder.mFilename.setText(Check.checkReplace(downloadTask.getFixName(), downloadTask.getDownloadStatus().getUrl()));
        if (mDownloadListener != null)
            holder.mBtnDownload.setOnClickListener(new StateClickListener(downloadTask, mDownloadListener));
        downingUpUi(downloadTask, holder);
    }


    private void downingUpUi(DownloadMode downloadTask, DownViewHolder holder) {
        holder.mProgressBar.setProgress(0);
        holder.mBtnDownload.setEnabled(true);
        holder.mSpeendTv.setVisibility(View.GONE);
        holder.mHintText.setVisibility(View.GONE);
        holder.mTvPercent.setVisibility(View.GONE);
        DownloadStatus status = downloadTask.getDownloadStatus();
        switch (status.getState()) {
            case DownloadTask.ERROR:
                holder.mHintText.setVisibility(View.VISIBLE);
                holder.mSpeendTv.setVisibility(View.GONE);
                holder.mHintText.setText(Check.checkReplace(status.getDescribe(),"下载失败"));
                holder.mBtnDownload.setText("重试");
                break;
            case DownloadTask.FINISH:
                holder.mTvPercent.setVisibility(View.VISIBLE);
                holder.mHintText.setVisibility(View.VISIBLE);
                holder.mSpeendTv.setText("");
                holder.mProgressBar.setProgress((int) status.getPercentNumber());
                holder.mHintText.setText(String.format("%s/%s", status.getFormatDownloadSize(), status.getFormatTotalSize()));
                holder.mTvPercent.setText(status.getPercent());
                holder.mBtnDownload.setText("完成");
                holder.mBtnDownload.setEnabled(false);
                holder.mBtnDownload.setOnClickListener(null);
                break;
            case DownloadTask.LOADING:
                holder.mBtnDownload.setText("暂停");
                holder.mTvPercent.setVisibility(View.VISIBLE);
                holder.mHintText.setVisibility(View.VISIBLE);
                holder.mSpeendTv.setVisibility(View.VISIBLE);
                holder.mSpeendTv.setText(status.getDownloadSpeed());
                holder.mProgressBar.setProgress((int) status.getPercentNumber());
                holder.mTvPercent.setText(status.getPercent());
                holder.mHintText.setText(String.format(Locale.getDefault(), "%s/%s"
                        , downloadTask.getDownloadStatus().getFormatDownloadSize()
                        , downloadTask.getDownloadStatus().getFormatTotalSize()));
                break;
            case DownloadTask.PAUSE:
                holder.mBtnDownload.setText("继续");
                break;
            case DownloadTask.START:
                holder.mTvPercent.setVisibility(View.VISIBLE);
                holder.mHintText.setVisibility(View.VISIBLE);
                holder.mSpeendTv.setVisibility(View.VISIBLE);
                holder.mProgressBar.setVisibility(View.VISIBLE);
                holder.mBtnDownload.setText("暂停");
                break;
            case DownloadTask.DEFAULT:
                holder.mBtnDownload.setText("下载");
                break;
            case DownloadTask.PREPARE:
                holder.mTvPercent.setVisibility(View.VISIBLE);
                holder.mHintText.setVisibility(View.VISIBLE);
                holder.mHintText.setText("创建任务...");
                break;

        }
    }

    @Override
    public int getItemCount() {
        return mDownloadModes.size();
    }

    static class DownViewHolder extends RecyclerView.ViewHolder {
        private final int holderId;
        @BindView(R.id.filename)
        TextView mFilename;
        @BindView(R.id.btn_download)
        Button mBtnDownload;
        @BindView(R.id.tv_percent)
        TextView mTvPercent;
        @BindView(R.id.progressBar)
        ProgressBar mProgressBar;
        @BindView(R.id.hint_text)
        TextView mHintText;
        @BindView(R.id.speendtv)
        TextView mSpeendTv;

        public DownViewHolder(View itemView, int id) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.holderId = id;
        }
    }

    public class StateClickListener implements View.OnClickListener {
        private final DownloadMode mDownloadTask;
        private final DownloadListener downloadlistener;

        public StateClickListener(DownloadMode downloadTask, DownloadListener downloadListener) {
            this.downloadlistener = downloadListener;
            this.mDownloadTask = downloadTask;
        }

        @Override
        public void onClick(View v) {
            switch (mDownloadTask.getDownloadStatus().getState()) {
                case DownloadTask.DEFAULT:
                case DownloadTask.ERROR:
                case DownloadTask.PAUSE:
                    downloadlistener.download(mDownloadTask.toDownloadEntity());
                    break;
                case DownloadTask.FINISH:
                    v.setOnClickListener(null);
                    break;
                case DownloadTask.START:
                case DownloadTask.LOADING:
                    downloadlistener.pause(mDownloadTask.toDownloadEntity());
                    break;
            }
        }
    }

    public interface DownloadListener {
        void download(DownloadEntity downloadTask);

        void download(List<DownloadEntity> downEntities);

        void pause(DownloadEntity downloadTask);
    }

}
