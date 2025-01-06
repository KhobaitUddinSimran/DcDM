package com.example.deepfakecalldetector.audio

data class DetectedAudio(
    val phoneNumber: String,
    val status: String, // "In Progress", "Verified", or "Suspected Deepfake"
    val date: String
)

val sampleAudioList = listOf(
    DetectedAudio("+6011-26163915", "In Progress", "15/03/2022"),
    DetectedAudio("+6011-26163915", "Verified", "15/03/2022"),
    DetectedAudio("+6011-26163915", "Verified", "15/03/2022"),
    DetectedAudio("+6011-26163915", "Suspected Deepfake", "15/03/2022"),
    DetectedAudio("+6011-26163915", "Suspected Deepfake", "15/03/2022"),
    DetectedAudio("+6011-26163915", "Verified", "15/03/2022")
)
