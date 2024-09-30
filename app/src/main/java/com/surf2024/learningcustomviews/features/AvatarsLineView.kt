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
import com.surf2024.learningcustomviews.features.AvatarsLineView.Companion.MAX_IMAGES

/**
 * `View` для отображения круглыхх изображений в линию.
 *
 * Этот класс рисует до [MAX_IMAGES] круглых изображений, загруженных по `URL`,
 * и, если изображений больше [MAX_IMAGES], накладывает индикатор на дополнительный круг,
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
) : View(context, attrs, defStyleAttr) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var imageBitmaps: List<Bitmap?> = emptyList()

    /**
     * Устанавливает список `URL` изображений, которые будут отображаться как аватары.
     *
     * Этот метод загружает изображения с помощью `Glide`,
     * применяет круглую обрезку
     * и обновляет `view` с загруженными битмапами.
     *
     * @param [urls] Список URL изображений для загрузки и отображения.
     */
    fun setImages(urls: List<String>) {
        imageBitmaps = mutableListOf()
        for (url in urls) {
            Glide.with(context)
                .asBitmap()
                .load(url)
                .transform(CircleCrop())
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        (imageBitmaps as MutableList).add(resource)
                        invalidate()
                    }
                })
        }
    }

    /**
     * Рисует `view`. Этот метод вызывается каждый раз, когда `view` необходимо перерисовать.
     *
     * Он вычисляет позиции для каждого аватара и вызывает соответствующие методы рисования.
     *
     * @param [canvas] Холст.
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val count = imageBitmaps.size
        val centerX = imageRadius / 2 + imagesMargin
        val centerY = imageRadius / 2 + imagesMargin

        for (i in 0 until minOf(count, MAX_IMAGES)) {
            drawCircleImage(canvas, imageBitmaps[i], centerX, centerY, i)
        }

        if (count > MAX_IMAGES) {
            drawOverlayCircle(canvas, centerX, centerY, count - MAX_IMAGES)
        }
    }

    /**
     * Рисует круглое изображение в заданной позиции на холсте.
     *
     * @param [canvas] Холст, на котором будет нарисовано изображение.
     * @param [bitmap] Битмап для рисования. Если `null`, ничего не рисуется.
     * @param [centerX] X-координата центра изображения.
     * @param [centerY] Y-координата центра изображения.
     * @param [index] Индекс изображения в списке, используемый для позиционирования.
     */
    private fun drawCircleImage(
        canvas: Canvas,
        bitmap: Bitmap?,
        centerX: Float,
        centerY: Float,
        index: Int
    ) {
        if (bitmap != null) {
            val left = centerX - imageRadius + index * imagesMargin
            val top = centerY - imageRadius
            val right = centerX + imageRadius + index * imagesMargin
            val bottom = centerY + imageRadius

            canvas.drawBitmap(bitmap, null, RectF(left, top, right, bottom), paint)
        }
    }

    /**
     * Рисует накладной круг, указывающий количество оставшихся изображений.
     *
     * @param [canvas] Холст, на котором будет нарисована накладка.
     * @param [centerX] X-координата центра накладки.
     * @param [centerY] Y-координата центра накладки.
     * @param [remaining] Количество оставшихся изображений для отображения.
     * @param [index] Индекс позиции накладки (по умолчанию [MAX_IMAGES]).
     */
    private fun drawOverlayCircle(
        canvas: Canvas,
        centerX: Float,
        centerY: Float,
        remaining: Int,
        index: Int = MAX_IMAGES
    ) {
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
        /** Максимальное количество изобрежаний. */
        const val MAX_IMAGES = 4
        /** Радиус по умолчанию для изображений. */
        const val imageRadius = 200f
        /** Отступ между изображениями. */
        const val imagesMargin = 150f
        /** Размер текста для индикатора накладки. */
        const val textSize = 100f
    }

}
