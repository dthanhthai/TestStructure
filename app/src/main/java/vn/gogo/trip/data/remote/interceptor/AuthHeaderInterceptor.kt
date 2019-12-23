package vn.gogo.trip.data.remote.interceptor

import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

//class AuthHeaderInterceptor(private val getApiTokenUseCase: GetApiTokenUseCase) : Interceptor {
//
//    companion object {
//        private const val AUTHORIZATION_HEADER = "Authorization"
//    }
//
//    override fun intercept(chain: Interceptor.Chain): Response = runBlocking {
//        val authorizationToken = getApiTokenUseCase.execute()
//        val newRequest = if (authorizationToken.isNotEmpty()) {
//            createRequestHeader(chain = chain, token = "Bearer $authorizationToken")
//        } else {
//            createRequestHeader(chain = chain)
//        }
//        return@runBlocking chain.proceed(newRequest)
//    }
//
//    private fun createRequestHeader(chain: Interceptor.Chain): Request =
//        chain.request()
//            .newBuilder()
//            .build()
//
//    private fun createRequestHeader(chain: Interceptor.Chain, token: String): Request =
//        chain.request()
//            .newBuilder()
//            .header(AUTHORIZATION_HEADER, token)
//            .build()
//}