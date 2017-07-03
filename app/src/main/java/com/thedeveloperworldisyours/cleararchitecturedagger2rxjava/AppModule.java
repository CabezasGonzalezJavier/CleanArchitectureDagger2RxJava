package com.thedeveloperworldisyours.cleararchitecturedagger2rxjava;

import com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.data.Repository;
import com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.schedulers.BaseSchedulerProvider;
import com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.schedulers.SchedulerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by javierg on 03/07/2017.
 */
@Module
public class AppModule {
    TopicsApplication mTopicsAplication;

    public AppModule(TopicsApplication topicsAplication) {
        topicsAplication = topicsAplication;
    }

    @Singleton
    @Provides
    Repository provideRepository() {
        return new Repository();
    }

    @Singleton
    @Provides
    BaseSchedulerProvider provideSchedulerProvider() {
        return new SchedulerProvider();
    }

    @Singleton
    @Provides
    UseCaseHandler provideUseCaseHandler() {
        return new UseCaseHandler(new UseCaseThreadPoolScheduler());
    }

}
