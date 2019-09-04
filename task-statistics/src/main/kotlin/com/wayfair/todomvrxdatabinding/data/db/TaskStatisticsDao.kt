package com.wayfair.todomvrxdatabinding.data.db

import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Observable
import io.reactivex.annotations.CheckReturnValue

@Dao
interface TaskStatisticsDao {

    @CheckReturnValue
    @Query("SELECT count(*) FROM Tasks WHERE complete = 0")
    fun getIncompleteTasksCount(): Observable<Int>

    @CheckReturnValue
    @Query("SELECT count(*) FROM Tasks WHERE complete = 1")
    fun getCompleteTasksCount(): Observable<Int>

    @CheckReturnValue
    @Query("SELECT count(*) FROM Tasks")
    fun getAllTasksCount(): Observable<Int>
}