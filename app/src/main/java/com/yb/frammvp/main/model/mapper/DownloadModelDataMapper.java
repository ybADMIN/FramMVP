package com.yb.frammvp.main.model.mapper;

import com.yb.frammvp.main.model.DownloadMode;
import com.yb.ilibray.data.download.down.DownloadStatus;
import com.yb.ilibray.data.download.down.entity.DownloadEntity;
import com.yb.frammvp.repository.bean.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Created by ericYang on 2017/6/23.
 * Email:eric.yang@huanmedia.com
 * what?
 */

public class DownloadModelDataMapper {
    /**
     * Transform a {@link User} into an {@link DownloadMode}.
     *
     * @param downloadEntity
     * @return {@link DownloadMode}.
     */
    public DownloadMode transform(DownloadEntity downloadEntity) {
        if (downloadEntity == null) {
            throw new IllegalArgumentException("Cannot transform a null value");
        }
        final DownloadMode downloadModel = new DownloadMode();
            downloadModel.setFixName(downloadEntity.getFixName());

        downloadModel.setDownloadStatus(new DownloadStatus(downloadEntity));
        return downloadModel;
    }

    /**
     * Transform a Collection of {@link DownloadEntity} into a Collection of {@link DownloadMode}.
     *
     * @param downloadEntities Objects to be transformed.
     * @return List of {@link DownloadMode}.
     */
    public Collection<DownloadMode> transform(Collection<DownloadEntity> downloadEntities) {
        Collection<DownloadMode> downloadModes;

        if (downloadEntities != null && !downloadEntities.isEmpty()) {
            downloadModes = new ArrayList<>();
            for (DownloadEntity user : downloadEntities) {
                downloadModes.add(transform(user));
            }
        } else {
            downloadModes = Collections.emptyList();
        }

        return downloadModes;
    }
}
