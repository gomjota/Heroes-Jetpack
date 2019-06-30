package com.juangomez.heroes_jetpack.domain.model

sealed class DomainError
object NotInternetDomainError : DomainError()
data class UnknownDomainError(val errorMessage: String = "Unknown Error") : DomainError()
data class NotIndexFoundDomainError(val key: String) : DomainError()
object AuthDomainError : DomainError()
