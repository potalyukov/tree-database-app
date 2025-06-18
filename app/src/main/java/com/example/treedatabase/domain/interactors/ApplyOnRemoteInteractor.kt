package com.example.treedatabase.domain.interactors

import com.example.treedatabase.domain.models.NodeDomain

interface ApplyOnRemoteInteractor {
    suspend operator fun invoke(nodes: List<NodeDomain>)
}
