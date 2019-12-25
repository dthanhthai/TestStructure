package vn.gogo.trip.domain.base

sealed class ResultWrapper<out T> {
    data class Success<out T>(val data: T): ResultWrapper<T>()
    data class GenericError(val code: Int? = null, val error: Throwable? = null): ResultWrapper<Nothing>()
    object NetworkError: ResultWrapper<Nothing>()
}