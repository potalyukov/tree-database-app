package com.example.treedatabase.domain.interactors

import com.example.treedatabase.domain.models.NodeDomain

interface ResetAllInteractor {
    suspend operator fun invoke()
}