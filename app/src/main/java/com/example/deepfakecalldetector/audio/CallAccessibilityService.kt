package com.example.deepfakecalldetector.audio

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import androidx.core.content.ContextCompat

class CallAccessibilityService : AccessibilityService() {

    private var audioRecorder: AudioRecorder? = null

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        event?.let {
            if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                val className = event.className?.toString()
                Log.d("CallAccessibilityService", "Window changed: $className")

                // Detect dialer or call screens
                if (className?.contains("com.android.incallui") == true) {
                    Log.d("CallAccessibilityService", "Call screen detected!")
                    startAudioRecording()
                }
            }
        }
    }

    override fun onInterrupt() {
        Log.d("CallAccessibilityService", "Service interrupted.")
        stopAudioRecording()
    }

    private fun startAudioRecording() {
        val context = applicationContext
        val audioProcessor = AudioProcessor(context) { isDeepfake ->
            Log.d("CallAccessibilityService", "Deepfake result: $isDeepfake")
        }
        audioRecorder = AudioRecorder(context) { audioData ->
            audioProcessor.processAudioData(audioData)
        }
        audioRecorder?.startRecording()
    }

    private fun stopAudioRecording() {
        audioRecorder?.stopRecording()
        audioRecorder = null
    }
}
