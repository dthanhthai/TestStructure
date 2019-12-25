package vn.gogo.trip.domain.usecase

import vn.gogo.trip.data.repositoty.google_video.GoogleVideoRepository
import vn.gogo.trip.data.response.google_video.GoogleVideoResponse
import vn.gogo.trip.domain.base.ResultWrapper
import vn.gogo.trip.domain.base.safeApiCall

interface GetGoogleVideoListUseCase {
    suspend fun execute(): ResultWrapper<GoogleVideoResponse>
}

class GetGoogleVideoListUseCaseImpl(private val repository: GoogleVideoRepository) :
    GetGoogleVideoListUseCase {
    override suspend fun execute(): ResultWrapper<GoogleVideoResponse> {
        return safeApiCall { repository.getGoogleVideo() }
//        return try {
//            val responseData = repository.getGoogleVideo()
//            if (responseData.googlevideoList.isNotEmpty()) {
//                UseCaseResult.Success(responseData)
//            } else {
//                UseCaseResult.Error(Throwable("Error: data is empty!"))
//            }
//        } catch (e: Exception) {
//            UseCaseResult.Error(Throwable(e.message))
//        }
    }

}