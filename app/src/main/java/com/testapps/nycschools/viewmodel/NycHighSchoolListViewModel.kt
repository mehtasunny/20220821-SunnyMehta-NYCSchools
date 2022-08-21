package com.testapps.nycschools.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testapps.nycschools.core.NetworkResult
import com.testapps.nycschools.domain.NycHighSchoolsListUseCase
import com.testapps.nycschools.domain.NycHighSchoolsSATScoreUseCase
import com.testapps.nycschools.network.model.NycSchool
import com.testapps.nycschools.network.model.SatScores
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NycHighSchoolListViewModel @Inject constructor(
    private val nycHighSchoolsListUseCase: NycHighSchoolsListUseCase,
    private val nycHighSchoolsSATScoreUseCase: NycHighSchoolsSATScoreUseCase
) : ViewModel() {

    private val schoolsListResponse = MutableLiveData<NycSchoolsData>()
    val schoolsListLiveData: MutableLiveData<NycSchoolsData> get() = schoolsListResponse

    fun loadNycHighSchoolsList() {
        viewModelScope.launch(Dispatchers.IO) {
            val schoolsListDeferred = async { nycHighSchoolsListUseCase.invoke() }
            val schoolsSATScoresDeferred = async { nycHighSchoolsSATScoreUseCase.invoke() }
            schoolsListResponse.postValue(
                NycSchoolsData(
                    schoolList = schoolsListDeferred.await(),
                    schoolSATScores = schoolsSATScoresDeferred.await()
                )
            )
        }
    }

    data class NycSchoolsData(
        val schoolList: NetworkResult<List<NycSchool>>,
        val schoolSATScores: NetworkResult<List<SatScores>>
    )
}