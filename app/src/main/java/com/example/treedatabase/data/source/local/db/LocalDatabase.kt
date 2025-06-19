package com.example.treedatabase.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.treedatabase.data.source.remote.api.simulated_remote_db.RemoteNodeEntity

@Database(entities = [LocalNodeEntity::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun localNodeDao(): LocalNodeDao
}