package com.wayfair.todomvrxdatabinding.taskaddedit

import androidx.fragment.app.Fragment
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Binds
import dagger.Module

@AssistedModule
@Module(includes = [AssistedInject_TaskAddEditFragmentModule::class])
abstract class TaskAddEditFragmentModule {

    @Binds
    abstract fun bindFragment(fragment: TaskAddEditFragment): Fragment
}
