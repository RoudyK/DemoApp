package com.roudykk.presentation.mapper

import com.roudykk.domain.model.Video
import com.roudykk.presentation.model.VideoView
import javax.inject.Inject

class VideoViewMapper @Inject constructor() : ViewMapper<VideoView, Video> {

    override fun mapFromView(view: VideoView): Video {
        return Video(id = view.id,
                isoName = view.isoName,
                name = view.name,
                key = view.key,
                type = view.type,
                site = view.site,
                size = view.size)
    }

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