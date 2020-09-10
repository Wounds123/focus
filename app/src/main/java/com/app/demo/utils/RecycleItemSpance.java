package com.app.demo.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * @anthor : 大海
 * 每天进步一点点
 * @time :   2019/7/3
 * @description : recycleView 的gridview间距
 */


public class RecycleItemSpance extends RecyclerView.ItemDecoration {
    private int spanCount; //列数
    private int spacing_horizental; //间隔
    private int spacing_vertical; //间隔
    private boolean includeEdge; //是否包含边缘


    public RecycleItemSpance(int spanCount, int spacing_horizental, int spacing_vertical, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing_horizental = spacing_horizental;
        this.spacing_vertical = spacing_vertical;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //这里是关键，需要根据你有几列来判断
        int position = parent.getChildAdapterPosition(view); // item position
        int column = position % spanCount; // item column

        if (includeEdge) {
            outRect.left = spacing_horizental - column * spacing_horizental / spanCount; // spacing - column * ((1f / spanCount) * spacing)
            outRect.right = (column + 1) * spacing_horizental / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

            if (position < spanCount) { // top edge
                outRect.top = spacing_vertical;
            }
            outRect.bottom = spacing_vertical; // item bottom
        } else {
            outRect.left = column * spacing_horizental / spanCount; // column * ((1f / spanCount) * spacing)
            outRect.right = spacing_horizental - (column + 1) * spacing_horizental / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
            if (position >= spanCount) {
                outRect.top = spacing_horizental; // item top
            }
        }
    }


}
