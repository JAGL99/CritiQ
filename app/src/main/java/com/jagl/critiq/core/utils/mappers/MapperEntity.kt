package com.jagl.critiq.core.utils.mappers

interface MapperEntity<Entity, DomainModel> {
    fun toDomain(entity: Entity): DomainModel
    fun toEntity(domainModel: DomainModel): Entity
}