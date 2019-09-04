package com.wayfair.todomvrxdatabinding.app.di;

import com.wayfair.todomvrxdatabinding.RoomScope;
import com.wayfair.todomvrxdatabinding.app.App;
import com.wayfair.todomvrxdatabinding.data.db.TaskStatisticsDao;
import com.wayfair.todomvrxdatabinding.data.db.TasksDao;
import com.wayfair.todomvrxdatabinding.data.db.TodoRoomDatabase;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

@Module
interface RoomModule {

    @Provides
    @RoomScope
    static TodoRoomDatabase providesRoomDatabase(App app) {
        return Room.databaseBuilder(app, TodoRoomDatabase.class, "Tasks.db").build();
    }

    @Provides
    static TasksDao providesTasksDao(TodoRoomDatabase todoRoomDatabase) {
        return todoRoomDatabase.taskDao();
    }

    @Provides
    static TaskStatisticsDao providesTasksStatisticsDao(TodoRoomDatabase todoRoomDatabase) {
        return todoRoomDatabase.taskStatisticsDao();
    }
}
