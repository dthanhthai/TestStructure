package ascend.wefresh.core.data.api
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import vn.gogo.trip.BuildConfig
import java.util.concurrent.TimeUnit

class BaseOkHttpClientBuilder() {

    fun init(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
                .readTimeout(HTTP_TIMEOUT_SECOND, TimeUnit.SECONDS)
                .connectTimeout(HTTP_TIMEOUT_SECOND, TimeUnit.SECONDS)
//                .addInterceptor(authHeaderInterceptor)
                .apply {
                    if (BuildConfig.DEBUG) {
                        val logging = HttpLoggingInterceptor()
                        logging.level = HttpLoggingInterceptor.Level.BODY
                        addInterceptor(logging)
                    }
                }
    }
}