package com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.topics.domain.usecase;

import android.support.annotation.NonNull;
import android.util.LruCache;

import com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.UseCase;
import com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.data.Repository;
import com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.data.ServiceInteractor;
import com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.schedulers.BaseSchedulerProvider;
import com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.topics.domain.Topics;

import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by javierg on 03/07/2017.
 */

public class GetTopics extends UseCase <GetTopics.RequestValues, GetTopics.ResponseValue> {

    private Repository mRepository;
    private BaseSchedulerProvider mSchedulerProvider;

    @NonNull
    private CompositeSubscription mSubscriptions;


    public GetTopics(@NonNull Repository repository, @NonNull BaseSchedulerProvider provider) {
        this.mRepository = checkNotNull(repository, "repository cannot be null");
        this.mSchedulerProvider = checkNotNull(provider, "schedulerProvider cannot be null");


    }

    @Override
    protected void executeUseCase(RequestValues requestValues) {
        mSubscriptions = requestValues.getSubcription();
        LruCache<String, List<Topics>> cache = new LruCache<>(5 * 1024 * 1024); // 5MiB

        final ServiceInteractor interactor = new ServiceInteractor(mRepository.getService(), cache);

        Subscription subscription = interactor.searchUsers()
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .subscribe((List<Topics> listTopics) -> {
                            ResponseValue responseValue = new ResponseValue(listTopics);
                            getUseCaseCallback().onSuccess(responseValue);
                        },
                        (Throwable error) -> {
                            try {
                                getUseCaseCallback().onError();
                            } catch (Throwable t) {
                                throw new IllegalThreadStateException();
                            }

                        },
                        () -> {
                        });

        mSubscriptions.add(subscription);

    }

    public static final class RequestValues implements UseCase.RequestValues {
        private CompositeSubscription mSubscriptions;

        public RequestValues(CompositeSubscription subscriptions) {
            mSubscriptions = subscriptions;
        }

        public CompositeSubscription getSubcription() {return mSubscriptions;}

    }

    public static final class ResponseValue implements UseCase.ResponseValue {

        private final List<Topics> mTopics;

        public ResponseValue(@NonNull List<Topics> topics) {
            mTopics = checkNotNull(topics, "tasks cannot be null!");
        }

        public List<Topics> getTopics() {
            return mTopics;
        }
    }
}
