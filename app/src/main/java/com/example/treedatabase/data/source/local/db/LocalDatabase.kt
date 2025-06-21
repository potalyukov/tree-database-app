package com.example.treedatabase.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalNodeEntity::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun localNodeDao(): LocalNodeDao
}