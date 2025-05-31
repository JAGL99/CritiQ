package com.jagl.critiq.core.remote.mappers

fun interface MapperData<Data, DomainModel> {
    fun toDomain(data: Data): DomainModel
}