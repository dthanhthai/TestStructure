package vn.gogo.trip.di

import ascend.wefresh.core.data.api.BaseOkHttpClientBuilder
import vn.gogo.trip.data.remote.BaseRetrofitBuilder
import ascend.wefresh.core.data.api.BaseUrl
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.converter.gson.GsonConverterFactory

//-DI NAME FOR WEFRESH GRAPHQL
private const val DI_AUTH_HEADER_INTERCEPTOR = "DI_AUTH_HEADER_INTERCEPTOR"
const val DI_RETROFIT_CLIENT = "DI_RETROFIT_CLIENT"
const val DI_CONVERTER_GSON = "DI_CONVERTER_GSON"
const val DI_API_BASE_URL = "DI_API_BASE_URL"

val koinModuleList = module {

    //-DI VIEW MODEL BELOW HERE

    //-DI USECASE BELOW HERE


    //-DI REPOSITORY BELOW HERE


    //-DI API BELOW HERE

//    single(named(name = DI_AUTH_HEADER_INTERCEPTOR)) {
//        AuthHeaderInterceptor(
//            getApiTokenUseCase = get(named(name = DI_GRAPHQL_GET_TOKEN_USECASE)))
//    }

    single {
        BaseOkHttpClientBuilder().init()
    }
    single(named(name = DI_RETROFIT_CLIENT)) {
        BaseRetrofitBuilder(
            baseOkHttpClintBuilder = get(),
            defaultBaseUrl = get(named(name = DI_API_BASE_URL)),
            converterFactory = get(named( name = DI_CONVERTER_GSON))
        ).build()
    }

    single (named(name = DI_CONVERTER_GSON)){
        GsonConverterFactory.create()
    }
    single(named(name = DI_API_BASE_URL)) { BaseUrl(url = "link base") }

}

