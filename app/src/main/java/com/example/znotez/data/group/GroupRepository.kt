package com.example.znotez.data.group

class GroupRepository(private val dao: GroupDao) {

    fun getAll() = dao.getAll()

    suspend fun create(name: String): Long {
        return dao.insert(GroupEntity(name = name))
    }

    suspend fun update(id: Long, name: String) {
        dao.update(GroupEntity(id = id, name = name))
    }

    suspend fun delete(id: Long) {
        dao.delete(GroupEntity(id = id, name = ""))
    }

    suspend fun getById(id: Long): GroupEntity? {
        return dao.getById(id)
    }
}