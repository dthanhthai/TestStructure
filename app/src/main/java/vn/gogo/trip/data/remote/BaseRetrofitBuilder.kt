package vn.gogo.trip.data.remote

import ascend.wefresh.core.data.api.BaseUrl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit

class BaseRetrofitBuilder(
    val baseOkHttpClintBuilder: OkHttpClient.Builder,
    val converterFactory: Converter.Factory,
    val defaultBaseUrl: BaseUrl
) {
    fun build(
        baseUrl: BaseUrl = defaultBaseUrl,
        vararg interceptors: Interceptor
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(converterFactory)
            .client(baseOkHttpClintBuilder.apply {
                interceptors.forEach { interceptor ->
                    this.addInterceptor(interceptor)
                }
            }.build())
            .baseUrl(baseUrl.url)
            .build()
}