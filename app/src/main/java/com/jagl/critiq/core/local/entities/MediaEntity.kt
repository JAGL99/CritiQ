package com.jagl.critiq.core.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jagl.critiq.core.local.mappers.MapperEntity
import com.jagl.critiq.core.model.Media

@Entity(tableName = "media")
data class MediaEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val title: String,
    val posterPath: String,
    val backdropPath: String,
    val type: String,
    val rating: Double,
    val releaseDate: String,
    val description: String
) {
    companion object : MapperEntity<MediaEntity, Media> {
        override fun toDomain(entity: MediaEntity): Media {
            return Media(
                id = entity.id,
                title = entity.title,
                posterPath = entity.posterPath,
                backdropPath = entity.backdropPath,
                type = entity.type,
                rating = entity.rating,
                releaseDate = entity.releaseDate,
                description = entity.description
            )
        }

        override fun toEntity(domainModel: Media): MediaEntity {
            return MediaEntity(
                id = domainModel.id,
                title = domainModel.title,
                posterPath = domainModel.posterPath,
                backdropPath = domainModel.backdropPath,
                type = domainModel.type,
                rating = domainModel.rating,
                releaseDate = domainModel.releaseDate,
                description = domainModel.description
            )
        }

    }
}