package com.surf2024.learningcustomviews.features.avatars_line_view

import android.content.Context
import android.graphics.Color
import android.graphics.Outline
import android.graphics.drawable.GradientDrawable
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.withStyledAttributes
import com.bumptech.glide.Glide
import com.surf2024.learningcustomviews.R

class AvatarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val imageView: AppCompatImageView = AppCompatImageView(context).apply {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        scaleType = ImageView.ScaleType.CENTER_CROP
        clipToOutline = true
    }

    private val borderView: View = View(context).apply {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        background = GradientDrawable().apply {
            setColor(Color.TRANSPARENT)
            setStroke(borderWidth, borderColor)
            cornerRadius = avatarRadius
        }
    }

    var avatarRadius: Float = DEFAULT_AVATAR_RADIUS.dpToPx(context)
        set(value) {
            field = value
            updateShape()
        }

    var borderColor: Int = DEFAULT_BORDER_COLOR
        set(value) {
            field = value
            updateShape()
        }

    var borderWidth: Int = DEFAULT_BORDER_WIDTH.toPx(context)
        set(value) {
            field = value
            updateShape()
        }

    init {
        addView(imageView)
        addView(borderView)
        context.withStyledAttributes(attrs, R.styleable.AvatarView) {
            avatarRadius = getDimension(R.styleable.AvatarView_avatar_radius, 0f)
            borderColor = getColor(R.styleable.AvatarView_border_color, borderColor)
            borderWidth = getDimensionPixelSize(
                R.styleable.AvatarView_border_width,
                borderWidth
            ).toPx(context)
        }
        updateShape()
    }

    fun setImage(
        url: String? = null,
        placeholderResId: Int = R.drawable.avatar_view_placeholder,
        errorResId: Int = R.drawable.avatar_view_placeholder
    ) {
        Glide.with(context)
            .load(url)
            .placeholder(placeholderResId)
            .error(errorResId)
            .into(imageView)
    }

    private fun updateShape() {
        borderView.background = GradientDrawable().apply {
            setColor(Color.TRANSPARENT)
            setStroke(borderWidth, borderColor)
            cornerRadius = avatarRadius - borderWidth / 2
        }
        imageView.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                outline.setRoundRect(0, 0, view.width, view.height, avatarRadius)
            }
        }
    }

    companion object {
        private const val DEFAULT_AVATAR_RADIUS = 50f
        private const val DEFAULT_BORDER_COLOR = Color.WHITE
        private const val DEFAULT_BORDER_WIDTH = 4
    }

}


