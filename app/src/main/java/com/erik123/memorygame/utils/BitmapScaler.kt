package com.erik123.memorygame.utils

import android.graphics.Bitmap

object BitmapScaler {

    fun scaleToFitWidth(b: Bitmap, width: Int,): Bitmap {
        val factor = width / b.width.toFloat()
        return Bitmap.createScaledBitmap(b, width, (b.height * factor).toInt(), true)


    }
    fun scaleToFitHeight(b: Bitmap, height: Int,): Bitmap
    {
        val factor = height / b.width.toFloat()
        return Bitmap.createScaledBitmap(b, height, (b.width * factor).toInt(), true)

    }

}
