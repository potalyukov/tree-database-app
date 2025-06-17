package com.example.treedatabase.data.api.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RemoteNodeEntity::class], version = 1)
abstract class RemoteDatabase : RoomDatabase() {
    abstract fun remoteNodeDao(): RemoteNodeDao
}