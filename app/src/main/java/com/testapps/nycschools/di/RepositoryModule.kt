package com.testapps.nycschools.di

import com.testapps.nycschools.repository.NycHighSchoolsRepository
import com.testapps.nycschools.repository.NycHighSchoolsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindNycHighSchoolRepository(impl: NycHighSchoolsRepositoryImpl) : NycHighSchoolsRepository
}