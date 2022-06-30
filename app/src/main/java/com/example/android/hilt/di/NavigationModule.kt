package com.example.android.hilt.di

import androidx.fragment.app.FragmentActivity
import com.example.android.hilt.navigator.AppNavigator
import com.example.android.hilt.navigator.AppNavigatorImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent

@InstallIn(ActivityComponent::class)
@Module
abstract class NavigationModule {

    @Binds
    abstract fun provideAppNavigator(appNavigatorImpl: AppNavigatorImpl) : AppNavigator

//    @Provides
//    fun provideAppNavigator(activity: FragmentActivity) : AppNavigator {
//        return AppNavigatorImpl(activity)
//    }
}