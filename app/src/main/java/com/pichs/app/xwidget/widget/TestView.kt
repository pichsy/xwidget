package com.pichs.app.xwidget.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import com.pichs.app.xwidget.R
import com.pichs.xwidget.utils.XColorHelper

class TestView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

//    private var mClipPathPaint = Paint()
//
//    private val mClipPath: Path = Path()
//
//    private var mBg: Bitmap? = null
//
//    init {
//        mClipPathPaint.color = XColorHelper.parseColor("#99f")
//        mClipPathPaint.style = Paint.Style.FILL
//        mClipPathPaint.isAntiAlias = true
//
//        mBg = drawableToBitmap(background)
//        setBackground(null)
//    }
//
//    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
//    }
//
//    override fun onDrawForeground(canvas: Canvas?) {
//        super.onDrawForeground(canvas)
//
//    }
//
//    override fun setBackgroundColor(color: Int) {
//        super.setBackgroundColor(color)
//
//    }
//
//    override fun setBackground(background: Drawable?) {
//        super.setBackground(background)
//    }
//
//    @SuppressLint("DrawAllocation")
//    override fun onDraw(canvas: Canvas?) {
//        canvas?.apply {
//            mClipPath.reset()
//            mClipPath.addRoundRect(RectF(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat()), FloatArray(8).apply {
//                this[0] = 20f
//                this[1] = 20f
//                this[2] = 30f
//                this[3] = 30f
//                this[4] = 40f
//                this[5] = 40f
//                this[6] = 50f
//                this[7] = 50f
//            }, Path.Direction.CW)
//
//            clipPath(mClipPath)
//
//            if (mBg != null) {
//                drawBitmap(mBg!!, Rect(0, 0, measuredWidth, measuredHeight), Rect(0, 0, measuredWidth, measuredHeight), null)
//            }
//
//            val bitmap = BitmapFactory.decodeResource(resources, R.drawable.panda_logo)
//            drawBitmap(bitmap, Rect(0, 0, bitmap.width, bitmap.height), Rect(0 + 20, 0 + 20, measuredWidth - 20, measuredHeight - 20), null)
//            save()
//        }
//
//        super.onDraw(canvas)
//    }
//
//    /**
//     * drawable 转 bitmap
//     * 这个函数可以放在Util类里面，算是一个公共函数
//     *
//     * @param drawable 要转换的Drawable
//     * @return 转换完成的Bitmap
//     */
//    private fun drawableToBitmap(drawable: Drawable?): Bitmap? {
//        if (drawable == null) {
//            return null
//        }
//        val w = 200
//        val h = 200
//        val config = if (drawable.opacity != PixelFormat.OPAQUE) Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
//        val bitmap = Bitmap.createBitmap(w, h, config)
//        //注意，下面三行代码要用到，否则在View或者SurfaceView里的canvas.drawBitmap会看不到图
//        val canvas = Canvas(bitmap)
//        drawable.setBounds(0, 0, w, h)
//        drawable.draw(canvas)
//        return bitmap
//    }
}