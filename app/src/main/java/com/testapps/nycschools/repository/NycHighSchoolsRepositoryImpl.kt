package com.testapps.nycschools.repository

import com.testapps.nycschools.core.NetworkResult
import com.testapps.nycschools.core.safeApiCall
import com.testapps.nycschools.network.NycHighSchoolsApi
import com.testapps.nycschools.network.model.NycSchool
import com.testapps.nycschools.network.model.SatScores
import javax.inject.Inject

class NycHighSchoolsRepositoryImpl @Inject constructor(
    private val NycHighSchoolsApi: NycHighSchoolsApi
) : NycHighSchoolsRepository {

    override suspend fun fetchNycHighSchoolsList(): NetworkResult<List<NycSchool>> {
        return safeApiCall(
            retrofitCall =  { NycHighSchoolsApi.getNycHighSchools() },
            errorMessage = "Error occurred while fetching NYC High Schools"
        )
    }

    override suspend fun fetchNycHighSchoolsSATScores(): NetworkResult<List<SatScores>> {
        return safeApiCall(
            retrofitCall =  { NycHighSchoolsApi.getNycHighSchoolsScores() },
            errorMessage = "Error occurred while fetching NYC High Schools SAT Scores"
        )
    }
}