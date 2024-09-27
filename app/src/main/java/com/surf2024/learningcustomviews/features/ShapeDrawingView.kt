package com.surf2024.learningcustomviews.features

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import com.surf2024.learningcustomviews.R
import com.surf2024.learningcustomviews.features.ShapeDrawingView.Companion.MAX_SHAPES
import kotlin.random.Random

/**
 * Класс [ShapeDrawingView] представляет собой белый холсте для генерации фигур [Shape] на нём.
 *
 * По нажатию на любую точку на экране в этой точке рисуется рандомная фигура [Shape]
 * рандомного цвета, из переданного массива [colors] через `setColors(newColors: List<Int>)`.
 *
 * Если массив цветов пуст, то цвет становится по умолчанию дефолтный [defaultColor].
 *
 * Вверху `View` отображается текстом текущее количество фигур на экране [shapeCount].
 *
 * При достижении определенного количества фигур [MAX_SHAPES] - появляется уведомление "Game over"
 * о завершении игры.
 *
 * Размер фигуры рандомный `от 20 до 50 dp`.
 *
 * @constructor Создает экземпляр [ShapeDrawingView].
 * @param context Контекст, в котором будет использоваться view.
 * @param attrs Атрибуты, заданные в XML (при наличии).
 *
 * - [defaultColor] по-умолчанию [Color.GREEN].
 *
 * @param defStyleAttr Стиль по умолчанию.
 */
class ShapeDrawingView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var defaultColor = Color.GREEN
    private var colors: List<Int> = emptyList()
    private val shapes = mutableListOf<Shape>()
    private val paint = Paint()
    private var shapeCount = 0

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShapeDrawingView)
        defaultColor = typedArray.getColor(R.styleable.ShapeDrawingView_defaultColor, defaultColor)
        typedArray.recycle()
    }

    /**
     * Рисует все фигуры на заданном холсте.
     *
     * @param canvas Холст, на котором будут генерироваться фигуры.
     */
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (shape in shapes) {
            paint.color = shape.color
            when (shape.type) {
                ShapeType.CIRCLE -> canvas.drawCircle(shape.x, shape.y, shape.size / 2f, paint)
                ShapeType.SQUARE -> canvas.drawRect(
                    shape.x - shape.size / 2f, shape.y - shape.size / 2f,
                    shape.x + shape.size / 2f, shape.y + shape.size / 2f, paint
                )

                ShapeType.ROUNDED_SQUARE -> {
                    val rect = RectF(
                        /* left = */ shape.x - shape.size / 2f,
                        /* top = */ shape.y - shape.size / 2f,
                        /* right = */ shape.x + shape.size / 2f,
                        /* bottom = */ shape.y + shape.size / 2f
                    )
                    canvas.drawRoundRect(rect, 20f, 20f, paint)
                }
            }
        }
        paint.color = Color.BLACK
        paint.textSize = 50f
        canvas.drawText("Count: $shapeCount", 50f, 100f, paint)
    }

    /**
     * Обрабатывает события касания для добавления фигур.
     *
     * @param event Событие касания [MotionEvent.ACTION_DOWN].
     * @return Всегда возвращает `true`.
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            addRandomShape(event.x, event.y)
            invalidate()
        }
        return true
    }

    /**
     * Устанавливает список цветов [colors].
     * [colors] используется для случайного задания цвета фигуре,
     * вместо значения по-умолчанию [defaultColor]
     *
     * @param newColors Новый список цветов.
     */
    fun setColors(newColors: List<Int>) {
        colors = newColors
        invalidate()
    }

    /**
     * Добавляет случайную фигуру в заданной позиции.
     *
     * @param x Координата X для размещения фигуры.
     * @param y Координата Y для размещения фигуры.
     */
    private fun addRandomShape(x: Float, y: Float) {
        val shapeType = ShapeType.entries.toTypedArray().random()
        val color = if (colors.isNotEmpty()) {
            colors.random()
        } else {
            defaultColor
        }
        val size = Random.nextInt(20, 51).dpToPx(context).toFloat()

        shapes.add(Shape(shapeType, x, y, size, color))
        shapeCount++

        if (shapeCount >= MAX_SHAPES) {
            Toast.makeText(context, context.getString(R.string.game_over), Toast.LENGTH_SHORT)
                .show()
            shapes.clear()
            shapeCount = 0
        }
    }

    /**
     * Конвертирует значение в `dp` в `px`.
     *
     * @param context Контекст для получения ресурсов.
     * @return Значение в пикселях.
     */
    private fun Int.dpToPx(context: Context): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            context.resources.displayMetrics
        ).toInt()
    }

    /**
     * [MAX_SHAPES] - максимальное количество фигур на холсте.
     */
    companion object {
        const val MAX_SHAPES = 10
    }

}

/**
 * Фигура, которая будет рисоваться.
 *
 * @property type Тип фигуры [ShapeType].
 * @property x Координата X центра фигуры.
 * @property y Координата Y центра фигуры.
 * @property size Размер фигуры.
 * @property color Цвет фигуры.
 */
data class Shape(val type: ShapeType, val x: Float, val y: Float, val size: Float, val color: Int)

/**
 * Перечисление фигур, которые можно рисовать.
 */
enum class ShapeType {
    CIRCLE,
    SQUARE,
    ROUNDED_SQUARE
}