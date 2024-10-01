package com.surf2024.learningcustomviews.features.avatars_line_view

import android.content.Context
import android.graphics.Color
import android.graphics.Outline
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.surf2024.learningcustomviews.R

class AvatarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val imageView: AppCompatImageView
    private val borderView: View

    var avatarRadius: Float = 0f
        set(value) {
            field = value
            updateShape()
        }

    var borderColor: Int = Color.WHITE
        set(value) {
            field = value
            updateShape()
        }
    var borderWidth: Int = 10
        set(value) {
            field = value
            updateShape()
        }

    init {
        imageView = AppCompatImageView(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            scaleType = ImageView.ScaleType.CENTER_CROP
            clipToOutline = true
        }

        borderView = View(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            background = GradientDrawable().apply {
                setColor(Color.TRANSPARENT)
                setStroke(borderWidth, borderColor)
                cornerRadius = avatarRadius
            }
        }

        addView(imageView)
        addView(borderView)

        context.theme.obtainStyledAttributes(attrs, R.styleable.AvatarView, 0, 0).apply {
            try {
                avatarRadius = getDimension(R.styleable.AvatarView_avatar_radius, 0f)
                borderColor = getColor(R.styleable.AvatarView_border_color, borderColor)
                borderWidth =
                    getDimensionPixelSize(R.styleable.AvatarView_border_width, borderWidth)
            } finally {
                recycle()
            }
        }

        updateShape()
    }

    private fun updateShape() {
        borderView.background = GradientDrawable().apply {
            setColor(Color.TRANSPARENT)
            setStroke(borderWidth, borderColor)
            cornerRadius = avatarRadius
        }

        imageView.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                outline.setRoundRect(0, 0, view.width, view.height, avatarRadius)
            }
        }
    }

    fun setImage(url: String?) {
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.avatar_view_placeholder)
            .error(R.drawable.avatar_view_placeholder)
            .into(imageView)
    }

}
