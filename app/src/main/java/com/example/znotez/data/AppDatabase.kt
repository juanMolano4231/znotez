package com.example.znotez.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.znotez.data.group.GroupDao
import com.example.znotez.data.group.GroupEntity

@Database(    entities = [GroupEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun groupDao(): GroupDao
}