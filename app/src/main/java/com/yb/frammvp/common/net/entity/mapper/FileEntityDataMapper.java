/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yb.frammvp.common.net.entity.mapper;


import com.yb.frammvp.common.net.entity.FileEntity;
import com.yb.frammvp.common.net.entity.UserEntity;
import com.yb.ilibray.data.download.down.entity.DownloadEntity;
import com.yb.frammvp.repository.bean.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Mapper class used to transform {@link UserEntity} (in the data layer) to {@link User} in the
 * domain layer.
 */
public class FileEntityDataMapper {

  public FileEntityDataMapper() {}

  /**
   * Transform a {@link UserEntity} into an {@link User}.
   *
   * @param fileEntity Object to be transformed.
   * @return {@link User} if valid {@link UserEntity} otherwise null.
   */
  public DownloadEntity transform(FileEntity fileEntity) {
    DownloadEntity downloadEntity = null;
    if (fileEntity != null) {
     downloadEntity =new DownloadEntity()
             .setUrl(fileEntity.getUrl())
             .setFixName(fileEntity.getFixName());
    }
    return downloadEntity;
  }

  /**
   * Transform a List of {@link UserEntity} into a Collection of {@link User}.
   *
   * @param userEntityCollection Object Collection to be transformed.
   * @return {@link User} if valid {@link UserEntity} otherwise null.
   */
  public List<DownloadEntity> transform(Collection<FileEntity> userEntityCollection) {
    final List<DownloadEntity> userList = new ArrayList<>();
    for (FileEntity fileEntity : userEntityCollection) {
      final DownloadEntity user = transform(fileEntity);
      if (user != null) {
        userList.add(user);
      }
    }
    return userList;
  }

}
