package vn.gogo.trip.ui.main

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch
import vn.gogo.trip.data.response.google_video.GoogleVideoResponse
import vn.gogo.trip.domain.base.ResultWrapper
import vn.gogo.trip.domain.base.UseCaseResult
import vn.gogo.trip.domain.usecase.IGetGoogleVideoListUseCase
import vn.gogo.trip.ui.base.BaseViewModel

class MainActivityViewModel(private val getGoogleVideoUseCase: IGetGoogleVideoListUseCase) :
    BaseViewModel() {
    var googleVideoList = MutableLiveData<GoogleVideoResponse>()

    fun loadGoogleVideoList() {
        launch {
            val result = getGoogleVideoUseCase.execute()

            if (result is ResultWrapper.Success) {
                googleVideoList.value = result.data
            }
        }
    }
}