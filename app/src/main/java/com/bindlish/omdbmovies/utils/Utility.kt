package com.bindlish.omdbmovies.utils

import androidx.appcompat.widget.SearchView
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.*

fun observableFromSearch(view : SearchView) : Observable<String> {
    val subject : PublishSubject<String> = PublishSubject.create()
    view.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
        override fun onQueryTextChange(newText: String?): Boolean {
            subject.onNext(newText)
            return true
        }

        override fun onQueryTextSubmit(query: String?): Boolean {
            subject.onComplete()
            view.clearFocus()
            return true
        }
    })
    return subject;
}