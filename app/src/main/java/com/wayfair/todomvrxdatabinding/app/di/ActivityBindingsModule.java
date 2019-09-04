package com.wayfair.todomvrxdatabinding.app.di;

import com.wayfair.todomvrxdatabinding.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
interface ActivityBindingsModule {
    @ContributesAndroidInjector
    MainActivity mainActivityInjector();
}
