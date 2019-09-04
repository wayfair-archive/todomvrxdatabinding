package com.wayfair.todomvrxdatabinding.taskcommon

import com.wayfair.todomvrxdatabinding.data.Task
import com.wayfair.todomvrxdatabinding.data.db.TasksDao
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.annotations.CheckReturnValue
import javax.inject.Inject

class TaskRepository @Inject constructor(
    private val dao: TasksDao
) {

    @CheckReturnValue
    fun observeTask(id: Int): Observable<Task> = dao.getTask(id)

    @CheckReturnValue
    fun saveTask(task: Task): Completable = dao.upsertTask(task)

    @CheckReturnValue
    fun deleteTask(id: Int): Completable = dao.deleteTask(id)
}
