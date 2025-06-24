package com.example.treedatabase.data.di

import android.content.Context
import androidx.room.Room
import com.example.treedatabase.data.mappers.NodeMapper
import com.example.treedatabase.data.source.local.LocalDataSource
import com.example.treedatabase.data.source.local.LocalDataSourceImpl
import com.example.treedatabase.data.source.local.db.LocalDatabase
import com.example.treedatabase.data.source.local.db.LocalNodeDao
import com.example.treedatabase.data.source.remote.RemoteDataSource
import com.example.treedatabase.data.source.remote.RemoteDataSourceImpl
import com.example.treedatabase.data.source.remote.api.SimulatedApi
import com.example.treedatabase.data.source.remote.api.TreeDatabaseApi
import com.example.treedatabase.data.source.remote.api.TreeDatabaseApiSecretDoor
import com.example.treedatabase.data.source.remote.api.simulated_remote_db.OnCreateDbCallback
import com.example.treedatabase.data.source.remote.api.simulated_remote_db.RemoteDatabase
import com.example.treedatabase.data.source.remote.api.simulated_remote_db.RemoteNodeDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val LOCAL_DB_NAME = "local_nodes_database"
private const val REMOTE_DB_NAME = "remote_nodes_database"

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {
    @Binds
    internal abstract fun bindRemoteDataSource(impl: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    internal abstract fun bindLocalDataSource(impl: LocalDataSourceImpl): LocalDataSource

    companion object {
        @Provides
        @Singleton
        fun provideRemoteNodeDatabase(
            @ApplicationContext context: Context,
            onCreateCallback: OnCreateDbCallback
        ) =
            Room.databaseBuilder(
                context.applicationContext,
                RemoteDatabase::class.java,
                REMOTE_DB_NAME
            ).addCallback(onCreateCallback).build()

        @Provides
        @Singleton
        fun provideLocalNodeDatabase(@ApplicationContext context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                LocalDatabase::class.java,
                LOCAL_DB_NAME
            ).build()

        @Provides
        @Singleton
        fun provideRemoteNodeDao(remoteDatabase: RemoteDatabase): RemoteNodeDao {
            return remoteDatabase.remoteNodeDao()
        }

        @Provides
        @Singleton
        fun provideLocalNodeDao(localDatabase: LocalDatabase): LocalNodeDao {
            return localDatabase.localNodeDao()
        }

        @Provides
        @Singleton
        fun provideTreeDatabaseApi(
            remoteNodeDao: RemoteNodeDao,
            nodeMapper: NodeMapper
        ): TreeDatabaseApi {
            return SimulatedApi(remoteNodeDao, nodeMapper)
        }

        @Provides
        @Singleton
        fun provideTreeDatabaseApiSecretDoor(api: SimulatedApi): TreeDatabaseApiSecretDoor {
            return api
        }
    }
}