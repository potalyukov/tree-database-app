package com.example.treedatabase.di

import com.example.treedatabase.data.repository.TreeDatabaseRepositoryImpl
import com.example.treedatabase.domain.contracts.TreeDatabaseRepository
import com.example.treedatabase.domain.interactors.ApplyOnRemoteInteractor
import com.example.treedatabase.domain.interactors.CreateNewInCacheInteractor
import com.example.treedatabase.domain.interactors.DeleteInCacheInteractor
import com.example.treedatabase.domain.interactors.FetchLocalDatabaseInteractor
import com.example.treedatabase.domain.interactors.FetchRemoteDatabaseInteractor
import com.example.treedatabase.domain.interactors.LoadRemoteNodeInteractor
import com.example.treedatabase.domain.interactors.ResetInteractor
import com.example.treedatabase.domain.interactors.UpdateCacheItemInteractor
import com.example.treedatabase.domain.interactors.impl.ApplyOnRemoteInteractorImpl
import com.example.treedatabase.domain.interactors.impl.CreateNewInCacheInteractorImpl
import com.example.treedatabase.domain.interactors.impl.DeleteInCacheInteractorImpl
import com.example.treedatabase.domain.interactors.impl.FetchLocalDatabaseInteractorImpl
import com.example.treedatabase.domain.interactors.impl.FetchRemoteDatabaseInteractorImpl
import com.example.treedatabase.domain.interactors.impl.LoadRemoteNodeInteractorImpl
import com.example.treedatabase.domain.interactors.impl.ResetInteractorImpl
import com.example.treedatabase.domain.interactors.impl.UpdateCacheItemInteractorImpl
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
    internal abstract fun bindResetInteractor(impl: ResetInteractorImpl): ResetInteractor

    @Binds
    internal abstract fun bindFetchLocalDatabaseInteractor(impl: FetchLocalDatabaseInteractorImpl): FetchLocalDatabaseInteractor

    @Binds
    internal abstract fun bindCreateNewInCacheInteractor(impl: CreateNewInCacheInteractorImpl): CreateNewInCacheInteractor

    @Binds
    internal abstract fun bindDeleteInCacheInteractor(impl: DeleteInCacheInteractorImpl): DeleteInCacheInteractor

    @Binds
    internal abstract fun bindLoadRemoteNodeInteractor(impl: LoadRemoteNodeInteractorImpl): LoadRemoteNodeInteractor

    @Binds
    internal abstract fun bindUpdateCacheItemInteractor(impl: UpdateCacheItemInteractorImpl): UpdateCacheItemInteractor
}