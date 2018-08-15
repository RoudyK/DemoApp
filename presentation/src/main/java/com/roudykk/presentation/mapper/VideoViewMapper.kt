package com.roudykk.presentation.mapper

import com.roudykk.domain.model.Video
import com.roudykk.presentation.model.VideoView
import javax.inject.Inject

class VideoViewMapper @Inject constructor() : ViewMapper<VideoView, Video> {

    override fun mapToView(domain: Video): VideoView {
        return VideoView(id = domain.id,
                isoName = domain.isoName,
                name = domain.name,
                key = domain.key,
                type = domain.type,
                site = domain.site,
                size = domain.size)
    }

}