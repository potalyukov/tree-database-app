package com.example.treedatabase.data.source.remote.api.simulated_remote_db

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class OnCreateDbCallback @Inject constructor(private val daoProvider: dagger.Lazy<RemoteNodeDao>) :
    RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        CoroutineScope(Dispatchers.IO).launch {
            daoProvider.get().reset()
        }
    }
}