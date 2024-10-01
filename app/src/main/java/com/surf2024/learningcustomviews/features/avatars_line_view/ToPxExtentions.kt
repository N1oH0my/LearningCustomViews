package com.surf2024.learningcustomviews.features.avatars_line_view

import android.content.Context

fun Int.toPx(context: Context): Int {
    return (this * context.resources.displayMetrics.density).toInt()
}

fun Float.dpToPx(context: Context): Float {
    return this * context.resources.displayMetrics.density
}

fun Float.spToPx(context: Context): Float {
    return this * context.resources.displayMetrics.scaledDensity
}