package com.exercise.repos.business.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPrefs(context: Context) {

    private var sharedPreferences: SharedPreferences? = null


    init {
        sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    }

    companion object {
        private var prefs: SharedPrefs? = null
        fun getPrefs(context: Context): SharedPrefs {
            synchronized(this) {
                if (prefs == null) {
                    prefs = SharedPrefs(context)
                }
            }
            return prefs!!

        }
    }

    fun setNightMode(value: Boolean) {
        sharedPreferences?.edit()?.putBoolean("night_mode", value)?.apply()
    }

    fun isNightMode(defValue: Boolean): Boolean {
        return sharedPreferences?.getBoolean("night_mode", defValue) ?: defValue
    }

    fun setValue(key: String, value: Long){
        sharedPreferences?.edit()?.putLong(key, value)?.apply()
    }

    fun getValue(key: String, defValue: Long) : Long{
        return sharedPreferences?.getLong(key, defValue) ?: defValue
    }

    fun isFirstTime(): Boolean {
        val isFirst =  sharedPreferences?.getBoolean("is_first_time", true) ?: true
        sharedPreferences?.edit()?.putBoolean("is_first_time", false)?.apply()
        return isFirst
    }

}