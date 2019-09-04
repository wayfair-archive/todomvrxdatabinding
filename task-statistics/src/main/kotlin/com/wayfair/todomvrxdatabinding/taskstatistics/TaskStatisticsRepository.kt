package com.wayfair.todomvrxdatabinding.taskstatistics

import com.wayfair.todomvrxdatabinding.data.db.TaskStatisticsDao
import io.reactivex.Observable
import io.reactivex.annotations.CheckReturnValue
import javax.inject.Inject

class TaskStatisticsRepository @Inject constructor(
    private val dao: TaskStatisticsDao
) {

    @CheckReturnValue
    fun observeIncompleteTasksCount(): Observable<Int> = dao.getIncompleteTasksCount()

    @CheckReturnValue
    fun observeCompleteTasksCount(): Observable<Int> = dao.getCompleteTasksCount()

    @CheckReturnValue
    fun observeAllTasksCount(): Observable<Int> = dao.getAllTasksCount()
}
