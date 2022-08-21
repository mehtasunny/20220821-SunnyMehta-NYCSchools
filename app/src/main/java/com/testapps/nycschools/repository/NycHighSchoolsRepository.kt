package com.testapps.nycschools.repository

import com.testapps.nycschools.core.NetworkResult
import com.testapps.nycschools.network.model.NycSchool
import com.testapps.nycschools.network.model.SatScores

interface NycHighSchoolsRepository {

    /**
     * Fetches List of NYC HighSchools
     */
    suspend fun fetchNycHighSchoolsList(): NetworkResult<List<NycSchool>>

    /**
     * Fetches SAT scores for NYC HighSchools
     */
    suspend fun fetchNycHighSchoolsSATScores(): NetworkResult<List<SatScores>>
}