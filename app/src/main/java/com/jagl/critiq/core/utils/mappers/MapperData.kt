package com.jagl.critiq.core.utils.mappers

fun interface MapperData<Data, DomainModel> {
    fun toDomain(data: Data): DomainModel
}