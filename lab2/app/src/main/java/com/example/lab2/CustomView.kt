package com.example.lab2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CustomView : View {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) :
            super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyle:
    Int) : super(context, attrs, defStyle)

    var switchedColours = false

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        val paint = Paint()
        // Paint here.
        paint.setARGB(255, 0, 0, 255)
        if (switchedColours)
            paint.setARGB(255, 255, 0, 0)
        canvas.drawRect(width.toFloat()/4, height.toFloat()/4, width.toFloat()/2, height.toFloat()/3, paint)
        paint.setARGB(255, 0, 255, 0)
        if (switchedColours)
            paint.setARGB(255, 0, 0, 0)
        canvas.drawOval(3*width.toFloat()/4, height.toFloat()/2, 9*width.toFloat()/10, 9*height.toFloat()/10, paint)
    }
}