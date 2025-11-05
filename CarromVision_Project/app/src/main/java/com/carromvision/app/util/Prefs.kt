package com.carromvision.app.util

import android.content.Context
import android.content.SharedPreferences

object Prefs {
    private const val NAME = "carrom_prefs"
    private const val KEY_LEARNING = "learning_mode"

    private fun prefs(ctx: Context): SharedPreferences =
        ctx.getSharedPreferences(NAME, Context.MODE_PRIVATE)

    fun setLearningMode(ctx: Context, value: Boolean) {
        prefs(ctx).edit().putBoolean(KEY_LEARNING, value).apply()
    }
    fun isLearningMode(ctx: Context): Boolean =
        prefs(ctx).getBoolean(KEY_LEARNING, false)
}
