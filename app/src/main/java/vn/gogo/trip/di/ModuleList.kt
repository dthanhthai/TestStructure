package vn.gogo.trip.di

import ascend.wefresh.core.data.api.BaseOkHttpClientBuilder
import vn.gogo.trip.data.remote.RetrofitBuilder
import ascend.wefresh.core.data.api.BaseUrl
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import vn.gogo.trip.data.constant.RemoteConstant
import vn.gogo.trip.data.remote.ServiceRetrofitBuilder
import vn.gogo.trip.data.remote.service.TestApiService
import vn.gogo.trip.data.repositoty.google_video.GoogleVideoRepositoryImpl
import vn.gogo.trip.data.repositoty.google_video.GoogleVideoRepository
import vn.gogo.trip.domain.usecase.GetGoogleVideoListUseCaseImpl
import vn.gogo.trip.domain.usecase.GetGoogleVideoListUseCase
import vn.gogo.trip.ui.main.MainActivityViewModel

//-DI NAME
private const val DI_AUTH_HEADER_INTERCEPTOR = "DI_AUTH_HEADER_INTERCEPTOR"
const val DI_RETROFIT_CLIENT = "DI_RETROFIT_CLIENT"
const val DI_CONVERTER_GSON = "DI_CONVERTER_GSON"
const val DI_CONVERTER_MOSHI = "DI_CONVERTER_MOSHI"
const val DI_API_BASE_URL = "DI_API_BASE_URL"

val koinModuleList = module {

    //-DI VIEW MODEL BELOW HERE

    viewModel { MainActivityViewModel(getGoogleVideoUseCase = get()) }

    //-DI USECASE BELOW HERE

    factory<GetGoogleVideoListUseCase> { GetGoogleVideoListUseCaseImpl(repository = get()) }

    //-DI REPOSITORY BELOW HERE

    factory<GoogleVideoRepository> {
        GoogleVideoRepositoryImpl(service = get())
    }

    //-DI API BELOW HERE

//    single(named(name = DI_AUTH_HEADER_INTERCEPTOR)) {
//        AuthHeaderInterceptor(
//            getApiTokenUseCase = get(named(name = DI_GRAPHQL_GET_TOKEN_USECASE)))
//    }

    single(named(DI_CONVERTER_GSON)) {
        GsonConverterFactory.create()
    }

    single(named(DI_CONVERTER_MOSHI)) {
        MoshiConverterFactory.create()
    }

    single(named(DI_API_BASE_URL)) { BaseUrl(url = RemoteConstant.baseUrl) }

    single {
        BaseOkHttpClientBuilder().init()
    }
    single(named(DI_RETROFIT_CLIENT)) {
        RetrofitBuilder(
            baseOkHttpClintBuilder = get(),
            defaultBaseUrl = get(named(name = DI_API_BASE_URL)),
            converterFactory = get(named(name = DI_CONVERTER_GSON))
        ).build()
    }

    single {
        ServiceRetrofitBuilder(
            baseOkHttpClintBuilder = get(),
            defaultBaseUrl = get(named(name = DI_API_BASE_URL)),
            converterFactory = get(named(name = DI_CONVERTER_MOSHI))
        ).build<TestApiService>()
    }

}

