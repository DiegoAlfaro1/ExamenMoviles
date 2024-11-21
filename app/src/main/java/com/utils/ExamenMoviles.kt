package com.utils

import android.app.Application
import com.data.network.NetworkModuleDI

class ExamenMoviles : Application() {
    override fun onCreate() {
        super.onCreate()
        NetworkModuleDI.initializeParse(this)
    }
}