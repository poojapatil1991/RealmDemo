package com.example.realmdemo

import android.app.Application
import android.content.Context
import io.realm.Realm
import io.realm.RealmConfiguration

class RealmDemoApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        Realm.init(context)
        //val config = RealmConfiguration.Builder().build()
        //Realm.setDefaultConfiguration(config)

        val configuration =
            RealmConfiguration
                .Builder()
                .name("student.realm")
                .schemaVersion(1)
                .migration(RealmMigrations()).build()

        Realm.setDefaultConfiguration(configuration)
        Realm.getInstance(configuration)

    }

    companion object {
        lateinit var context: Context
    }

    override fun onTerminate() {
        Realm.getDefaultInstance().close()
        super.onTerminate()
    }
/*  If you want to copy data from iOS real database to android
    private fun importData() {
        copyBundledRealmFile(activity.getResources().openRawResource(rawRealmResource), dbaFullName)
        Realm.init(activity)
    }

    private fun copyBundledRealmFile(
        inputStream: InputStream,
        outFileName: String
    ): String? {
        try {
            val file = File(activity.getFilesDir(), outFileName)
            if (file.exists()) {
                return file.getAbsolutePath()
            }
            val outputStream = FileOutputStream(file)
            val buf = ByteArray(1024)
            var bytesRead: Int
            while (inputStream.read(buf).also({ bytesRead = it }) > 0) {
                outputStream.write(buf, 0, bytesRead)
            }
            outputStream.close()
            return file.getAbsolutePath()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }*/
}