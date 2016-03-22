package com.softwares.swamisamarth.desidimetest.custom;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by Narendra Kothamire on 3/21/2016.
 */
public class PaginationRecyclerView extends RecyclerView {
    private int visibleItemCount;
    private int totalItemCount;
    private int firstVisibleItem;
    private boolean isMoreLoading;
    private int visibleThreshold = 1;
    private LoadMoreListener onLoadMoreListener;

    public PaginationRecyclerView(Context context) {
        super(context);
    }

    public PaginationRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PaginationRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setOnLoadMoreListener(LoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public boolean isMoreLoading() {
        return isMoreLoading;
    }

    public void setIsMoreLoading(boolean isMoreLoading) {
        this.isMoreLoading = isMoreLoading;
    }

    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        visibleItemCount = getChildCount();
        totalItemCount = getAdapter().getItemCount();
        firstVisibleItem = ((LinearLayoutManager)getLayoutManager()).findFirstVisibleItemPosition();
        if (!isMoreLoading && (totalItemCount - visibleItemCount)<= (firstVisibleItem + visibleThreshold)) {
            if (onLoadMoreListener != null) {
                onLoadMoreListener.onLoadMore();
            }
            isMoreLoading = true;
        }
    }


    public interface LoadMoreListener{
        void onLoadMore();
    }
}
