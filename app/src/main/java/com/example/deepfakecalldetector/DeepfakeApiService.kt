package com.example.deepfakecalldetector

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// Data class to represent the audio data being sent to the API
data class AudioRequest(val audioData: ByteArray) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as AudioRequest
        return audioData.contentEquals(other.audioData)
    }

    override fun hashCode(): Int {
        return audioData.contentHashCode()
    }
}

// Data class to represent the response from the API
data class DeepfakeResponse(val isDeepfake: Boolean)

// Interface for Retrofit API call
interface DeepfakeApiService {
    @POST("detect")
    fun detectDeepfake(@Body request: AudioRequest): Call<DeepfakeResponse>
}
