package com.wayfair.todomvrxdatabinding.app.di;

import com.wayfair.todomvrxdatabinding.PerApplication;
import com.wayfair.todomvrxdatabinding.RoomScope;
import com.wayfair.todomvrxdatabinding.app.App;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

// REMEMBER TO UPDATE TestAppComponent
@Component(
        modules = {
                ActivityBindingsModule.class,
                AndroidSupportInjectionModule.class,
                AppModule.class,
                FragmentBindingsModule.class,
                RoomModule.class,
        }
)
@PerApplication
@RoomScope
interface AppComponent extends AndroidInjector<App> {

    @Component.Factory
    interface Factory extends AndroidInjector.Factory<App> {
    }
}
