package tech.leson.yonstore

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidFileProperties
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.KoinExperimentalAPI
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import tech.leson.yonstore.di.appModule
import tech.leson.yonstore.di.mainModule

class YonStoreApp : Application() {
    @KoinExperimentalAPI
    override fun onCreate() {
        super.onCreate()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = getString(R.string.default_notification_channel_id)
            val channelName = getString(R.string.default_notification_channel_name)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(NotificationChannel(channelId,
                channelName, NotificationManager.IMPORTANCE_LOW))
        }

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@YonStoreApp)
            androidFileProperties()
            fragmentFactory()
            loadKoinModules(listOf(appModule, mainModule))
        }

    }
}
