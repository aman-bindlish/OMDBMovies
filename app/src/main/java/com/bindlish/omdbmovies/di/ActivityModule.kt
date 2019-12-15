package com.bindlish.omdbmovies.di

import com.bindlish.omdbmovies.ui.MoviesDetailActivity
import com.bindlish.omdbmovies.ui.MoviesDetailFragment
import com.bindlish.omdbmovies.ui.MoviesListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributeMoviesListActivity(): MoviesListActivity

    @ContributesAndroidInjector
    internal abstract fun contributeMoviesDetailActivity(): MoviesDetailActivity

    @ContributesAndroidInjector
    internal abstract fun contributeMoviesDetailFragment(): MoviesDetailFragment
}