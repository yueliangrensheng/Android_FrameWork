package com.yazao.base.weight

import android.graphics.Canvas
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewItemDecoration() : RecyclerView.ItemDecoration() {


    //LinearLayoutManager
    var linearLeftSpacing = 0
    var linearRightSpacing = 0
    var linearTopSpacing = 0
    var linearBottomSpacing = 0
    var linearFirstLeftSpacing = 0
    var linearLastRightSpacing = 0
    var linearFirstTopSpacing = 0
    var linearLastBottomSpacing = 0
    fun setLinearLayoutManagerSpacing(
        linearLeftSpacing: Int,
        linearRightSpacing: Int,
        linearFirstLeftSpacing: Int,
        linearLastRightSpacing: Int,
        linearTopSpacing: Int,
        linearBottomSpacing: Int,
        linearFirstTopSpacing: Int,
        linearLastBottomSpacing: Int
    ): RecyclerViewItemDecoration {
        this.linearLeftSpacing = linearLeftSpacing
        this.linearRightSpacing = linearRightSpacing
        this.linearTopSpacing = linearTopSpacing
        this.linearBottomSpacing = linearBottomSpacing
        this.linearFirstLeftSpacing = linearFirstLeftSpacing
        this.linearLastRightSpacing = linearLastRightSpacing
        this.linearFirstTopSpacing = linearFirstTopSpacing
        this.linearLastBottomSpacing = linearLastBottomSpacing
        return this
    }

    //GridLayoutManager
    //StaggeredGridLayoutManager

    private var layoutManager: RecyclerView.LayoutManager? = null
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        val count = state.itemCount
        layoutManager = parent.layoutManager

        when (layoutManager) {
            is LinearLayoutManager -> {

                val orientation = (layoutManager as LinearLayoutManager).orientation
                when (orientation) {
                    LinearLayoutManager.HORIZONTAL -> {

                        if (position == 0) {
                            //第一个item
                            outRect.left = linearFirstLeftSpacing
                            outRect.right = linearRightSpacing
                        } else if (position == count - 1) {
                            //最后一个item
                            outRect.left = linearLeftSpacing
                            outRect.right = linearLastRightSpacing
                        } else {
                            //中间item
                            outRect.left = linearLeftSpacing
                            outRect.right = linearRightSpacing
                        }

                    }

                    LinearLayoutManager.VERTICAL -> {
                        outRect.left = 0
                        outRect.right = 0
                        outRect.top = 0
                        outRect.bottom = 0
                    }
                }

            }

            is GridLayoutManager -> {
                outRect.left = 0
                outRect.right = 0
                outRect.top = 0
                outRect.bottom = 0
            }

            else -> {
                outRect.left = 0
                outRect.right = 0
                outRect.top = 0
                outRect.bottom = 0
            }
        }


    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
    }


}