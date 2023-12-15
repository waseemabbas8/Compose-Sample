package com.waseem.sample.core.usecase

interface UseCase<out T : Any, in Params : Any> {
    suspend operator fun invoke(params: Params): Result<T>
}
