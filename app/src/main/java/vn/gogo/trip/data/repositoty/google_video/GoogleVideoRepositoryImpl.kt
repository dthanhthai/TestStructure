package vn.gogo.trip.data.repositoty.google_video

import vn.gogo.trip.data.remote.service.TestApiService
import vn.gogo.trip.data.response.google_video.GoogleVideoResponse

interface GoogleVideoRepository {
    suspend fun getGoogleVideo(): GoogleVideoResponse
}

class GoogleVideoRepositoryImpl(private val service: TestApiService) :
    GoogleVideoRepository {
    override suspend fun getGoogleVideo(): GoogleVideoResponse {
       return service.getTestVideos()
    }

}

