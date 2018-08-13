package com.roudykk.data.mapper

import com.roudykk.data.model.VideoEntity
import com.roudykk.domain.model.Video
import javax.inject.Inject

class VideoEntityMapper @Inject constructor() : Mapper<VideoEntity, Video> {

    override fun mapFromEntity(entity: VideoEntity): Video {
        return Video(id = entity.id,
                isoName = entity.isoName,
                name = entity.name,
                key = entity.key,
                type = entity.type,
                site = entity.site,
                size = entity.size)
    }

    override fun mapToEntity(domain: Video): VideoEntity {
        return VideoEntity(id = domain.id,
                isoName = domain.isoName,
                name = domain.name,
                key = domain.key,
                type = domain.type,
                site = domain.site,
                size = domain.size)
    }

}