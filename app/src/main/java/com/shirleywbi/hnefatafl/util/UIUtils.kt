package com.shirleywbi.hnefatafl.util

import android.view.View

fun getDps(dps: Int, view: View): Int {
    val scale = view.context.resources.displayMetrics.density
    return (dps * scale + 0.5f).toInt()
}