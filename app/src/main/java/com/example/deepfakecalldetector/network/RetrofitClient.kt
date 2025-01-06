package com.example.deepfakecalldetector.network



import com.example.deepfakecalldetector.DeepfakeApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://95dbe8d3-a02c-488a-bd8a-ca9cf55c0040.mock.pstmn.io"
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: DeepfakeApiService = retrofit.create(DeepfakeApiService::class.java)
}
