package com.wayfair.todomvrxdatabinding.test

import com.wayfair.todomvrxdatabinding.data.Task
import com.wayfair.todomvrxdatabinding.data.db.TasksDao
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.Subject

class TestTasksDao : TasksDao {

    private val tasksMap = hashMapOf<Int, Task>()

    private val subject: Subject<HashMap<Int, Task>> = BehaviorSubject.create()

    override fun getTasks(): Observable<List<Task>> = subject.map { it.values.toList() }

    override fun getTask(id: Int): Observable<Task> =
        subject.filter { it.containsKey(id) }
            .map { it[id] }

    override fun upsertTask(todo: Task) = Completable.fromAction {
        tasksMap[todo.id] = todo
        subject.onNext(tasksMap)
    }

    override fun deleteTask(id: Int) = Completable.fromAction {
        tasksMap.remove(id)
        subject.onNext(tasksMap)
    }

    override fun deleteAll() = Completable.fromAction {
        tasksMap.clear()
        subject.onNext(tasksMap)
    }

    override fun setCompleteOnTask(id: Int, isComplete: Boolean) = Completable.fromAction {
        val task = tasksMap[id]
        tasksMap[id] = task!!.copy(complete = isComplete)
        subject.onNext(tasksMap)
    }
}
