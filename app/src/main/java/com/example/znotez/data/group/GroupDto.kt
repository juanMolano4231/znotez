package com.example.znotez.data.group

data class GroupDto(
    val id: Long,
    val name: String
)

fun GroupEntity.toDto() = GroupDto(id, name)
fun GroupDto.toEntity() = GroupEntity(id, name)