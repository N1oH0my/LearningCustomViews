package com.surf2024.learningcustomviews.features.avatars_line_view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import com.surf2024.learningcustomviews.R

class AvatarsLineView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private var imageUrls: List<String> = emptyList()

    private var maxImages: Int = 4
    private var avatarRadius: Float = 20f
    private var avatarSize: Float = 200f
    private var avatarsMargin: Float = 100f
    private var textSize: Float = 50f

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.AvatarsLineView, 0, 0).apply {
            try {
                maxImages = getInteger(R.styleable.AvatarsLineView_max_avatars, maxImages)
                avatarRadius = getDimension(R.styleable.AvatarsLineView_avatars_radius, avatarRadius)
                avatarSize = getDimension(R.styleable.AvatarsLineView_avatar_size, avatarSize)
                avatarsMargin =
                    getDimension(R.styleable.AvatarsLineView_avatars_margin, avatarsMargin)
                textSize = getDimension(R.styleable.AvatarsLineView_text_size, textSize)
            } finally {
                recycle()
            }
        }
    }

    fun setImages(urls: List<String>) {
        imageUrls = urls
        removeAllViews()

        val displayedImages = minOf(urls.size, maxImages)

        for (i in 0 until displayedImages) {
            val avatarView = AvatarView(context).apply {
                layoutParams = LayoutParams(avatarSize.toInt(), avatarSize.toInt())
                avatarRadius = this@AvatarsLineView.avatarRadius
                setImage(urls[i])
            }
            addView(avatarView)
        }

        if (urls.size > maxImages) {
            val remainingCount = urls.size - maxImages
            val greyAvatarView = AvatarView(context).apply {
                layoutParams = LayoutParams(avatarSize.toInt(), avatarSize.toInt())
                avatarRadius = this@AvatarsLineView.avatarRadius
                val textView = TextView(context).apply {
                    text = "+$remainingCount"
                    setTextColor(Color.WHITE)
                    textSize =
                        this@AvatarsLineView.textSize / resources.displayMetrics.scaledDensity
                    gravity = Gravity.CENTER
                }
                addView(textView)
            }
            addView(greyAvatarView)
        }

        requestLayout()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)

        val totalWidth =
            (minOf(imageUrls.size, maxImages + 1) * (avatarSize + avatarsMargin)).toInt()
        val totalHeight = avatarSize.toInt()

        val measuredWidth = if (widthMode == MeasureSpec.EXACTLY) widthSize else totalWidth
        setMeasuredDimension(measuredWidth, totalHeight)

        for (i in 0 until childCount) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec)
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val centerX = (avatarSize / 2).toInt()
        val centerY = measuredHeight / 2

        for (i in 0 until childCount) {
            val child = getChildAt(i) as AvatarView
            val offsetX = (i * avatarsMargin / 2).toInt()
            val l = centerX - (avatarSize / 2).toInt() + offsetX
            val t = centerY - (avatarSize / 2).toInt()
            val r = l + avatarSize.toInt()
            val b = t + avatarSize.toInt()
            child.layout(l, t, r, b)
        }
    }
}