package com.wayfair.todomvrxdatabinding.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.wayfair.todomvrxdatabinding.data.Task
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.annotations.CheckReturnValue

@Dao
interface TasksDao {

    @CheckReturnValue
    @Query("SELECT * FROM Tasks")
    fun getTasks(): Observable<List<Task>>

    @CheckReturnValue
    @Query("SELECT * FROM Tasks WHERE id = :id")
    fun getTask(id: Int): Observable<Task>

    @CheckReturnValue
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsertTask(todo: Task): Completable

    @CheckReturnValue
    @Query("DELETE FROM Tasks WHERE id = :id")
    fun deleteTask(id: Int): Completable

    @CheckReturnValue
    @Query("DELETE FROM Tasks")
    fun deleteAll(): Completable

    @CheckReturnValue
    @Query("UPDATE TASKS SET complete = :isComplete WHERE id = :id")
    fun setCompleteOnTask(id: Int, isComplete: Boolean): Completable
}
