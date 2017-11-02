package com.yb.frammvp.common.net;


import com.yb.frammvp.common.net.entity.FileEntity;
import com.yb.frammvp.common.net.entity.UserEntity;
import com.yb.ilibray.BuildConfig;
import com.yb.ilibray.data.net.DataResponse;
import com.yb.ilibray.data.net.converter.GsonConverterFactory;
import com.yb.ilibray.data.net.converter.StringConverterFactory;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by ericYang on 2017/5/18.
 * Email:eric.yang@huanmedia.com
 * what?
 */

public interface RemoteApiService {
//    String BASEURL="http://10.0.2.2:8080";
    String BASEURL="http://192.168.29.166:8080";
    @POST("/user/{id}")
    Observable<DataResponse<UserEntity>> getUser(@Path("id") int id);
    @GET("/user/users")
    Observable<DataResponse<List<UserEntity>>> getUsers();
    @GET("/download/list")
    Observable<DataResponse<List<FileEntity>>> getFileList();
    /**
     * Create a new ApiService
     */
  public static class Factory {
        private Factory() {  }

        public static RemoteApiService createService( ) {
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            builder.readTimeout(10, TimeUnit.SECONDS);
            builder.connectTimeout(9, TimeUnit.SECONDS);

            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(interceptor);
            }

//            builder.addInterceptor(new HeaderInterceptor());
            OkHttpClient client = builder.build();
            Retrofit retrofit =
                    new Retrofit.Builder().baseUrl(BASEURL)
                            .client(client)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addConverterFactory(StringConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .build();
            return retrofit.create(RemoteApiService.class);
        }
    }

}
