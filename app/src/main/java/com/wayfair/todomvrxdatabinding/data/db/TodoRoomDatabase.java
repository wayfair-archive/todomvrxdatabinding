package com.wayfair.todomvrxdatabinding.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import com.wayfair.todomvrxdatabinding.data.Task;

/**
 * The Room Database that contains the Task table.
 */
@Database(entities = Task.class, version = 1)
public abstract class TodoRoomDatabase extends RoomDatabase {

    abstract public TasksDao taskDao();

    abstract public TaskStatisticsDao taskStatisticsDao();
}
