package com.bindlish.omdbmovies.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bindlish.omdbmovies.viewmodel.MoviesViewModel
import com.bindlish.omdbmovies.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun provideViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MoviesViewModel::class)
    protected abstract fun bindMoviesViewModel(viewModel: MoviesViewModel): ViewModel
}