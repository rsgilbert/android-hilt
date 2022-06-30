package com.example.android.hilt.di

import androidx.fragment.app.FragmentActivity
import com.example.android.hilt.navigator.AppNavigator
import com.example.android.hilt.navigator.AppNavigatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@InstallIn(ActivityComponent::class)
@Module
class AppNavigatorModule {

    @Provides
    fun provideAppNavigator(activity: FragmentActivity) : AppNavigator {
        return AppNavigatorImpl(activity)
    }
}