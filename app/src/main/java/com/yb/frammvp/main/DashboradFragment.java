package com.yb.frammvp.main;

import android.Manifest;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.yb.frammvp.R;
import com.yb.frammvp.common.BaseFragment;
import com.yb.frammvp.main.adapter.DownloadAdapter;
import com.yb.ilibray.data.download.down.DownLoadManager;
import com.yb.ilibray.data.download.down.DownLoadRxObserver;
import com.yb.ilibray.data.download.down.DownloadStatus;
import com.yb.ilibray.data.download.down.DownloadTask;
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
 * {@link DashboradListener} interface
 * to handle interaction events.
 * Use the {@link DashboradFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class DashboradFragment extends BaseFragment implements  DownloadAdapter.DownloadListener {
    FileDownPresenter mFileDownPresenter;
    @BindView(R.id.filename)
    TextView mFilename;
    @BindView(R.id.btn_download)
    Button mBtnDownload;
    @BindView(R.id.button)
    Button mButton;
    @BindView(R.id.progressBar)
    ProgressBar mProgressBar;
    @BindView(R.id.hint_text)
    TextView mHintText;
    @BindView(R.id.downloadContent)
    TextView mDownloadContent;
    @BindView(R.id.tv_percent)
    TextView mTvPercent;
    private DashboradListener mListener;
    private Disposable downLoadScript;
    private int timeLoadings;
    private RxPermissions rxPermissions;
    private Unbinder mButterBind;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DashboradListener) {
            mListener = (DashboradListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement HomeFListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashborad, container, false);
       mButterBind = ButterKnife.bind(this, view);
        rxPermissions = new RxPermissions(getActivity());
        initView(view);
        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        downLoadScript.dispose();
    }


    public DashboradFragment() {
    }

    public static DashboradFragment newInstance() {
        DashboradFragment fragment = new DashboradFragment();
        return fragment;
    }

    String path = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1495790134697&di=9543515bb4c47f3c5a463fe41e6d876d&imgtype=0&src=http%3A%2F%2Fimg2.manshijian.com%2Fupload%2Fmember%2Fimage%2F1417%2F6aec1cdeeb9906bd921fd56d4c5edebf.jpg";
    String path2 = "http://10.0.2.2:8080/download/ssr-android.apk";

    private void initView(View view) {
        mDownloadContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        mBtnDownload.setText("下载");
        mFilename.setText("ssr-android.apk");
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonPressed(Uri.parse("goto://Multitask"));
            }
        });
        mBtnDownload.setOnClickListener(v -> {
            rxPermissions
                    .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    .subscribe(granted -> {

                        if (granted) { // Always true pre-M
                            String url = "https://qd.myapp.com/myapp/qqteam/AndroidQQ/mobileqq_android.apk";
                            DownloadEntity downloadEntity = new DownloadEntity().setUrl(url).setMethod("GET");
                            DownloadStatus status= (DownloadStatus) v.getTag();
                            if (status==null) {
                                DownLoadManager.getInstance(getContext()).addDownLoad(downloadEntity);
                                return;
                            }
                            if (status.getState()==DownloadTask.PAUSE||status.getState()==DownloadTask.DEFAULT||status.getState()==DownloadTask.ERROR||status.getState()==DownloadTask.FAIL){
                                DownLoadManager.getInstance(getContext()).addDownLoad(downloadEntity);
                            }else if (status.getState()==DownloadTask.START||status.getState()==DownloadTask.PREPARE|| status.getState()==DownloadTask.LOADING){
                                DownLoadManager.getInstance(getContext()).pause(downloadEntity);
                            }
                        } else {
                            // Oups permission denied
                            Toast.makeText(getContext(), "不允许访问存储设备", Toast.LENGTH_SHORT).show();
                        }
                    });
//                            String url = "http://192.168.29.168:8080/download/file?filename=ssr-android.apk";
//                                  String url = "http://192.168.29.168:8080/download/ssr-android.apk";

        });
        downLoadScript = DownLoadRxObserver.getDefault()
                .register(DownloadStatus.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread()).subscribe(this::upDownloadUI,
                        throwable -> mDownloadContent.append("\n"+throwable.getMessage()));
//        mImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DownloadEntity downEntity = new DownloadEntity.Bulide()
//                        .url(path2)
//                        .method("GET")
//                        .bulide();
//                DownLoadManager.getInstance(getContext()).addDownLoad(downEntity);
//            }
//        });
//        mImageView.setOnClickListener(v -> {
//            Map<String,ProgressListener> map = new HashMap<>();
//            for (int i = 0; i < downs.length; i++) {
//               map.put(downs[i],new ProgressListener() {
//                   @Override
//                   public void progressIndeterminate() {
//                       Toast.makeText(getActivity(), "下载失败", Toast.LENGTH_SHORT).show();
//                   }
//
//                   @Override
//                   public void onProgress(long contentLength, long downloadLength, boolean isDone) {
//                       mProgressBar.setProgress((int) ((downloadLength/contentLength)*100));
//                   }
//               });
//            }
//            DownManager.getInstance(getActivity()).downLoad(map).subscribe(file -> {
//                Toast.makeText(getActivity(), "下载成功:"+file.exists(), Toast.LENGTH_SHORT).show();
//            },Throwable::printStackTrace);
//        });
//        GlideUtils.getInstance().loadFragmentBlurBitmap(this, path, mImageView, 5);
    }

    private void upDownloadUI(DownloadStatus status) {
        mBtnDownload.setTag(status);
        mBtnDownload.setEnabled(true);
        switch (status.getState()) {
            case DownloadTask.DEFAULT:
                contentSet(status, "【DEFAULT】");
                break;
            case DownloadTask.DELETE:
                contentSet(status, "【DELETE】");
                mBtnDownload.setEnabled(false);
                mBtnDownload.setText("已删除");
                break;
            case DownloadTask.ERROR:
                contentSet(status, "【ERROR】");
                mBtnDownload.setText("重试");
                break;
            case DownloadTask.FAIL:
                mBtnDownload.setText("重试");
                contentSet(status, "【FAIL】");
                break;
            case DownloadTask.FINISH:
                mBtnDownload.setEnabled(false);
                mBtnDownload.setText("完成");
                mProgressBar.setProgress((int) status.getPercentNumber());
                mHintText.setText(String.format("%s/%s", status.getFormatDownloadSize(),status.getFormatTotalSize()));
                mTvPercent.setText(status.getPercent());
                contentSet(status, "【FINISH】");
                mButton.setText((++timeLoadings)+"速度:0b/s");
                break;
            case DownloadTask.LOADING:
                mButton.setText((++timeLoadings)+"  速度:"+status.getDownloadSpeed());
                mProgressBar.setProgress((int) status.getPercentNumber());
                mHintText.setText(String.format("%s/%s", status.getFormatDownloadSize(),status.getFormatTotalSize()));
                mTvPercent.setText(status.getPercent());
//                contentSet(status, "【LOADING】");
                break;
            case DownloadTask.PAUSE:
                mButton.setText((++timeLoadings)+"速度:0b/s");
                mBtnDownload.setText("下载");
                contentSet(status, "【PAUSE】");
                break;
            case DownloadTask.PREPARE:
                mBtnDownload.setEnabled(false);
                mHintText.setText("准备中...");
                contentSet(status, "【PREPARE】");
                break;
            case DownloadTask.START:
                mBtnDownload.setText("暂停");
                contentSet(status, "【START】");
                break;
        }
    }

    private void contentSet(DownloadStatus downloadStatus, String aDefault) {
        mDownloadContent.append(aDefault);
        mDownloadContent.append(downloadStatus.getDescribe() == null ? "" : downloadStatus.getDescribe());
        mDownloadContent.append("\n");

    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.dashboradAction(uri);
        }
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
        mButterBind.unbind();
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
    public interface DashboradListener {
        void dashboradAction(Uri uri);
    }
}
