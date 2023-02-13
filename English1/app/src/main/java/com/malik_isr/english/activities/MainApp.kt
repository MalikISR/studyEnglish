package com.malik_isr.english.activities

import android.app.Application
import com.malik_isr.english.db.MainDataBase

class MainApp:Application() {
    val database by lazy { MainDataBase.getDataBase(this) }
}