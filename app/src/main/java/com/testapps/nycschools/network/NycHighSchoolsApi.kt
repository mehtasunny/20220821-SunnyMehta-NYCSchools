package com.testapps.nycschools.network

import com.testapps.nycschools.network.model.NycSchool
import com.testapps.nycschools.network.model.SatScores
import retrofit2.http.GET
import retrofit2.Response

interface NycHighSchoolsApi {

    @GET("s3k6-pzi2.json")
    suspend fun getNycHighSchools(): Response<List<NycSchool>>

    @GET("f9bf-2cp4.json")
    suspend fun getNycHighSchoolsScores(): Response<List<SatScores>>
}