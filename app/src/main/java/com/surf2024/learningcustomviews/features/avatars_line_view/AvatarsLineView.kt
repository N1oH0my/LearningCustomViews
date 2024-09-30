package com.surf2024.learningcustomviews.features.avatars_line_view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import android.widget.TextView
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
    var maxImages: Int = 4
        set(value) {
            field = value
            requestLayout()
        }

    /**
     * Радиус аватара.
     * При установке нового значения будет запрашиваться повторная компоновка.
     */
    var avatarRadius: Float = 20f
        set(value) {
            field = value
            requestLayout()
        }

    /**
     * Размер аватара.
     * При установке нового значения будет запрашиваться повторная компоновка.
     */
    var avatarSize: Float = 200f
        set(value) {
            field = value
            requestLayout()
        }

    /**
     * Отступ между аватарами.
     * При установке нового значения будет запрашиваться повторная компоновка.
     */
    var avatarsMargin: Float = 100f
        set(value) {
            field = value
            requestLayout()
        }

    /**
     * Размер текста.
     * При установке нового значения будет запрашиваться повторная компоновка.
     */
    var textSize: Float = 50f
        set(value) {
            field = value
            requestLayout()
        }

    init {
        context.theme.obtainStyledAttributes(attrs, R.styleable.AvatarsLineView, 0, 0).apply {
            try {
                maxImages = getInteger(R.styleable.AvatarsLineView_max_avatars, maxImages)
                avatarRadius =
                    getDimension(R.styleable.AvatarsLineView_avatars_radius, avatarRadius)
                avatarSize = getDimension(R.styleable.AvatarsLineView_avatar_size, avatarSize)
                avatarsMargin =
                    getDimension(R.styleable.AvatarsLineView_avatars_margin, avatarsMargin)
                textSize = getDimension(R.styleable.AvatarsLineView_text_size, textSize)
            } finally {
                recycle()
            }
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
}