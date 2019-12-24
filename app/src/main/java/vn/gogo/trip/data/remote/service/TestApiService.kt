package vn.gogo.trip.data.remote.service

import retrofit2.http.GET
import vn.gogo.trip.data.response.google_video.GoogleVideoResponse

interface TestApiService {
    @GET("android-tv/android_tv_videos_new.json")
    suspend fun getTestVideos(): GoogleVideoResponse
}