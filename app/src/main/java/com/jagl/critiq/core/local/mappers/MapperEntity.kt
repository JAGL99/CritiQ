package com.jagl.critiq.core.local.mappers

interface MapperEntity<Entity, DomainModel> {
    fun toDomain(entity: Entity): DomainModel
    fun toEntity(domainModel: DomainModel): Entity
}