package br.com.rangeldev.servicecontroller.application

import android.app.Application
import br.com.rangeldev.servicecontroller.helper.HelperDB

class OSApplication: Application() {

    var helperDB: HelperDB? = null
        private set

    companion object {
        lateinit var instance:OSApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        helperDB = HelperDB(this)
    }
}