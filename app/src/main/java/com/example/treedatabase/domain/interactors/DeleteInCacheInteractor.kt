package com.example.treedatabase.domain.interactors

interface DeleteInCacheInteractor {
    suspend operator fun invoke(id: Long)
}

