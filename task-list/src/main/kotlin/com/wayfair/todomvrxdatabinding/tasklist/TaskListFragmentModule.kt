package com.wayfair.todomvrxdatabinding.tasklist

import androidx.fragment.app.Fragment
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Binds
import dagger.Module

@AssistedModule
@Module(includes = [AssistedInject_TaskListFragmentModule::class])
abstract class TaskListFragmentModule {

    @Binds
    abstract fun bindFragment(fragment: TaskListFragment): Fragment
}
