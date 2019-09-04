package com.wayfair.todomvrxdatabinding.taskstatistics

import androidx.fragment.app.Fragment
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Binds
import dagger.Module

@AssistedModule
@Module(includes = [AssistedInject_TaskStatisticsFragmentModule::class])
abstract class TaskStatisticsFragmentModule {

    @Binds
    abstract fun bindFragment(fragment: TaskStatisticsFragment): Fragment
}
