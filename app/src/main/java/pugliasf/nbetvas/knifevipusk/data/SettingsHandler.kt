package pugliasf.nbetvas.knifevipusk.data

import android.content.Context

class SettingsHandler private constructor(){
    var theme = 0 //тема
    var sound = true //звук

    fun getSettings(context: Context) { //Настройки загружаются единожды в TasksActivity.onCreate()
        val settings = context.getSharedPreferences(STORAGE_NAME, Context.MODE_PRIVATE)
        theme = settings.getInt(THEME, THEME_DAY)
        sound = settings.getBoolean(SOUND, true)
    }

    companion object {
        val instance = SettingsHandler()

        //Storage
        const val STORAGE_NAME = "settings"
        const val THEME = "theme"
        const val SOUND = "sound"

        //Значения
        const val THEME_NIGHT = 1
        const val THEME_DAY = 2
    }

}