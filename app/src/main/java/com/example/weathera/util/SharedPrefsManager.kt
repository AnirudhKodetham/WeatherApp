package com.example.weathera.util



import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefsManager @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs = context.getSharedPreferences("weather_prefs", Context.MODE_PRIVATE)

    fun saveLastCity(city: String) {
        prefs.edit().putString("last_city", city).apply()
    }

    fun getLastCity(): String? {
        return prefs.getString("last_city", null)
    }
}