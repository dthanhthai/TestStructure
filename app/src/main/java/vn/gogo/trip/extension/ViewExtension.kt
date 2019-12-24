package vn.gogo.trip.extension

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet

inline fun Context.getStyledAttributes(set: AttributeSet?,
                                       attrs: IntArray,
                                       defStyleAttr: Int = 0,
                                       defStyleRes: Int = 0,
                                       func: TypedArray.() -> Unit) {
    if (set == null) {
        return
    }

    val typedArray: TypedArray = obtainStyledAttributes(set, attrs, defStyleAttr, defStyleRes)
    try {
        typedArray.func()
    } finally {
        typedArray.recycle()
    }
}