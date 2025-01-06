package com.example.deepfakecalldetector

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

fun interface DeepfakeApiService {
    @POST("detect")
    fun detectDeepfake(@Body request: AudioRequest): Call<DeepfakeResponse>
}
