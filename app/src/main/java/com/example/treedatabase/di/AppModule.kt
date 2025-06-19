package com.example.treedatabase.di

import com.example.treedatabase.data.repository.TreeDatabaseRepositoryImpl
import com.example.treedatabase.domain.contracts.TreeDatabaseRepository
import com.example.treedatabase.domain.interactors.ApplyOnRemoteInteractor
import com.example.treedatabase.domain.interactors.ApplyOnRemoteInteractorImpl
import com.example.treedatabase.domain.interactors.CreateNewInCacheInteractor
import com.example.treedatabase.domain.interactors.CreateNewInCacheInteractorImpl
import com.example.treedatabase.domain.interactors.FetchLocalDatabaseInteractor
import com.example.treedatabase.domain.interactors.FetchLocalDatabaseInteractorImpl
import com.example.treedatabase.domain.interactors.FetchRemoteDatabaseInteractor
import com.example.treedatabase.domain.interactors.FetchRemoteDatabaseInteractorImpl
import com.example.treedatabase.domain.interactors.ResetAllInteractor
import com.example.treedatabase.domain.interactors.ResetAllInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {
    @Binds
    abstract fun bindTreeDatabaseRepository(impl: TreeDatabaseRepositoryImpl): TreeDatabaseRepository

    @Binds
    internal abstract fun bindFetchRemoteDatabaseInteractor(impl: FetchRemoteDatabaseInteractorImpl): FetchRemoteDatabaseInteractor

    @Binds
    internal abstract fun bindApplyOnRemoteInteractor(impl: ApplyOnRemoteInteractorImpl): ApplyOnRemoteInteractor

    @Binds
    internal abstract fun bindResetAllInteractor(impl: ResetAllInteractorImpl): ResetAllInteractor

    @Binds
    internal abstract fun bindFetchLocalDatabaseInteractor(impl: FetchLocalDatabaseInteractorImpl): FetchLocalDatabaseInteractor

    @Binds
    internal abstract fun bindCreateNewInCacheInteractor(impl: CreateNewInCacheInteractorImpl): CreateNewInCacheInteractor
}