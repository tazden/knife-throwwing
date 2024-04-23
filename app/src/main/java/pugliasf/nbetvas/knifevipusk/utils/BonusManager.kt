package pugliasf.nbetvas.knifevipusk.utils

import android.content.Context
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object BonusManager {
    private const val PREF_NAME = "MyAppPreferences"
    private const val LAST_LOGIN_DATE = "last_login_date"
    private const val DAYS_COUNT = "days_count"

    fun checkAndProvideBonus(context: Context) {
        val preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        // Получаем дату последнего входа из SharedPreferences
        val lastLoginDate = preferences.getString(LAST_LOGIN_DATE, "")

        // Получаем текущую дату
        val currentDate = getCurrentDate()

        // Получаем количество дней, в течение которых пользователь заходил в приложение
        var daysCount = preferences.getInt(DAYS_COUNT, 0)

        // Сравниваем даты
        if (lastLoginDate != currentDate) {
            // Если это новый день, увеличиваем количество дней на 1
            daysCount++
            // Предоставляем бонус и обновляем дату последнего входа и количество дней
            preferences.edit()
                .putString(LAST_LOGIN_DATE, currentDate)
                .putInt(DAYS_COUNT, daysCount)
                .apply()
        }
    }

    fun getDaysCount(context: Context): Int {
        val preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        return preferences.getInt(DAYS_COUNT, 0)
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        return sdf.format(Date())
    }


}