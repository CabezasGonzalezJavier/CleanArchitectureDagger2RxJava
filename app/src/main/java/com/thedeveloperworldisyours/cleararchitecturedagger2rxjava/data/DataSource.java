package com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.data;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by javierg on 19/04/2017.
 */

public interface DataSource {

    Retrofit getService();

    OkHttpClient getOkHttpClient();

}
