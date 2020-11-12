package com.verona1024.ttestapp.domain

import com.verona1024.ttestapp.data.device.DeviceInfoRepository
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import com.google.common.hash.Hashing
import com.verona1024.ttestapp.R
import com.verona1024.ttestapp.data.net.SecretFileRepository
import com.verona1024.ttestapp.domain.base.UseCase
import com.verona1024.ttestapp.presentation.TestAppApplication.Companion.applicationContext
import java.nio.charset.StandardCharsets
import java.util.zip.ZipInputStream

class TimeHashUseCase : UseCase() {
    private val deviceInfoRepository = DeviceInfoRepository()
    private val secretFileRepository = SecretFileRepository()

    suspend fun execute(step: Long, count: Int): List<Pair<String, String>> {
        return withContext(Dispatchers.IO) {
            val sdf = SimpleDateFormat(applicationContext().getString(R.string.time_format))
            val imei = deviceInfoRepository.getImei()
            val list = ArrayList<Pair<String, String>>()

            // Get file
            val file = withContext(Dispatchers.IO) {
                secretFileRepository.getFile()
            }

            // Found that downloaded file is an archive, so let's unzip it.
            val fileZip = ZipInputStream(file.inputStream())
            val unzippedFile = fileZip.nextEntry

            // Calculate data
            if (unzippedFile != null && unzippedFile.extra != null) {
                val extra = unzippedFile.extra
                val calcCalendar = Calendar.getInstance()
                for (i in 0..count) {
                    var hash = 0
                    calcCalendar.timeInMillis += step

                    for (byte in extra) {
                        hash += calculateSH256(
                            byte.toString() +
                                    imei +
                                    calcCalendar.get(Calendar.HOUR_OF_DAY) +
                                    calcCalendar.get(Calendar.MINUTE) +
                                    calcCalendar.get(Calendar.SECOND)

                        ).sum()
                    }
                    list.add(
                        Pair(
                            sdf.format(Date(calcCalendar.time.time)), hash.toString()
                        )
                    )
                }
            }

            list
        }
    }

    private fun calculateSH256(secret: String): ByteArray = Hashing.sha256()
        .hashString(secret, StandardCharsets.UTF_8)
        .asBytes()
}