package com.example.treedatabase.domain.interactors

interface ResetInteractor {
    suspend operator fun invoke(resetCache: Boolean = true, resetRemote: Boolean = true)
}