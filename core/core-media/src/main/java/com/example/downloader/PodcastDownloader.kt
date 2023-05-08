package com.example.downloader

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.core.net.toUri

class PodcastDownloader(
    private val context: Context
) : Downloader {

    private val downloadManager = context.getSystemService(DownloadManager::class.java)

    override fun downloadPodcast(url: String): Long {
        val request = DownloadManager.Request(url.toUri())
            .setMimeType("audio/*")
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setTitle("podcast.mp3")
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "podcast.mp3")
        return downloadManager.enqueue(request)
    }
}