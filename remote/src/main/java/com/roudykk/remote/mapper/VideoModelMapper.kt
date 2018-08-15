package com.roudykk.remote.mapper

import com.roudykk.data.model.VideoEntity
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

    override fun mapFromModel(model: VideoModel): VideoEntity {
        return VideoEntity(id = model.id,
                isoName = model.iso_639_1,
                name = model.name,
                key = model.key,
                type = model.type,
                site = model.site,
                size = model.size)
    }

}