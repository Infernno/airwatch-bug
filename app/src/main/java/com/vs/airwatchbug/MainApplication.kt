package com.vs.airwatchbug

import android.app.Application
import android.content.Context
import android.content.Intent
import com.airwatch.app.AWSDKApplication
import com.airwatch.app.AWSDKApplicationDelegate

class MainApplication : Application(), AWSDKApplication by AWSDKApplicationDelegate() {

    override fun onCreate() {
        super.onCreate()
        onCreate(this)
    }

    override fun getSystemService(name: String): Any? {
        return getAWSystemService(name, super.getSystemService(name))
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        attachBaseContext(this)
    }

    // Application-specific overrides.
    override fun onPostCreate() {
    }

    override fun getMainActivityIntent(): Intent {
        return Intent(applicationContext, MainActivity::class.java)
    }

    override fun getMagCertificateEnable(): Boolean {
        return true
    }
}