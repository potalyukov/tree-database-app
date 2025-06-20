package com.example.treedatabase.domain.models

data class NodeDomain (val id: Long = 0, val value: String, val parent: Long?, val deleted : Boolean, val depth : Int = 0)