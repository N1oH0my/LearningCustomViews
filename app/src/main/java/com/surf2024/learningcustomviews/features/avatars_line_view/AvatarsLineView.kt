package com.surf2024.learningcustomviews.features.avatars_line_view

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.withStyledAttributes
import com.surf2024.learningcustomviews.R

/**
 * `View` для отображения круглыхх изображений в линию.
 *
 * Этот класс рисует до [maxImages] круглых изображений, загруженных по `URL`,
 * и, если изображений больше [maxImages], накладывает индикатор на дополнительный круг,
 * указывающий количество дополнительных изображений.
 *
 * @property [context] Контекст, связанный с этим видом.
 * @property [attrs] Необязательный набор атрибутов для пользовательских XML атрибутов.
 * @property [defStyleAttr] Атрибут стиля по умолчанию.
 */
class AvatarsLineView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    /**
     * Список URL-адресов изображений для аватаров.
     * При установке нового значения будет запрашиваться повторная компоновка.
     */
    var imageUrls: List<String> = emptyList()
        set(value) {
            field = value
            requestLayout()
        }

    /**
     * Максимальное количество изображений, которые могут быть отображены.
     * При установке нового значения будет запрашиваться повторная компоновка.
     */
    var maxImages: Int = DEFAULT_MAX_IMAGES
        set(value) {
            field = value
            requestLayout()
        }

    /**
     * Радиус аватара.
     * При установке нового значения будет запрашиваться повторная компоновка.
     */
    var avatarRadius: Float = DEFAULT_AVATAR_RADIUS_DP.dpToPx(context)
        set(value) {
            field = value
            requestLayout()
        }

    /**
     * Размер аватара.
     * При установке нового значения будет запрашиваться повторная компоновка.
     */
    var avatarSize: Float = DEFAULT_AVATAR_SIZE_DP.dpToPx(context)
        set(value) {
            field = value
            requestLayout()
        }

    /**
     * Отступ между аватарами.
     * При установке нового значения будет запрашиваться повторная компоновка.
     */
    var avatarsMargin: Float = DEFAULT_AVATARS_MARGIN_DP.dpToPx(context)
        set(value) {
            field = value
            requestLayout()
        }

    /**
     * Размер текста.
     * При установке нового значения будет запрашиваться повторная компоновка.
     */
    var textSize: Float = DEFAULT_TEXT_SIZE_SP.spToPx(context)
        set(value) {
            field = value
            requestLayout()
        }

    /**
     * Цвет текста.
     * При установке нового значения будет запрашиваться повторная компоновка.
     */
    var textColor: Int = ContextCompat.getColor(context, R.color.black)
        set(value) {
            field = value
            requestLayout()
        }

    /**
     * Цвет обводки.
     * При установке нового значения будет запрашиваться повторная компоновка.
     */
    var avatarsBorderColor: Int = ContextCompat.getColor(context, R.color.white)
        set(value) {
            field = value
            requestLayout()
        }

    /**
     * Размер обводки.
     * При установке нового значения будет запрашиваться повторная компоновка.
     */
    var avatarsBorderWidth: Int = DEFAULT_AVATARS_BORDER_WIDTH_DP.toPx(context)
        set(value) {
            field = value
            requestLayout()
        }

    init {
        context.withStyledAttributes(attrs, R.styleable.AvatarsLineView) {
            maxImages = getInteger(R.styleable.AvatarsLineView_max_avatars, maxImages)
            avatarRadius =
                getDimension(R.styleable.AvatarsLineView_avatars_radius, avatarRadius)
            avatarSize = getDimension(R.styleable.AvatarsLineView_avatar_size, avatarSize)
            avatarsMargin =
                getDimension(R.styleable.AvatarsLineView_avatars_margin, avatarsMargin)
            textSize = getDimension(R.styleable.AvatarsLineView_text_size, textSize)
            avatarsBorderColor =
                getColor(R.styleable.AvatarsLineView_avatars_border_color, avatarsBorderColor)
            textColor =
                getColor(R.styleable.AvatarsLineView_text_color, textColor)
            avatarsBorderWidth = getDimensionPixelSize(
                R.styleable.AvatarsLineView_avatars_border_width,
                avatarsBorderWidth
            )
        }
    }

    /**
     * Устанавливает список `URL` изображений, которые будут отображаться как аватары.
     *
     * Этот метод удаляет все существующие представления и создает новые аватары
     * на основе переданных URL. Если количество изображений превышает максимальное
     * количество, показывается накладной круг с количеством оставшихся изображений.
     *
     * @param urls Список URL изображений для загрузки и отображения.
     *             Если список пустой, то не будет создано ни одного аватара.
     */
    @SuppressLint("SetTextI18n")
    fun setImages(
        urls: List<String>,
        placeholderResId: Int = R.drawable.avatar_view_placeholder,
        errorResId: Int = R.drawable.avatar_view_placeholder,
        lastPlaceholderResId: Int = R.drawable.avatar_view_placeholder,
    ) {
        imageUrls = urls
        removeAllViews()

        val displayedImages = minOf(urls.size, maxImages)

        for (i in 0 until displayedImages) {
            val avatarView = AvatarView(context).apply {
                layoutParams = LayoutParams(avatarSize.toInt(), avatarSize.toInt())
                avatarRadius = this@AvatarsLineView.avatarRadius
                borderColor = avatarsBorderColor
                borderWidth = avatarsBorderWidth
                setImage(urls[i], placeholderResId, errorResId)
            }
            addView(avatarView)
        }

        if (urls.size > maxImages) {
            val remainingCount = urls.size - maxImages
            val greyAvatarView = AvatarView(context).apply {
                layoutParams = LayoutParams(avatarSize.toInt(), avatarSize.toInt())
                avatarRadius = this@AvatarsLineView.avatarRadius
                borderColor = avatarsBorderColor
                borderWidth = avatarsBorderWidth
                setImage(errorResId = lastPlaceholderResId)
                val textView = TextView(context).apply {
                    text = "+$remainingCount"
                    setTextColor(textColor)
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

    /**
     * Измеряет размеры представления.
     *
     * Этот метод используется для вычисления ширины и высоты компонента
     * на основе предоставленных спецификаций измерения.
     * Он учитывает общее количество изображений и их размеры, чтобы установить правильные
     * размеры для данного представления.
     *
     * @param widthMeasureSpec Спецификация измерения ширины.
     * @param heightMeasureSpec Спецификация измерения высоты.
     */
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

    /**
     * Располагает дочерние представления внутри текущего компонента.
     *
     * Этот метод отвечает за размещение аватаров по центру Y и началу от X с учетом
     * отступов между ними.
     * Каждый дочерний элемент получает свои координаты на основе текущего положения и
     * размеров аваторов.
     *
     * @param changed Указывает, были ли изменены размеры компонента.
     * @param left Левая граница текущего компонента.
     * @param top Верхняя граница текущего компонента.
     * @param right Правая граница текущего компонента.
     * @param bottom Нижняя граница текущего компонента.
     */
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

    companion object {
        private const val DEFAULT_MAX_IMAGES = 4
        private const val DEFAULT_AVATAR_RADIUS_DP = 50f
        private const val DEFAULT_AVATAR_SIZE_DP = 70f
        private const val DEFAULT_AVATARS_MARGIN_DP = 45f
        private const val DEFAULT_TEXT_SIZE_SP = 20f
        private const val DEFAULT_AVATARS_BORDER_WIDTH_DP = 3
    }

}