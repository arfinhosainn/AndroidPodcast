package com.example.mappers

import com.example.model.EpisodeEntity
import com.example.model.EpisodeSong

fun Episode.toEpisodeSong(): EpisodeSong {
    return EpisodeSong(
        episode_id = episode_id,
        author_id = author_id,
        playback_url = playback_url,
        show_id = show_id,
        image_url = image_url,
        title = title,
        image_original_url = image_original_url,
        published_at = published_at,
        download_url = download_url,
        duration = duration
    )
}

fun Episode.toEpisodeEntity(): EpisodeEntity {
    return EpisodeEntity(
        episode_id = episode_id,
        author_id = author_id,
        playback_url = playback_url,
        show_id = show_id,
        image_url = image_url,
        title = title,
        image_original_url = image_original_url,
        published_at = published_at,
        download_url = download_url,
        duration = duration,
        download_enabled = download_enabled,
        explicit = explicit,
        image_transformation = image_transformation,
        site_url = site_url,
        type = type,
        waveform_url = waveform_url
    )
}

fun EpisodeEntity.toEpisode(): Episode {
    return Episode(
        episode_id = episode_id,
        author_id = author_id,
        playback_url = playback_url,
        show_id = show_id,
        image_url = image_url,
        title = title,
        image_original_url = image_original_url,
        published_at = published_at,
        download_url = download_url,
        duration = duration,
        download_enabled = download_enabled,
        explicit = explicit,
        image_transformation = image_transformation,
        site_url = site_url,
        type = type,
        waveform_url = waveform_url
    )
}


