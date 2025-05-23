package com.jagl.critiq.core.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jagl.critiq.core.utils.mappers.MapperEntity
import com.jagl.critiq.domain.data.MediaDomain

@Entity(tableName = "media")
data class MediaEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val posterPath: String,
    val backdropPath: String,
    val type: String,
    val rating: Double,
    val releaseDate: String,
    val description: String,
    val page: Int
) {
    companion object : MapperEntity<MediaEntity, MediaDomain> {
        override fun toDomain(entity: MediaEntity): MediaDomain {
            return MediaDomain(
                id = entity.id,
                title = entity.title,
                posterPath = entity.posterPath,
                backdropPath = entity.backdropPath,
                type = entity.type,
                rating = entity.rating,
                releaseDate = entity.releaseDate,
                description = entity.description,
                page = entity.page
            )
        }

        override fun toEntity(domainModel: MediaDomain): MediaEntity {
            return MediaEntity(
                id = domainModel.id,
                title = domainModel.title,
                posterPath = domainModel.posterPath,
                backdropPath = domainModel.backdropPath,
                type = domainModel.type,
                rating = domainModel.rating,
                releaseDate = domainModel.releaseDate,
                description = domainModel.description,
                page = domainModel.page
            )
        }

    }
}