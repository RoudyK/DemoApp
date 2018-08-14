package com.roudykk.remote.mapper

import com.roudykk.data.model.VideoEntity
import com.roudykk.domain.model.Video
import com.roudykk.remote.model.VideoModel
import javax.inject.Inject

class VideoModelMapper @Inject constructor() : ModelMapper<VideoModel, VideoEntity> {

    override fun mapToModel(entity: VideoEntity): VideoModel {
        return VideoModel(id = entity.id,
                iso_639_1 = entity.isoName,
                name = entity.name,
                key = entity.key,
                type = entity.type,
                site = entity.site,
                size = entity.size)
    }

    override fun mapFromModel(domain: VideoModel): VideoEntity {
        return VideoEntity(id = domain.id,
                isoName = domain.iso_639_1,
                name = domain.name,
                key = domain.key,
                type = domain.type,
                site = domain.site,
                size = domain.size)
    }

}