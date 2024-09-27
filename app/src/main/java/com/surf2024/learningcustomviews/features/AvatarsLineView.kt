package com.surf2024.learningcustomviews.features

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition

class AvatarsLineView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var imageBitmaps: List<Bitmap?> = emptyList()

    fun setImages(urls: List<String>) {
        imageBitmaps = mutableListOf()
        for (url in urls) {
            Glide.with(context)
                .asBitmap()
                .load(url)
                .transform(CircleCrop())
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        (imageBitmaps as MutableList).add(resource)
                        invalidate()
                    }
                })
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val count = imageBitmaps.size
        val centerX = imageRadius / 2 + imagesMargin
        val centerY = imageRadius / 2 + imagesMargin

        for (i in 0 until minOf(count, 4)) {
            drawCircleImage(canvas, imageBitmaps[i], centerX, centerY, i)
        }

        if (count > 4) {
            drawOverlayCircle(canvas, centerX, centerY, count - 4)
        }
    }

    private fun drawCircleImage(canvas: Canvas, bitmap: Bitmap?, centerX: Float, centerY: Float, index: Int) {
        if (bitmap != null) {
            val left = centerX - imageRadius + index * imagesMargin
            val top = centerY - imageRadius
            val right = centerX + imageRadius + index * imagesMargin
            val bottom = centerY + imageRadius

            canvas.drawBitmap(bitmap, null, RectF(left, top, right, bottom), paint)
        }
    }

    private fun drawOverlayCircle(canvas: Canvas, centerX: Float, centerY: Float, remaining: Int, index: Int = 4) {
        paint.color = ContextCompat.getColor(context, android.R.color.darker_gray)
        val left = centerX - imageRadius + index * imagesMargin
        val top = centerY - imageRadius
        val right = centerX + imageRadius + index * imagesMargin
        val bottom = centerY + imageRadius

        canvas.drawOval(RectF(left, top, right, bottom), paint)

        paint.color = ContextCompat.getColor(context, android.R.color.white)
        paint.textSize = textSize
        val text = "+$remaining"
        val textWidth = paint.measureText(text)
        val textX = centerX - textWidth / 2 + index * imagesMargin
        val textY = centerY + imageRadius / 6

        canvas.drawText(text, textX, textY, paint)
    }

    companion object {
        const val imageRadius = 200f
        const val imagesMargin = 150f
        const val textSize = 100f
    }

}
