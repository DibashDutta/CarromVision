package com.carromvision.app.access

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent

class AccessibilityHelperService : AccessibilityService() {
    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        // Placeholder: can observe UI changes if needed
    }
    override fun onInterrupt() {}
}
