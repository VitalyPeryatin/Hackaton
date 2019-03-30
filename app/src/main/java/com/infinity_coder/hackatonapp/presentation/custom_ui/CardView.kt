package com.infinity_coder.hackatonapp.presentation.custom_ui

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView

class CardView(context: Context, attributeSet: AttributeSet?): CardView(context, attributeSet) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measuredWidth
        val height = (width/1.56).toInt()
        setMeasuredDimension(width, height)
    }
}