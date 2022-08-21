package com.testapps.nycschools.domain

import com.testapps.nycschools.core.NetworkResult
import com.testapps.nycschools.network.model.NycSchool
import com.testapps.nycschools.repository.NycHighSchoolsRepository
import javax.inject.Inject

class NycHighSchoolsListUseCase @Inject constructor(
    private val repository: NycHighSchoolsRepository
) {
    suspend fun invoke(): NetworkResult<List<NycSchool>> {
        return repository.fetchNycHighSchoolsList()
    }
}