package com.thedeveloperworldisyours.cleararchitecturedagger2rxjava;

import com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.topics.TopicsActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by javierg on 03/07/2017.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(TopicsActivity themeActivity);

}
