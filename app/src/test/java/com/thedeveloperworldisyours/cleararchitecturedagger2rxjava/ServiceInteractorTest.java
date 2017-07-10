package com.thedeveloperworldisyours.cleararchitecturedagger2rxjava;

import android.util.LruCache;

import com.google.gson.Gson;
import com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.data.ServiceInteractor;
import com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.topics.domain.Topics;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.observers.TestSubscriber;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by javierg on 10/07/2017.
 */

public class ServiceInteractorTest {
    @Mock
    private LruCache<String, List<Topics>> mCache;

    @Before
    public void setup(){

        MockitoAnnotations.initMocks(this);
        when(mCache.get(anyString())).thenReturn(null);
    }

    @Test
    public void mockService() {

        Topics topics = new Topics(1, "football");
        List<Topics> result = new ArrayList();
        result.add(topics);



        MockWebServer mockService = new MockWebServer();
        mockService.enqueue(new MockResponse().setBody(new Gson().toJson(result)));

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(mockService.url("dfdf/"))
                .build();

        TestSubscriber<List<Topics>> subscriber = new TestSubscriber<>();
        ServiceInteractor serviceInteractor = new ServiceInteractor(retrofit, mCache);
        serviceInteractor.searchUsers().subscribe(subscriber);



        subscriber.assertNoErrors();
        subscriber.assertCompleted();
    }

    @Test
    public void callServiceTest() {

        Topics topics = new Topics(1, "Discern The Beach");
        Topics topicsTwo = new Topics(2, "Discern The Football Player");
        List<Topics> result = new ArrayList();
        result.add(topics);
        result.add(topicsTwo);



        MockWebServer mockWebServer = new MockWebServer();
        mockWebServer.enqueue(new MockResponse().setBody(new Gson().toJson(result)));

        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(mockWebServer.url("https://guessthebeach.herokuapp.com/api/"))
                .build();

        TestSubscriber<List<Topics>> subscriber = new TestSubscriber<>();
        ServiceInteractor serviceInteractor = new ServiceInteractor(retrofit, mCache);
        serviceInteractor.searchUsers().subscribe(subscriber);



        subscriber.assertNoErrors();
        subscriber.assertCompleted();
    }


}
