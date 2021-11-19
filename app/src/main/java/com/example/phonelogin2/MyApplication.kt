package com.example.phonelogin2

import android.app.Application
import cn.bmob.v3.Bmob

/**
 *@Description
 *@Author PC
 *@QQ 1578684787
 */
class MyApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        //第一：默认初始化
        Bmob.initialize(this, "56ad195da375b130d0e9b054a9e550d0")
    }
}