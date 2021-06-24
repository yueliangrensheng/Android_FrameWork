package com.yazao.base.weight

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager
import com.yazao.lib.xlog.Log
import kotlin.math.abs

class AwesomeViewPager : ViewPager {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
    }

    init {

    }

    var lastX = -1f
    var lastY = -1f

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        var x = ev!!.rawX
        var y = ev!!.rawY
        var dealtX = 0f
        var dealtY = 0f


        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                parent.requestDisallowInterceptTouchEvent(true)
            }
            MotionEvent.ACTION_MOVE -> {
                dealtX = (abs(x - lastX) + dealtX)
                dealtY = (abs(y - lastY) + dealtY)

                lastX = x
                lastY = y

                if (dealtX >= dealtY) {
                    parent.requestDisallowInterceptTouchEvent(true)
                    Log.i("--ViewPager -- x > y")
                } else {
                    Log.i("--ViewPager -- x < y")
                    parent.requestDisallowInterceptTouchEvent(false)
                    return false
                }
            }
            MotionEvent.ACTION_UP -> {
            }
        }

        return super.dispatchTouchEvent(ev)
    }

}