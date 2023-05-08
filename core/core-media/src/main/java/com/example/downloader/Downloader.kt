package com.example.downloader

interface Downloader {

    fun downloadPodcast(url: String): Long
}