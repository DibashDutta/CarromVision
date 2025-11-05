package com.carromvision.app

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.carromvision.app.overlay.OverlayService
import com.carromvision.app.util.AppLauncher
import com.carromvision.app.util.Prefs

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnLearn: Button = findViewById(R.id.btnLearn)
        val btnStart: Button = findViewById(R.id.btnStart)

        btnLearn.setOnClickListener {
            ensureOverlayPermission {
                // Mark learning mode and start overlay
                Prefs.setLearningMode(this, true)
                startOverlayService()
                // Directly launch Carrom Pool for learning
                AppLauncher.launchCarromPool(this)
                Toast.makeText(this, "Learning started. Play a few games.", Toast.LENGTH_LONG).show()
            }
        }

        btnStart.setOnClickListener {
            ensureOverlayPermission {
                Prefs.setLearningMode(this, false)
                startOverlayService()
                AppLauncher.launchCarromPool(this)
            }
        }
    }

    private fun startOverlayService() {
        val intent = Intent(this, OverlayService::class.java)
        startForegroundService(intent)
    }

    private fun ensureOverlayPermission(onGranted: () -> Unit) {
        if (!Settings.canDrawOverlays(this)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:$packageName"))
            startActivityForResult(intent, 101)
            Toast.makeText(this, "Please grant 'Draw over other apps' then try again.", Toast.LENGTH_LONG).show()
        } else {
            onGranted()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {
            if (Settings.canDrawOverlays(this)) {
                Toast.makeText(this, "Permission granted. Tap again.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Overlay permission required.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
