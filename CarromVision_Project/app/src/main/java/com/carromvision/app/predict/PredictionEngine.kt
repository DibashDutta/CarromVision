package com.carromvision.app.predict

import android.content.Context
import android.os.Handler
import android.os.Looper
import com.carromvision.app.util.Prefs
import kotlin.random.Random

data class Predictions(
    val linesWhite: List<FloatArray>,
    val linesBlack: List<FloatArray>,
    val linesRed: List<FloatArray>,
    val pocketCenters: List<FloatArray>
)

object PredictionEngine {

    private var learned = false

    fun beginLearning(context: Context, onComplete: () -> Unit) {
        // Simulate learning time; replace with real data capture/model training
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            learned = true
            Prefs.setLearningMode(context, false)
            onComplete()
        }, 10000) // 10 seconds placeholder
    }

    fun isLearned(): Boolean = learned

    fun getPredictions(w: Int, h: Int): Predictions {
        // Placeholder demo predictions (randomized) — replace with real physics/ML
        val linesWhite = List(2) {
            floatArrayOf(
                (w * 0.2f), (h * (0.2f + it*0.1f)),
                (w * 0.8f), (h * (0.2f + it*0.1f))
            )
        }
        val linesBlack = List(2) {
            floatArrayOf(
                (w * 0.2f), (h * (0.5f + it*0.1f)),
                (w * 0.8f), (h * (0.5f + it*0.1f))
            )
        }
        val linesRed = listOf(
            floatArrayOf((w*0.3f), (h*0.8f), (w*0.7f), (h*0.3f))
        )
        val pockets = listOf(
            floatArrayOf(24f, 24f),
            floatArrayOf(w-24f, 24f),
            floatArrayOf(24f, h-24f),
            floatArrayOf(w-24f, h-24f)
        )
        return Predictions(linesWhite, linesBlack, linesRed, pockets.shuffled().take(2))
    }
}
