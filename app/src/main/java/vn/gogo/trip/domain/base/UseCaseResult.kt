package vn.gogo.trip.domain.base

sealed class UseCaseResult<out T : Any?> {
    class Success<out T : Any>(val data: T?) : UseCaseResult<T>()
    object Complete : UseCaseResult<Nothing>()
    class Error(val exception: Throwable) : UseCaseResult<Nothing>()
}