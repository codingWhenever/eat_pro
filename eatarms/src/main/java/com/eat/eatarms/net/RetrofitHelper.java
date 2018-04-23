package com.eat.eatarms.net;

import android.os.Build;
import android.support.annotation.RequiresApi;

import com.eat.eatarms.BuildConfig;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author leo
 * @date
 */
public abstract class RetrofitHelper {
    /**
     * The M retrofit.
     */
    public Retrofit mRetrofit;

    /**
     * 默认超时时长30s
     */
    private static final int DEFAULT_TIME_OUT = 30;

    /**
     * Instantiates a new Retrofit helper.
     */
    public RetrofitHelper() {
        if (null == mRetrofit) {
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            builder.readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                    .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(false);
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor)
                    .addInterceptor(new BasicParamsInterceptor.Builder().addHeaderParamsMap(getBaseParams()).build());
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(HttpURLAddress.getInstance().getCurrentURL())
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
    }

    public <T> void toSubscribe(Observable<T> observable, Observer<T> observer) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .subscribe(observer);

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static <T> T getPresenter(Class<T> cls) {
        T instance = null;
        try {
            instance = cls.newInstance();
            if (instance == null) {
                return null;
            }
            return instance;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 公共头部参数
     *
     * @return
     */
    public abstract Map<String, String> getBaseParams();


}
