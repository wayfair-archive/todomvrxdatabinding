package com.wayfair.todomvrxdatabinding.taskdetail

import androidx.fragment.app.Fragment
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Binds
import dagger.Module

@AssistedModule
@Module(includes = [AssistedInject_TaskDetailFragmentModule::class])
abstract class TaskDetailFragmentModule {

    @Binds
    abstract fun bindFragment(fragment: TaskDetailFragment): Fragment
}
