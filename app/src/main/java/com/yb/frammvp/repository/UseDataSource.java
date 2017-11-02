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
package com.yb.frammvp.repository;


import com.yb.frammvp.repository.bean.User;
import com.yb.ilibray.data.download.down.entity.DownloadEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * Interface that represents a UserRepository for getting {@link User} related data.
 */
public interface UseDataSource extends DataSource{
    /**
     * Get an {@link Observable} which will emit a List of {@link User}.
     */
    Observable users();

    /**
     * Get an {@link Observable} which will emit a {@link User}.
     *
     * @param userId The user id used to retrieve user data.
     */
    Observable<User> user(final int userId);
    Observable<List<DownloadEntity>> getFilelist();

}