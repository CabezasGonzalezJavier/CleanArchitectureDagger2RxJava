package com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.topics;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.R;
import com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.topics.domain.Topics;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TopicsFragment extends Fragment implements TopicsContract.View, TopicsAdapter.MyClickListener {


    @BindView(R.id.topics_fragment_progress)
    ProgressBar mProgressBar;

    @BindView(R.id.topics_fragment_retry_button)
    Button mRetry;

    @BindView(R.id.topics_fragment_relative_layout)
    RelativeLayout mRelativeLayout;

    @BindView(R.id.topics_fragment_recycler_view)
    RecyclerView mRecyclerView;

    List<Topics> mListTopics;

    private TopicsContract.Presenter mPresenter;

    public TopicsFragment() {
        // Required empty public constructor
    }

    public static TopicsFragment newInstance() {
        return new TopicsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.topics_fragment, container, false);
        ButterKnife.bind(this, view);
        mPresenter.subscribe();
        return view;
    }

    @Override
    public void setPresenter(TopicsContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void showTopics(List<Topics> list) {
        mListTopics = list;
        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        TopicsAdapter adapter = new TopicsAdapter(getActivity(), list);
        mRecyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

    }

    @Override
    public void showError() {
        mProgressBar.setVisibility(View.GONE);
        mRetry.setVisibility(View.VISIBLE);
//        Utils.customSnackBar(getActivity().getString(R.string.no_connection), R.color.colorCancel, mRelativeLayout);
        mRetry.setText(getString(R.string.retry));

    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (!active) {
            mRetry.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mPresenter.unSubscribe();
    }

    @Override
    public void onItemClick(int position, View v) {

    }

    @OnClick(R.id.topics_fragment_retry_button)
    public void retry(View view) {
        mProgressBar.setVisibility(View.VISIBLE);
        mRetry.setVisibility(View.GONE);
        mPresenter.fetch();
    }
}