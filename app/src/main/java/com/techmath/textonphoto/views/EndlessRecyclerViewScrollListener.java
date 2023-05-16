package com.techmath.textonphoto.views;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {


    RecyclerView.LayoutManager layoutManager;
    private int currentPage = 0;
    private boolean loading = true;
    private int previousTotalItemCount = 0;
    private int visibleThreshold = 5;

    public abstract void onLoadMore(int i, int i2, RecyclerView recyclerView);


    public EndlessRecyclerViewScrollListener(GridLayoutManager gridLayoutManager) {
        this.layoutManager = gridLayoutManager;
        this.visibleThreshold *= gridLayoutManager.getSpanCount();
    }


    public int getLastVisibleItem(int[] iArr) {
        int i = 0;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            if (i2 == 0) {
                i = iArr[i2];
            } else if (iArr[i2] > i) {
                i = iArr[i2];
            }
        }
        return i;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int i, int i2) {
        int i3;
        int itemCount = this.layoutManager.getItemCount();
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            i3 = getLastVisibleItem(((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null));
        } else if (layoutManager instanceof GridLayoutManager) {
            i3 = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
        } else {
            i3 = layoutManager instanceof LinearLayoutManager ? ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition() : 0;
        }
        if (itemCount < this.previousTotalItemCount) {
            this.currentPage = 0;
            this.previousTotalItemCount = itemCount;
            if (itemCount == 0) {
                this.loading = true;
            }
        }
        if (this.loading && itemCount > this.previousTotalItemCount) {
            this.loading = false;
            this.previousTotalItemCount = itemCount;
        }
        if (!this.loading && i3 + this.visibleThreshold > itemCount) {
            this.currentPage++;
            onLoadMore(this.currentPage, itemCount, recyclerView);
            this.loading = true;
        }
    }


}
