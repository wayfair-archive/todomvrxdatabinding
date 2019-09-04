package com.wayfair.todomvrxdatabinding.app;

import com.wayfair.todomvrxdatabinding.app.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

public class App extends DaggerApplication {

    @Override
    protected AndroidInjector<App> applicationInjector() {
        return DaggerAppComponent.factory().create(this);
    }
}
