package com.surf2024.learningcustomviews.features.avatars_line_view

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.surf2024.learningcustomviews.R

class AvatarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private val imageView: AppCompatImageView

    var avatarRadius: Float = 0f
        set(value) {
            field = value
            radius = value
            invalidate()
        }

    init {
        imageView = AppCompatImageView(context).apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            foregroundGravity = Gravity.CENTER
            scaleType = ImageView.ScaleType.FIT_XY
            setImageResource(R.drawable.avatar_view_placeholder)
        }
        addView(imageView)

        context.theme.obtainStyledAttributes(attrs, R.styleable.AvatarView, 0, 0).apply {
            try {
                avatarRadius = getDimension(R.styleable.AvatarView_avatar_radius, 0f)
            } finally {
                recycle()
            }
        }

        radius = avatarRadius
    }

    fun setImage(url: String?) {
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.avatar_view_placeholder)
            .error(R.drawable.avatar_view_placeholder)
            .into(imageView)
    }

}
