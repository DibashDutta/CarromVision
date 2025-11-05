package com.carromvision.app.overlay

import android.content.Context
import android.graphics.*
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import com.carromvision.app.predict.PredictionEngine

class OverlayView(context: Context) : View(context) {

    private val paintWhite = Paint().apply {
        color = Color.WHITE
        strokeWidth = 3f
        style = Paint.Style.STROKE
        isAntiAlias = true
        alpha = (255 * 0.7).toInt()
    }
    private val paintBlack = Paint().apply {
        color = Color.BLACK
        strokeWidth = 3f
        style = Paint.Style.STROKE
        isAntiAlias = true
        alpha = (255 * 0.7).toInt()
    }
    private val paintRed = Paint().apply {
        color = Color.RED
        strokeWidth = 3f
        style = Paint.Style.STROKE
        isAntiAlias = true
        alpha = (255 * 0.7).toInt()
    }
    private val pocketHalo = Paint().apply {
        color = Color.GREEN
        style = Paint.Style.STROKE
        strokeWidth = 6f
        isAntiAlias = true
        alpha = (255 * 0.5).toInt()
    }

    private val handler = Handler(Looper.getMainLooper())
    private val ticker = object : Runnable {
        override fun run() {
            invalidate()
            handler.postDelayed(this, 16L) // ~60fps
        }
    }

    init { handler.post(ticker) }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Placeholder prediction data
        val predictions = PredictionEngine.getPredictions(width, height)

        // Draw predicted lines
        predictions.linesWhite.forEach { (sx, sy, ex, ey) ->
            canvas.drawLine(sx, sy, ex, ey, paintWhite)
        }
        predictions.linesBlack.forEach { (sx, sy, ex, ey) ->
            canvas.drawLine(sx, sy, ex, ey, paintBlack)
        }
        predictions.linesRed.forEach { (sx, sy, ex, ey) ->
            canvas.drawLine(sx, sy, ex, ey, paintRed)
        }

        // Draw pocket halos if any predicted goals
        predictions.pocketCenters.forEach { (cx, cy) ->
            canvas.drawCircle(cx, cy, 24f, pocketHalo)
        }
    }

    fun showToast(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }
}
