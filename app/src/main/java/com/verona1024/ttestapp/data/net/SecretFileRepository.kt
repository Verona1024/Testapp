package com.verona1024.ttestapp.data.net

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.*
import kotlin.concurrent.schedule

class SecretFileRepository {
    companion object {
        const val UPDATE_START_DELAY = 0L
        const val UPDATE_LOOP = 5000L
        const val FILE_URL = ""
    }

    private var client: OkHttpClient = OkHttpClient()
    private var downloadedFile: ByteArray? = null

    fun getFile(): ByteArray {
        return downloadedFile ?: refreshFile()
    }

    private fun refreshFile(): ByteArray {
        val request = Request.Builder()
            .url(FILE_URL)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            downloadedFile = response.body?.bytes() ?: ByteArray(0)

            return downloadedFile!!
        }
    }

    init {
        Timer().schedule(UPDATE_START_DELAY, UPDATE_LOOP) {
            try {
                refreshFile()
            } catch (e: IOException) {
                // Could not get file.
            }
        }
    }
}