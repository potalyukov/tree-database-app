package com.example.treedatabase.domain.interactors

import com.example.treedatabase.domain.models.NodeDomain
import kotlinx.coroutines.flow.Flow

interface FetchRemoteDatabaseInteractor {
    operator fun invoke(): Flow<List<NodeDomain>>
}