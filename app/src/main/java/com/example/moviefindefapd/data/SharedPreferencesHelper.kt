import android.content.Context

object SharedPreferencesHelper {

    private const val PREFERENCES_FILE_KEY = "myPrefs"
    private const val STRING_KEY = "myStringKey"

    fun saveStringToPreferences(context: Context, value: String) {
        val sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(STRING_KEY, value)
        editor.apply()
    }

    fun getStringFromPreferences(context: Context): String? {
        val sharedPreferences = context.getSharedPreferences(PREFERENCES_FILE_KEY, Context.MODE_PRIVATE)
        return sharedPreferences.getString(STRING_KEY, null)
    }
}
