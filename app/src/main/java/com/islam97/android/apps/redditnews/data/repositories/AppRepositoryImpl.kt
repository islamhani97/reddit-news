package com.islam97.android.apps.redditnews.data.repositories

import com.islam97.android.apps.redditnews.domain.repositories.AppRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl
@Inject constructor() : BaseRepository(), AppRepository