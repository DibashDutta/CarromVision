package com.carromvision.app.util

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast

object AppLauncher {

    // Default package name for Carrom Pool (user can modify if needed)
    private const val CARROM_PACKAGE = "com.miniclip.carrom"

    fun launchCarromPool(context: Context) {
        val pm: PackageManager = context.packageManager
        val launchIntent = pm.getLaunchIntentForPackage(CARROM_PACKAGE)
        if (launchIntent != null) {
            context.startActivity(launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        } else {
            Toast.makeText(context, "Carrom Pool not found. Opening Play Store...", Toast.LENGTH_LONG).show()
            val uri = Uri.parse("market://details?id=$CARROM_PACKAGE")
            val goToMarket = Intent(Intent.ACTION_VIEW, uri).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(goToMarket)
        }
    }
}
