package com.wayfair.todomvrxdatabinding.app.di;

import android.content.res.Resources;

import com.wayfair.todomvrxdatabinding.app.App;

import dagger.Module;
import dagger.Provides;

@Module
interface AppModule {

    @Provides
    static Resources resources(App app) {
        return app.getResources();
    }
}
