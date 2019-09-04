package com.wayfair.todomvrxdatabinding.tasklist

import android.annotation.SuppressLint
import com.wayfair.todomvrxdatabinding.data.Task
import com.wayfair.todomvrxdatabinding.data.db.TasksDao
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.annotations.CheckReturnValue
import java.util.concurrent.TimeUnit
import javax.inject.Inject

internal class TaskListRepository @Inject constructor(
    private val dao: TasksDao
) {

    @SuppressLint("RxJava2DefaultScheduler")
    @CheckReturnValue
    fun observeTasks(delay: Boolean = false): Observable<List<Task>> =
        Completable.complete()
            .delay(if (delay) RETRIEVE_TASKS_DELAY else 0, TimeUnit.MILLISECONDS)
            .andThen(dao.getTasks())

    @CheckReturnValue
    fun clearTasks(): Completable = dao.deleteAll()

    @CheckReturnValue
    fun setComplete(id: Int, isComplete: Boolean) = dao.setCompleteOnTask(id, isComplete)

    companion object {
        private const val RETRIEVE_TASKS_DELAY = 2000L
    }
}
