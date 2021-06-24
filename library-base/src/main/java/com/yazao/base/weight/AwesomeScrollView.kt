package com.yazao.base.weight

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.ScrollView
import com.yazao.lib.xlog.Log

class AwesomeScrollView : ScrollView {

    constructor(context: Context?) : super(context) {}

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}

    init {

    }

    // 滑动距离及坐标
    private var xDistance = 0f
    private var yDistance = 0f
    private var xLast = 0f
    private var yLast = 0f
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                run {
                    yDistance = 0f
                    xDistance = yDistance
                }
                xLast = ev.rawX
                yLast = ev.rawY
            }
            MotionEvent.ACTION_MOVE -> {
                val curX = ev.rawX
                val curY = ev.rawY
                xDistance += Math.abs(curX - xLast)
                yDistance += Math.abs(curY - yLast)
                xLast = curX
                yLast = curY

                if (xDistance <= yDistance) {
                    parent.requestDisallowInterceptTouchEvent(true)
//                    Log.i("--ScrollView -- x < y")
                } else {
                    parent.requestDisallowInterceptTouchEvent(false)
//                    Log.i("--ScrollView -- x > y")
                    return false
                }
            }
        }
        return super.onInterceptTouchEvent(ev)
    }
}