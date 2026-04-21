package com.example.znotez.data.group

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.znotez.data.dataStore
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class GroupRepository(private val context: Context) {

    private val gson = Gson()
    private val KEY = stringPreferencesKey("groups")

    private fun empty(): List<Group> = emptyList()

    fun getAll(): Flow<List<Group>> =
        context.dataStore.data.map { prefs ->
            decode(prefs[KEY])
        }

    suspend fun getById(id: Long): Group? {
        val list = context.dataStore.data.first()[KEY]?.let { decode(it) } ?: empty()
        return list.find { it.id == id }
    }

    suspend fun create(name: String): Long {
        return context.dataStore.edit { prefs ->
            val list = decode(prefs[KEY]).toMutableList()

            // name unique constraint
            if (list.any { it.name == name }) return@edit

            val newId = (list.maxOfOrNull { it.id } ?: 0L) + 1
            list.add(Group(newId, name))

            prefs[KEY] = encode(list)
        }.let {
            // recompute id (simpler return path)
            val list = decode(context.dataStore.data.first()[KEY])
            list.maxOf { it.id }
        }
    }

    suspend fun update(id: Long, name: String) {
        context.dataStore.edit { prefs ->
            val list = decode(prefs[KEY]).toMutableList()

            if (list.any { it.name == name && it.id != id }) return@edit

            val index = list.indexOfFirst { it.id == id }
            if (index != -1) {
                list[index] = list[index].copy(name = name)
            }

            prefs[KEY] = encode(list)
        }
    }

    suspend fun delete(id: Long) {
        context.dataStore.edit { prefs ->
            val list = decode(prefs[KEY]).filter { it.id != id }
            prefs[KEY] = encode(list)
        }
    }

    private fun encode(list: List<Group>): String =
        gson.toJson(list)

    private fun decode(raw: String?): List<Group> {
        if (raw.isNullOrBlank()) return emptyList()
        val type = object : TypeToken<List<Group>>() {}.type
        return gson.fromJson(raw, type)
    }
}