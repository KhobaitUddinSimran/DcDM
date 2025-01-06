package com.example.deepfakecalldetector

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
