package com.example.deepfakecalldetector

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.TelephonyManager
import android.util.Log
import com.example.deepfakecalldetector.audio.AudioRecordingService

class CallReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == TelephonyManager.ACTION_PHONE_STATE_CHANGED) {
            val state = intent.getStringExtra(TelephonyManager.EXTRA_STATE)

            when (state) {
                TelephonyManager.EXTRA_STATE_RINGING -> {
                    Log.d("CallReceiver", "Incoming call detected!")
                    startAudioRecordingService(context)
                }
                TelephonyManager.EXTRA_STATE_OFFHOOK -> {
                    Log.d("CallReceiver", "Call answered or outgoing call!")
                    startAudioRecordingService(context)
                }
                TelephonyManager.EXTRA_STATE_IDLE -> {
                    Log.d("CallReceiver", "Call ended!")
                    stopAudioRecordingService(context)
                }
            }
        }
    }

    private fun startAudioRecordingService(context: Context) {
        val serviceIntent = Intent(context, AudioRecordingService::class.java)
        context.startForegroundService(serviceIntent) // Start service in foreground mode
    }

    private fun stopAudioRecordingService(context: Context) {
        val serviceIntent = Intent(context, AudioRecordingService::class.java)
        context.stopService(serviceIntent) // Stop the service when the call ends
    }
}