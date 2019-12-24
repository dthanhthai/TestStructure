package vn.gogo.trip.di

import ascend.wefresh.core.data.api.BaseOkHttpClientBuilder
import vn.gogo.trip.data.remote.RetrofitBuilder
import ascend.wefresh.core.data.api.BaseUrl
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.converter.gson.GsonConverterFactory
import vn.gogo.trip.data.constant.RemoteConstant
import vn.gogo.trip.data.remote.ServiceRetrofitBuilder
import vn.gogo.trip.data.remote.service.TestApiService
import vn.gogo.trip.data.repositoty.google_video.GoogleVideoRepository
import vn.gogo.trip.data.repositoty.google_video.IGoogleVideoRepository
import vn.gogo.trip.domain.usecase.GetGoogleVideoListUseCase
import vn.gogo.trip.domain.usecase.IGetGoogleVideoListUseCase
import vn.gogo.trip.ui.main.MainActivityViewModel

//-DI NAME
private const val DI_AUTH_HEADER_INTERCEPTOR = "DI_AUTH_HEADER_INTERCEPTOR"
const val DI_RETROFIT_CLIENT = "DI_RETROFIT_CLIENT"
const val DI_CONVERTER_GSON = "DI_CONVERTER_GSON"
const val DI_API_BASE_URL = "DI_API_BASE_URL"
const val DI_TEST_GOOGLE_VIDEO_SERVICE = "DI_TEST_GOOGLE_VIDEO_SERVICE"

val koinModuleList = module {

    //-DI VIEW MODEL BELOW HERE

    viewModel { MainActivityViewModel(getGoogleVideoUseCase = get()) }

    //-DI USECASE BELOW HERE

    factory<IGetGoogleVideoListUseCase> { GetGoogleVideoListUseCase(repository = get()) }

    //-DI REPOSITORY BELOW HERE

    factory<IGoogleVideoRepository> {
        GoogleVideoRepository(service = get(named(DI_TEST_GOOGLE_VIDEO_SERVICE)))
    }

    //-DI API BELOW HERE

//    single(named(name = DI_AUTH_HEADER_INTERCEPTOR)) {
//        AuthHeaderInterceptor(
//            getApiTokenUseCase = get(named(name = DI_GRAPHQL_GET_TOKEN_USECASE)))
//    }

    single(named(DI_CONVERTER_GSON)) {
        GsonConverterFactory.create()
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

    single(named(DI_TEST_GOOGLE_VIDEO_SERVICE)) {
        ServiceRetrofitBuilder(
            baseOkHttpClintBuilder = get(),
            defaultBaseUrl = get(named(name = DI_API_BASE_URL)),
            converterFactory = get(named(name = DI_CONVERTER_GSON))
        ).build<TestApiService>()
    }

}

