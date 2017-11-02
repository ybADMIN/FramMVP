package com.yb.frammvp.main;

import android.support.annotation.NonNull;
import android.widget.Toast;

import com.yb.frammvp.repository.RepositoryManager;
import com.yb.frammvp.common.exception.ErrorMessageFactory;
import com.yb.frammvp.main.model.mapper.DownloadModelDataMapper;
import com.yb.frammvp.main.model.DownloadMode;
import com.yb.ilibray.data.download.down.DownLoadManager;
import com.yb.ilibray.data.download.down.entity.DownloadEntity;
import com.yb.frammvp.repository.UserRepository;
import com.yb.ilibray.presenter.Presenter;

import java.util.List;


/**
 * Created by ericYang on 2017/5/18.
 * Email:eric.yang@huanmedia.com
 * what?
 */
public class FileDownPresenter extends Presenter {
    private final UserRepository mRepository;
    private FileDownView mView;
    private DownloadModelDataMapper mModelDataMapper=new DownloadModelDataMapper();
    public FileDownPresenter() {
        this.mRepository= (UserRepository) RepositoryManager.getInstance().getRepositorys(RepositoryManager.USERREPOSITORY);
    }

    public void setView(@NonNull FileDownView view){
        this.mView = view;
    }

    public void getFileLists(){
        addDisposable(mRepository.getFilelist().subscribe(downloadEntities -> {
                    mView.showDownLoadList((List<DownloadMode>) mModelDataMapper.transform(downloadEntities));
                }
               , throwable -> Toast.makeText(mView.context(), ErrorMessageFactory.create(mView.context(),throwable), Toast.LENGTH_SHORT).show()
        ));
    }

    void download(DownloadEntity downloadEntity){
        DownLoadManager.getInstance(mView.context()).addDownLoad(downloadEntity);
    }
    void pauseDownload(DownloadEntity downloadTask){
        DownLoadManager.getInstance(mView.context()).pause(downloadTask);
    }
    @Override
    public void destroy() {
        dispose();
    }

}
