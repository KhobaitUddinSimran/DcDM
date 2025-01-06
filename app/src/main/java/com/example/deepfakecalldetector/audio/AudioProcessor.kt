package com.example.deepfakecalldetector.audio

import android.content.Context
import android.util.Log
import com.example.deepfakecalldetector.AudioRequest
import com.example.deepfakecalldetector.DeepfakeResponse
import com.example.deepfakecalldetector.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AudioProcessor(
    private val context: Context,
    private val onResult: (Boolean) -> Unit
) {

    fun processAudioData(audioData: ByteArray) {
        Log.d("AudioProcessor", "Received audio data chunk: ${audioData.size} bytes")

        CoroutineScope(Dispatchers.IO).launch {
            val call = RetrofitClient.apiService.detectDeepfake(AudioRequest(audioData))
            call.enqueue(object : Callback<DeepfakeResponse> {
                override fun onResponse(call: Call<DeepfakeResponse>, response: Response<DeepfakeResponse>) {
                    if (response.isSuccessful) {
                        val isDeepfake = response.body()?.isDeepfake ?: false
                        Log.d("AudioProcessor", "Deepfake detected: $isDeepfake")
                        NotificationHelper.showNotification(context, isDeepfake) // Show notification
                        onResult(isDeepfake) // Update UI
                    } else {
                        Log.e("AudioProcessor", "API request failed")
                    }
                }

                override fun onFailure(call: Call<DeepfakeResponse>, t: Throwable) {
                    Log.e("AudioProcessor", "API call failed: ${t.message}")
                }
            })
        }
    }
}