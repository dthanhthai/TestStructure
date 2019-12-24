package vn.gogo.trip.domain.usecase

import vn.gogo.trip.data.repositoty.google_video.IGoogleVideoRepository
import vn.gogo.trip.data.response.google_video.GoogleVideoResponse
import vn.gogo.trip.domain.base.UseCaseResult
import java.lang.Exception

interface IGetGoogleVideoListUseCase {
    suspend fun execute(): UseCaseResult<GoogleVideoResponse>
}

class GetGoogleVideoListUseCase(private val repository: IGoogleVideoRepository) :
    IGetGoogleVideoListUseCase {
    override suspend fun execute(): UseCaseResult<GoogleVideoResponse> {
        return try {
            val responseData = repository.getGoogleVideo()
            if (responseData.googlevideoList.isNotEmpty()) {
                UseCaseResult.Success(responseData)
            } else {
                UseCaseResult.Error(Throwable("Error: data is empty!"))
            }
        } catch (e: Exception) {
            UseCaseResult.Error(Throwable(e.message))
        }
    }

}