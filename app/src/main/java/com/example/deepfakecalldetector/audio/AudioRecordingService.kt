package com.example.deepfakecalldetector.audio

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.deepfakecalldetector.AudioRequest
import com.example.deepfakecalldetector.DeepfakeApiService
import com.example.deepfakecalldetector.DeepfakeResponse
import com.example.deepfakecalldetector.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AudioRecordingService : Service() {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://your-api-url.com/") // Replace with your API base URL
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val deepfakeApiService = retrofit.create(DeepfakeApiService::class.java)

    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }
        startForeground(1, createNotification())
        Log.d("AudioRecordingService", "Service started")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Here you would start recording the audio and send it to the server
        Log.d("AudioRecordingService", "Recording started")

        // Example: Replace with actual audio recording logic
        val audioData = ByteArray(1024) // Placeholder for actual audio data

        // Send audio data to the deepfake detection API
        sendAudioForDeepfakeDetection(audioData)

        return START_STICKY
    }

    private fun sendAudioForDeepfakeDetection(audioData: ByteArray) {
        val audioRequest = AudioRequest(audioData)

        deepfakeApiService.detectDeepfake(audioRequest).enqueue(object : Callback<DeepfakeResponse> {
            override fun onResponse(call: Call<DeepfakeResponse>, response: Response<DeepfakeResponse>) {
                if (response.isSuccessful) {
                    val deepfakeResponse = response.body()
                    if (deepfakeResponse != null) {
                        if (deepfakeResponse.isDeepfake) {
                            Log.d("AudioRecordingService", "Deepfake detected!")
                            // Handle deepfake detected scenario (e.g., notify user)
                        } else {
                            Log.d("AudioRecordingService", "Audio is not a deepfake.")
                        }
                    }
                } else {
                    Log.e("AudioRecordingService", "Error: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<DeepfakeResponse>, t: Throwable) {
                Log.e("AudioRecordingService", "API call failed: ${t.message}")
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        // Stop recording and release resources
        Log.d("AudioRecordingService", "Recording stopped")
    }

    override fun onBind(intent: Intent?): IBinder? = null

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            "AudioServiceChannel",
            "Audio Service Channel",
            NotificationManager.IMPORTANCE_LOW
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager?.createNotificationChannel(channel)
    }

    private fun createNotification(): Notification {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notification.Builder(this, "AudioServiceChannel")
                .setContentTitle("Recording Call")
                .setContentText("Recording audio for deepfake detection.")
                .setSmallIcon(R.drawable.ic_notification)
                .build()
        } else {
            Notification.Builder(this)
                .setContentTitle("Recording Call")
                .setContentText("Recording audio for deepfake detection.")
                .setSmallIcon(R.drawable.ic_notification)
                .build()
        }
    }
}