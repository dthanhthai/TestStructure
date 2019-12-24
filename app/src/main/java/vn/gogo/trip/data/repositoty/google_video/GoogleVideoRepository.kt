package vn.gogo.trip.data.repositoty.google_video

import vn.gogo.trip.data.remote.service.TestApiService
import vn.gogo.trip.data.response.google_video.GoogleVideoResponse

interface IGoogleVideoRepository {
    suspend fun getGoogleVideo(): GoogleVideoResponse
}

class GoogleVideoRepository(private val service: TestApiService) :
    IGoogleVideoRepository {
    override suspend fun getGoogleVideo(): GoogleVideoResponse {
       return service.getTestVideos()
    }

}

