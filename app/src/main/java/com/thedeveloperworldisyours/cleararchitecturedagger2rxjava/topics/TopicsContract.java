package com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.topics;

import com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.BasePresenter;
import com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.BaseView;
import com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.topics.domain.Topics;

import java.util.List;

/**
 * Created by javierg on 03/07/2017.
 */

public class TopicsContract {

    interface Presenter extends BasePresenter {
        void fetch();
    }

    interface View extends BaseView<Presenter> {
        void showTopics(List<Topics> list);

        void showError();

        void setLoadingIndicator(boolean active);

    }
}
