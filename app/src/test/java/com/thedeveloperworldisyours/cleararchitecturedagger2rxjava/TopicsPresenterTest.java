package com.thedeveloperworldisyours.cleararchitecturedagger2rxjava;

import com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.data.Repository;
import com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.schedulers.BaseSchedulerProvider;
import com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.topics.TopicsContract;
import com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.topics.TopicsPresenter;
import com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.topics.domain.Topics;
import com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.topics.domain.usecase.GetTopics;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by javierg on 10/07/2017.
 */

public class TopicsPresenterTest {

    @Mock
    BaseSchedulerProvider mSchedulerProvider;

    @Mock
    UseCaseHandler mProvideUseCaseHandler;

    @Mock
    Repository mRepository;

    @Mock
    private TopicsContract.View mView;

    private TopicsContract.Presenter mPresenter;

    private GetTopics mGetTopics;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mGetTopics = new GetTopics(mRepository, mSchedulerProvider);
        mPresenter = new TopicsPresenter(mView, mGetTopics, mProvideUseCaseHandler);
    }


    @Test
    public void loadAllTopics() {
//        mPresenter.fetch();
        Topics topics = new Topics(1, "Discern The Beach");
        Topics topicsTwo = new Topics(2, "Discern The Football Player");
        List<Topics> result = new ArrayList();
        result.add(topics);
        result.add(topicsTwo);



//        when(mRepository.getService()).thenReturn(Observable.just(result));

    }
}
