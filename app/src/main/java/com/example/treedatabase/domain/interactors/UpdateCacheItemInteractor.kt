package com.example.treedatabase.domain.interactors

import com.example.treedatabase.domain.models.NodeDomain

interface UpdateCacheItemInteractor {
    suspend operator fun invoke(updatedNode: NodeDomain)
}