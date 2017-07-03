package com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.topics;

import android.support.annotation.NonNull;

import com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.UseCase;
import com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.UseCaseHandler;
import com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.topics.domain.Topics;
import com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.topics.domain.usecase.GetTopics;

import java.util.List;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by javierg on 03/07/2017.
 */

public class TopicsPresenter implements TopicsContract.Presenter {

    private TopicsContract.View mView;
    private final GetTopics mGetTopics;

    private final UseCaseHandler mUseCaseHandler;

    @NonNull
    private CompositeSubscription mSubscriptions;

    public TopicsPresenter(TopicsContract.View mView, GetTopics mGetTopics, UseCaseHandler mUseCaseHandler) {
        this.mView = mView;
        this.mGetTopics = mGetTopics;
        this.mUseCaseHandler = mUseCaseHandler;

        mSubscriptions = new CompositeSubscription();

        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
        fetch();
    }

    @Override
    public void unSubscribe() {
        mSubscriptions.clear();
    }

    @Override
    public void fetch() {
        GetTopics.RequestValues requestValues = new GetTopics.RequestValues(mSubscriptions);

        mUseCaseHandler.execute(mGetTopics, requestValues, new UseCase.UseCaseCallback<GetTopics.ResponseValue>() {
            @Override
            public void onSuccess(GetTopics.ResponseValue response) {
                List<Topics> topics = response.getTopics();

                mView.setLoadingIndicator(false);
                mView.showTopics(topics);
            }

            @Override
            public void onError() {
                mView.showError();
            }
        });

    }

}
