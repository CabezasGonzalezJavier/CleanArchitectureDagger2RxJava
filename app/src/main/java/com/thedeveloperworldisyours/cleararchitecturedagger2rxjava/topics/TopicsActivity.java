package com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.topics;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.R;
import com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.TopicsApplication;
import com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.UseCaseHandler;
import com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.data.Repository;
import com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.schedulers.BaseSchedulerProvider;
import com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.topics.domain.usecase.GetTopics;

import javax.inject.Inject;

import static com.google.common.base.Preconditions.checkNotNull;

public class TopicsActivity extends AppCompatActivity {
    @Inject
    Repository mRepository;

    @Inject
    BaseSchedulerProvider mSchedulerProvider;

    @Inject
    UseCaseHandler mProvideUseCaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.topics_activity);
        initializeDagger();
        initFragment();
    }

    private void initializeDagger() {
        TopicsApplication app = (TopicsApplication) getApplication();
        app.getAppComponent().inject(this);
    }

    private void initFragment () {
        TopicsFragment topicsFragment = (TopicsFragment) getSupportFragmentManager().
                findFragmentById(R.id.topics_activity_contentFrame);
        if (topicsFragment == null) {
            topicsFragment = topicsFragment.newInstance();
            addFragmentToActivity(getSupportFragmentManager(),
                    topicsFragment, R.id.topics_activity_contentFrame);
        }
        new TopicsPresenter(topicsFragment, new GetTopics(mRepository, mSchedulerProvider), mProvideUseCaseHandler);

    }


    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }
}
