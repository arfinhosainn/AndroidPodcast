package com.example.androidpodcast.downloader

interface Downloader {

    fun downloadPodcast(url: String): Long
}