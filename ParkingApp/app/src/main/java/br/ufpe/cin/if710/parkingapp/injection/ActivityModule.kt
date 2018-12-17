package br.ufpe.cin.if710.parkingapp.injection

import br.ufpe.cin.if710.parkingapp.ui.MainActivity
import br.ufpe.cin.if710.parkingapp.ui.ParkingListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * TODO:
 * - Implement 
 *
 * All activities intended to use Dagger2 @Inject should be listed here.
 *
 * Example:
 *
 * @ContributesAndroidInjector
 * internal abstract fun contributeFooActivity(): FooActivity
 *
 */
@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun contributeParkingListActivity(): ParkingListActivity

}