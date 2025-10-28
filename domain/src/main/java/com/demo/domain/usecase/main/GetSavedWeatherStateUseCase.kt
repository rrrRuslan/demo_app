package com.demo.domain.usecase.main

import com.demo.domain.entity.repository.HomeRepository
import javax.inject.Inject

class GetSavedWeatherStateUseCase @Inject constructor(
    private val homeRepository: HomeRepository
) {

    suspend operator fun invoke() = homeRepository.getMainState()

}