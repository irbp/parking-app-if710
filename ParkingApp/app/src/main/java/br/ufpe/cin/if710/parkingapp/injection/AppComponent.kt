package br.ufpe.cin.if710.parkingapp.injection

import android.app.Application
import br.ufpe.cin.if710.parkingapp.ParkingApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 *
 * A component is used to initialize modules.
 * Dagger 2 will auto-generate DaggerAppComponent which is used for initialization at Application.
 *
 */
@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, ActivityModule::class])
interface AppComponent {

    fun inject(parkingApp: ParkingApp)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }
}