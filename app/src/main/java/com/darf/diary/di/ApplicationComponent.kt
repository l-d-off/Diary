package com.darf.diary.di

import android.app.Application
import com.darf.diary.presentation.EditEventFragment
import com.darf.diary.presentation.DiaryFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    fun inject(fragment: EditEventFragment)

    fun inject(fragment: DiaryFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance
            application: Application
        ): ApplicationComponent
    }
}