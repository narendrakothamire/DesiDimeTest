package com.softwares.swamisamarth.desidimetest.custom;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by Narendra Kothamire on 3/22/2016.
 */
public class EndlessRecyclerView extends RecyclerView {
    private boolean hasMore;
    private LinearLayoutManager layoutManager;
    private LoadMoreListener onLoadMoreListener;

    public EndlessRecyclerView(Context context) {
        super(context);
    }

    public EndlessRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EndlessRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean getHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public LoadMoreListener getOnLoadMoreListener() {
        return onLoadMoreListener;
    }

    public void setOnLoadMoreListener(LoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        if (layout instanceof LinearLayoutManager) {
            layoutManager = (LinearLayoutManager) layout;
        } else {
            throw new RuntimeException("Only LinearLayoutManager Supported");
        }
    }



    public interface LoadMoreListener {
        void onLoadMore();


    }
}
