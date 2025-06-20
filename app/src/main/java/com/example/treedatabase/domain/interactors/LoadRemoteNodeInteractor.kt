package com.example.treedatabase.domain.interactors

interface LoadRemoteNodeInteractor {
    suspend operator fun invoke(id: Long)
}