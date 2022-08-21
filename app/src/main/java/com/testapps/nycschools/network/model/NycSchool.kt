package com.testapps.nycschools.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NycSchool(
    @Json(name = "dbn")
    val dbn: String,
    @Json(name = "school_name")
    val schoolName: String?,
    @Json(name = "overview_paragraph")
    val overviewParagraph: String?,
    @Json(name = "neighborhood")
    val neighborhood: String,
    @Json(name = "total_students")
    val totalStudents: String?,
    @Json(name = "city")
    val city: String?,
    @Json(name = "zip")
    val zip: String?,
    @Json(name = "primary_address_line_1")
    val address1: String?,
    @Json(name = "state_code")
    val state: String?
)

@JsonClass(generateAdapter = true)
data class SatScores(
    @Json(name = "dbn")
    val dbn: String,
    @Json(name = "sat_critical_reading_avg_score")
    val reading: String,
    @Json(name = "sat_math_avg_score")
    val math: String,
    @Json(name = "sat_writing_avg_score")
    val writing: String,
    @Json(name = "num_of_sat_test_takers")
    val avgTestTakers: String,
)