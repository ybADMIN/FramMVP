package com.yb.frammvp.main;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yb.frammvp.R;
import com.yb.frammvp.common.BaseFragment;
import com.yb.frammvp.main.adapter.DownloadAdapter;
import com.yb.frammvp.main.model.DownloadMode;
import com.yb.ilibray.data.download.down.DownLoadRxObserver;
import com.yb.ilibray.data.download.down.DownloadStatus;
import com.yb.ilibray.data.download.down.entity.DownloadEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MultitaskDownloadListener} interface
 * to handle interaction events.
 * Use the {@link MultitaskDownload#newInstance} factory method to
 * create an instance of this fragment.
 */

public class MultitaskDownload extends BaseFragment implements FileDownView, DownloadAdapter.DownloadListener {
    FileDownPresenter mFileDownPresenter;
    @BindView(R.id.rv_download)
    RecyclerView mRvDownload;
    @BindView(R.id.downloadAll)
    Button downloadAll;
    private MultitaskDownloadListener mListener;
    private Disposable downLoadScript;
    private int timeLoadings;
    private RxPermissions rxPermissions;
    private DownloadAdapter adapter;
    private Unbinder mUnBind;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof MultitaskDownloadListener) {
            mListener = (MultitaskDownloadListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement MultitaskDownload");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_multitask_download, container, false);
        mUnBind=  ButterKnife.bind(this, view);
        rxPermissions = new RxPermissions(getActivity());
        initView(view);
        setPresenter();
        mFileDownPresenter.getFileLists();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        downLoadScript.dispose();
    }


    private void setPresenter() {
        mFileDownPresenter = new FileDownPresenter();
        mFileDownPresenter.setView(this);
    }

    public MultitaskDownload() {
    }

    public static MultitaskDownload newInstance() {
        MultitaskDownload fragment = new MultitaskDownload();
        return fragment;
    }

    String path = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495790134697&di=9543515bb4c47f3c5a463fe41e6d876d&imgtype=0&src=http%3A%2F%2Fimg2.manshijian.com%2Fupload%2Fmember%2Fimage%2F1417%2F6aec1cdeeb9906bd921fd56d4c5edebf.jpg";
    String path2 = "http://10.0.2.2:8080/download/ssr-android.apk";

    private void initView(View view) {
        downloadAll.setOnClickListener(v -> {
            for (int i = 0; i < adapter.getDownloadModes().size(); i++) {
                DownloadMode downloadMode = adapter.getDownloadModes().get(i);
                Logger.i("下载任务名称",downloadMode.getDownloadStatus().getUrl());
                mFileDownPresenter.download(adapter.getDownloadModes().get(i).toDownloadEntity());
            }
        });
        adapter =new DownloadAdapter();
        mRvDownload.setAdapter(adapter);
        mRvDownload.setItemAnimator(null);
        adapter.setDownloadListener(this);
        downLoadScript = DownLoadRxObserver.getDefault()
                .register(DownloadStatus.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread()).subscribe(this::upDownloadUI,
                        throwable -> Toast.makeText(getContext(), throwable.getMessage(),Toast.LENGTH_SHORT).show());
    }

    private void upDownloadUI(DownloadStatus downloadStatus) {
        for (int i = 0; i < adapter.getDownloadModes().size(); i++) {
            DownloadMode oldDownloadStatus = adapter.getDownloadModes().get(i);
            if (oldDownloadStatus.getDownloadStatus().getUrl().equals(downloadStatus.getUrl())){
                oldDownloadStatus.setDownloadStatus(downloadStatus);
                adapter.notifyItemChanged(i);
            }
        }
    }


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.dashboradAction(uri);
        }
    }

    @Override
    public Context context() {
        return getContext();
    }

    @Override
    public void showDownLoadList(List<DownloadMode> downloadTasks) {
        adapter.setDownloadModes(downloadTasks);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void download(DownloadEntity downloadTask) {
        mFileDownPresenter.download(downloadTask);
    }

    @Override
    public void download(List<DownloadEntity> downEntities) {
    }

    @Override
    public void pause(DownloadEntity downloadTask) {
        mFileDownPresenter.pauseDownload(downloadTask);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnBind.unbind();
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface MultitaskDownloadListener {
        void dashboradAction(Uri uri);
    }
}
