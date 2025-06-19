package com.example.treedatabase.domain.interactors

import com.example.treedatabase.domain.models.NodeDomain

interface CreateNewInCacheInteractor {
    suspend operator fun invoke(node: NodeDomain)
}