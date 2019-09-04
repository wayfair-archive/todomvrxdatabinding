package com.wayfair.todomvrxdatabinding.data.db

import com.wayfair.todomvrxdatabinding.data.Task
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

class TestTaskStatisticsDao : TaskStatisticsDao {

    private val tasksMap = hashMapOf<Int, Task>()

    private val subject: Subject<HashMap<Int, Task>> = BehaviorSubject.create()

    internal fun saveTask(todo: Task) = Completable.fromAction {
        tasksMap[todo.id] = todo
        subject.onNext(tasksMap)
    }

    internal fun deleteTasks() = Completable.fromAction {
        tasksMap.clear()
        subject.onNext(tasksMap)
    }

    override fun getIncompleteTasksCount(): Observable<Int> =
        subject.map { tasks ->
            tasks.values
                .filter { !it.complete }
                .count()
        }

    override fun getCompleteTasksCount(): Observable<Int> =
        subject.map { tasks ->
            tasks.values
                .filter { it.complete }
                .count()
        }

    override fun getAllTasksCount(): Observable<Int> =
        subject.map { it.values.count() }
}