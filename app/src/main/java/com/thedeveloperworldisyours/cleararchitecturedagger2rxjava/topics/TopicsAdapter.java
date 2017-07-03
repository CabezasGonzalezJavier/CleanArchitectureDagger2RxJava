package com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.topics;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.R;
import com.thedeveloperworldisyours.cleararchitecturedagger2rxjava.topics.domain.Topics;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by javierg on 19/04/2017.
 */

public class TopicsAdapter extends RecyclerView
        .Adapter<TopicsAdapter
        .DataObjectHolder> {

    private Context mContext;
    private List<Topics> mList;
    private static TopicsAdapter.MyClickListener sClickListener;
    // Allows to remember the last item shown on screen
    private int lastPosition = -1;

    static class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener {
        @BindView(R.id.topics_item_name)
        Button mName;

        DataObjectHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mName.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            sClickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(TopicsAdapter.MyClickListener myClickListener) {
        this.sClickListener = myClickListener;
    }

    public TopicsAdapter(Context context, List<Topics> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public TopicsAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.topics_item_list, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(TopicsAdapter.DataObjectHolder holder, int position) {

        holder.mName.setText(mList.get(position).getName());
        // Here you apply the animation when the view is bound
        setAnimation(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    interface MyClickListener {
        void onItemClick(int position, View v);
    }

}
