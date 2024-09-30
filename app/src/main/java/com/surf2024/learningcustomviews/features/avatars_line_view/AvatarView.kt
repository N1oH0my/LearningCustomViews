package com.surf2024.learningcustomviews.features.avatars_line_view

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
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
            setImageResource(R.drawable.image_view_bg)
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
        cardElevation = 8f
    }

    fun setImage(url: String?) {
        Glide.with(context)
            .load(url)
            .placeholder(
                ColorDrawable(
                    ContextCompat.getColor(
                        context,
                        android.R.color.holo_blue_bright
                    )
                )
            )
            .error(ColorDrawable(ContextCompat.getColor(context, android.R.color.holo_red_light)))
            .into(imageView)
    }

}
