package com.yazao.base.weight;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;


/**
 * 类描述：RecyclerView Item间距
 *
 * <pre>
 *     目前支持 LinearLayoutManager 和 GridLayoutManager
 * </pre>
 * <p>
 * // 一、LinearLayoutManager使用场景：整个边框 left,top,right,bottom --> 30,30,30,60   item间距 --> 30
 *
 * <pre>
 *      //OneSide = true
 *
 *       //1. LinearLayoutManager
 *       LinearLayoutManager layoutManager = new XLinearLayoutManager(this);//Vertical
 *       recyclerView.setLayoutManager(layoutManager);
 *
 *       //2.SpacingItemDecoration 单元素
 *       int lastTopSpanCount = 30;//Item 第一行元素
 *       int lastBottomSpanCount = 60;//Item 最后一行元素
 *
 *       int leftOrRightSpanCount = 30;
 *       int topOrBottomSpanCount = 30;
 *
 *       SpacingItemDecoration spacingItemDecoration = new SpacingItemDecoration(
 *                 leftOrRightSpanCount, topOrBottomSpanCount,
 *                 lastTopSpanCount, lastBottomSpanCount);
 *
 *      //3.Optional（if true, just use the left、top、right ; other,all use）
 *      spacingItemDecoration.setOneSide(true);
 *
 *      recyclerView.addItemDecoration(spacingItemDecoration);
 * </pre>
 *
 * <pre>
 *       //OneSide = false
 *
 *       //1. LinearLayoutManager
 *       LinearLayoutManager layoutManager = new XLinearLayoutManager(this);//Vertical
 *       recyclerView.setLayoutManager(layoutManager);
 *
 *       //2.SpacingItemDecoration 单元素
 *       int lastTopSpanCount = 30;//Item 第一行元素
 *       int lastBottomSpanCount = 60;//Item 最后一行元素
 *
 *       int leftOrRightSpanCount = 30;
 *       int topOrBottomSpanCount = 15;
 *
 *       SpacingItemDecoration spacingItemDecoration = new SpacingItemDecoration(
 *                 leftOrRightSpanCount, topOrBottomSpanCount,
 *                 lastTopSpanCount, lastBottomSpanCount);
 *
 *      //3.Optional（if true, just use the left、top、right; other,all use）
 *      spacingItemDecoration.setOneSide(false);
 *
 *      recyclerView.addItemDecoration(spacingItemDecoration);
 * </pre>
 * <p>
 * <p>
 * // 二、GridLayoutManager使用场景：整个边框 left,top,right,bottom --> 30,30,30,60   item间距 --> 10
 * <p>
 * oneSide true or false 区别在于： item间距的计算
 * <p>
 * //在计算Item的宽度时候， 需要同时 减去 item的 left 和 right
 * //因此在处理边界距离时候，可以把多余的差值设置到 RecyclerView 的 padding
 * <pre>
 *      //OneSide = true
 *
 *       //1. GridLayoutManager
 *       int spanCount = 3;//列数
 *       GridLayoutManager layoutManager = new GridLayoutManager(this,spanCount);
 *       recyclerView.setLayoutManager(layoutManager);
 *
 *       //2.SpacingItemDecoration 多元素
 *
 *       int leftSpanCount = 10;//Item 左间距
 *       int topSpanCount = 10;//Item 上间距
 *       int rightSpanCount = 10;//Item 右间距
 *       int bottomSpanCount = 10;//Item 下间距
 *
 *       int lastTopSpanCount = 30;//Item 第一行元素
 *       int lastLeftSpanCount = 30;//Item 最左边元素
 *       int lastRightSpanCount = 30;//Item 最右边元素
 *       int lastBottomSpanCount = 60;//Item 最后一行元素
 *
 *
 *       SpacingItemDecoration spacingItemDecoration = new SpacingItemDecoration(spanCount,
 *                 leftSpanCount,topSpanCount,rightSpanCount,bottomSpanCount,
 *                 lastTopSpanCount,lastBottomSpanCount,lastLeftSpanCount,lastRightSpanCount);
 *
 *      //3.Optional（if true, just use the left、top; other,all use）
 *      spacingItemDecoration.setOneSide(true);
 *
 *      recyclerView.addItemDecoration(spacingItemDecoration);
 * </pre>
 *
 * <pre>
 *      //OneSide = false
 *
 *       //1. GridLayoutManager
 *       int spanCount = 3;//列数
 *       GridLayoutManager layoutManager = new GridLayoutManager(this,spanCount);
 *       recyclerView.setLayoutManager(layoutManager);
 *
 *       //2.SpacingItemDecoration 多元素
 *
 *       int leftSpanCount = 5;//Item 左间距
 *       int topSpanCount = 5;//Item 上间距
 *       int rightSpanCount = 5;//Item 右间距
 *       int bottomSpanCount = 5;//Item 下间距
 *
 *       int lastTopSpanCount = 30;//Item 第一行元素
 *       int lastLeftSpanCount = 30;//Item 最左边元素
 *       int lastRightSpanCount = 30;//Item 最右边元素
 *       int lastBottomSpanCount = 60;//Item 最后一行元素
 *
 *
 *       SpacingItemDecoration spacingItemDecoration = new SpacingItemDecoration(spanCount,
 *                 leftSpanCount,topSpanCount,rightSpanCount,bottomSpanCount,
 *                 lastTopSpanCount,lastBottomSpanCount,lastLeftSpanCount,lastRightSpanCount);
 *
 *      //3.Optional（if true, just use the left、top; other,all use）
 *      spacingItemDecoration.setOneSide(false);
 *
 *      recyclerView.addItemDecoration(spacingItemDecoration);
 * </pre>
 *
 * @author zhaishaoping
 * @data 15/11/2017 2:57 PM
 */

public class SpacingItemDecoration extends RecyclerView.ItemDecoration {

    private int spanSize = 1;// 每行/列 显示 Item 的数量
    private int leftSpanCount = 0;//Item 左间距
    private int topSpanCount = 0;//Item 上间距 
    private int rightSpanCount = 0;//Item 右间距 
    private int bottomSpanCount = 0;//Item 下间距 
    private int lastTopSpanCount = 0;//Item 第一行元素 
    private int lastLeftSpanCount = 0;//Item 最左边元素 
    private int lastRightSpanCount = 0;//Item 最右边元素 
    private int lastBottomSpanCount = 0;//Item 最后一行元素 
    //头布局相关
    private boolean hasHead = false;//是否有头布局
    int headLeftSpanCount = 0;
    int headRightSpanCount = 0;
    //是否只使用对应一边的值,即 左右、上下 四个方向 只选取 一组中的一个方向.默认使用的是 left 和 top 值
    boolean isOneSide = false;


    /**
     * 每行/列 仅一个元素的 构造方法 (无header)
     */
    public SpacingItemDecoration(int leftOrRightSpanCount, int topOrBottomSpanCount, int lastTopSpanCount, int lastBottomSpanCount) {
        this(RecyclerView.VERTICAL, leftOrRightSpanCount, topOrBottomSpanCount, lastTopSpanCount, lastBottomSpanCount);
    }

    /**
     * 每行/列 仅一个元素的 构造方法 (无header)
     */
    public SpacingItemDecoration(int orientation, int leftOrRightSpanCount, int topOrBottomSpanCount, int lastTopSpanCount, int lastBottomSpanCount) {
        this(false, 0, 0,
                leftOrRightSpanCount, topOrBottomSpanCount, leftOrRightSpanCount, topOrBottomSpanCount,
                lastTopSpanCount, lastBottomSpanCount);
    }

    /**
     * 每行/列 仅一个元素的 构造方法（可判断有无header；左右间距和上下间距 分别相同）
     */
    public SpacingItemDecoration(boolean hasHead, int headLeftSpanCount, int headRightSpanCount,
                                 int leftOrRightSpanCount, int topOrBottomSpanCount, int lastTopSpanCount, int lastBottomSpanCount) {

        this(hasHead, headLeftSpanCount, headRightSpanCount,
                leftOrRightSpanCount, topOrBottomSpanCount, leftOrRightSpanCount, topOrBottomSpanCount,
                lastTopSpanCount, lastBottomSpanCount);
    }

    /**
     * 每行/列 仅一个元素的 构造方法（可判断有无header；分别设置各个方向的间距）
     */
    public SpacingItemDecoration(boolean hasHead, int headLeftSpanCount, int headRightSpanCount,
                                 int leftSpanCount, int topSpanCount, int rightSpanCount, int bottomSpanCount,
                                 int lastTopSpanCount, int lastBottomSpanCount) {
        this(hasHead, headLeftSpanCount, headRightSpanCount,
                1,
                leftSpanCount, topSpanCount, rightSpanCount, bottomSpanCount,
                0, lastTopSpanCount, 0, lastBottomSpanCount);

    }

    /**
     * 每行/列 多个元素的 构造方法
     */
    public SpacingItemDecoration(int spanSize,
                                 int leftSpanCount, int topSpanCount, int rightSpanCount, int bottomSpanCount,
                                 int lastLeftSpanCount, int lastTopSpanCount, int lastRightSpanCount, int lastBottomSpanCount) {
        this(false, 0, 0,
                spanSize,
                leftSpanCount, topSpanCount, rightSpanCount, bottomSpanCount,
                lastLeftSpanCount, lastTopSpanCount, lastRightSpanCount, lastBottomSpanCount);
    }

    /**
     * 每行/列 多个元素的 构造方法
     */
    public SpacingItemDecoration(boolean hasHead, int headLeftSpanCount, int headRightSpanCount,
                                 int spanSize,
                                 int leftSpanCount, int topSpanCount, int rightSpanCount, int bottomSpanCount,
                                 int lastLeftSpanCount, int lastTopSpanCount, int lastRightSpanCount, int lastBottomSpanCount) {
        this.hasHead = hasHead;
        this.headLeftSpanCount = headLeftSpanCount;
        this.headRightSpanCount = headRightSpanCount;
        this.spanSize = spanSize;
        this.leftSpanCount = leftSpanCount;
        this.topSpanCount = topSpanCount;
        this.rightSpanCount = rightSpanCount;
        this.bottomSpanCount = bottomSpanCount;
        this.lastTopSpanCount = lastTopSpanCount;
        this.lastLeftSpanCount = lastLeftSpanCount;
        this.lastRightSpanCount = lastRightSpanCount;
        this.lastBottomSpanCount = lastBottomSpanCount;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (spanSize < 1) {
            spanSize = 1;
        }

        int position = parent.getChildLayoutPosition(view);

//        Log.i("yazao3", "--- getItemOffsets " + " --- spanSize = " + spanSize + ", --- position = " + position);

        if (spanSize > 1) {
            //每行/列 多个元素：spanSize

            if (isOneSide) {
                outRect.left = leftSpanCount;
                outRect.top = topSpanCount;
                outRect.right = 0;
                outRect.bottom = 0;
            } else {
                outRect.left = leftSpanCount;
                outRect.top = topSpanCount;
                outRect.right = rightSpanCount;
                outRect.bottom = bottomSpanCount;
            }

            //在计算Item的宽度时候， 需要同时 减去 item的 left 和 right
            //因此在处理边界距离时候，可以把多余的差值设置到 RecyclerView 的 padding

            if (position % spanSize == 0) {//最左边
                outRect.left = lastLeftSpanCount;
            }

            if (position % spanSize == spanSize - 1) {//最右边
                outRect.right = lastRightSpanCount;
            }

            if (position / spanSize == 0) {//第一行
                outRect.top = lastTopSpanCount;
            }
//            Log.i("yazao3", "--- getItemOffsets " + "position = " + position + " --- 当前行 = " + (position / spanSize) + ", --- 总行数 = " + (parent.getAdapter().getItemCount() - 1) / spanSize);
            // 当前行数：position/ spanSize； 总行数：(parent.getAdapter().getItemCount() - 1 )/ spanSize
            if (position / spanSize == (parent.getAdapter().getItemCount() - 1) / spanSize) {//最后一行
                outRect.bottom = lastBottomSpanCount;
            }

        } else {
            //每行/列 仅一个元素

            //单元素时候，如果 isOneSide = true,辣么 left = leftSpanCount,right = rightSpanCount,top = 0,bottom = bottomSpanCount;
            //          如果 isOneSide = false, 取各自的值
            if (isOneSide) {
                outRect.left = leftSpanCount;
                outRect.right = rightSpanCount;
                outRect.top = topSpanCount;
                outRect.bottom = 0;
            } else {
                outRect.left = leftSpanCount;
                outRect.top = topSpanCount;
                outRect.right = rightSpanCount;
                outRect.bottom = bottomSpanCount;
            }

            // 第一个元素
            if (hasHead) {
                if (position == 0 && hasHead) {//第一个元素 -- head
                    outRect.left = headLeftSpanCount;
                    outRect.right = headRightSpanCount;
                }
            } else {
                if (position == 0) {//第一个元素
                    outRect.top = lastTopSpanCount;
                }
            }

            // 最后一个元素
            if (position == parent.getAdapter().getItemCount() - 1) {//最后一行
                outRect.bottom = lastBottomSpanCount;
            }

        }

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
//        int childCount = parent.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View childView = parent.getChildAt(i);

//            Log.i("position = " + i + ", left = " + childView.getLeft() + ", right = " + childView.getRight() + ", width = " + childView.getMeasuredWidth() + ", height = " + childView.getMeasuredHeight() + ", width = " + childView.getMeasuredWidth());
//        }
    }


    public SpacingItemDecoration setOneSide(boolean oneSide) {
        isOneSide = oneSide;
        return this;
    }
}
