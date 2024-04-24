package com.example.drawapp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View


class DrawingView(context: Context,attrs:AttributeSet): View(context,attrs) {

    private var mDrawPath: CustomPath? = null
    private var mCanvasBitmap: Bitmap? = null

    private var mDrawPaint: Paint? = null
    private var mCanvasPaint: Paint? = null

    private var mBrushSize: Float = 0.toFloat()
    // A variable to hold a color of the stroke.
    private var color = Color.BLACK

    private var canvas: Canvas? = null

    private val mPaths = ArrayList<CustomPath>()

    private val mUndoPaths = ArrayList<CustomPath>()


    init {
        setUpDrawing()
    }

    // This method initializes the attributes of the ViewForDrawing class.
    private fun setUpDrawing() {
        mDrawPaint = Paint()
        mDrawPath = CustomPath(color, mBrushSize)

        mDrawPaint?.color = color

        mDrawPaint?.style = Paint.Style.STROKE
        mDrawPaint?.strokeJoin = Paint.Join.ROUND
        mDrawPaint?.strokeCap = Paint.Cap.ROUND

        mCanvasPaint = Paint(Paint.DITHER_FLAG)
        //mBrushSize = 20.toFloat()
    }

    override fun onSizeChanged(w: Int, h: Int, wprev: Int, hprev: Int) {
        super.onSizeChanged(w, h, wprev, hprev)
        mCanvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
        canvas = Canvas(mCanvasBitmap!!)
    }


    // This method is called when a stroke is drawn on the canvas as a part of the painting.
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

    }


    // This method acts as an event listener when a touch event is detected on the device.
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchX = event.x // Touch event of X coordinate
        val touchY = event.y // touch event of Y coordinate

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {}

            MotionEvent.ACTION_MOVE -> {}

            MotionEvent.ACTION_UP -> {}
            
            else -> return false
        }

        invalidate()
        return true
    }


    // This method is called when either the brush/eraser sizes are to be changed.
    fun setSizeForBrush(newSize: Float) {
        mBrushSize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, newSize,
            resources.displayMetrics
        )
        mDrawPaint!!.strokeWidth = mBrushSize
    }

    // This function is called when the user desires a color change.
    fun setColor(newColor: String) {
        color = Color.parseColor(newColor)
        mDrawPaint!!.color = color
    }

    // This function removes the last stroke input by the user depending on the number of times undo has been activated.
    fun onClickUndo() {
        if (mPaths.size > 0) {

            mUndoPaths.add(mPaths.removeAt(mPaths.size - 1))
            invalidate() // Invalidate the whole view. If the view is visible
        }
    }


    internal inner class CustomPath(var color:Int,var brushThickness:Float):Path()

}
