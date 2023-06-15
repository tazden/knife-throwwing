package xyz.genscode.dagger

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import xyz.genscode.dagger.data.SettingsHandler
import xyz.genscode.dagger.databinding.ActivityMainMenuBinding


class MainMenuActivity : AppCompatActivity() {
    lateinit var b : ActivityMainMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        b = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(b.root)

        SettingsHandler.instance.getSettings(this)
        updateUiTheme()
        updateUiSound()
        changeTheme(-1)

        b.btStart.setOnClickListener {
            startGame()
        }
        b.btSettings.setOnClickListener {
            showSettings()
        }

        if(intent.hasExtra("url")){
            val url = intent.getStringExtra("url")
            if(!url.equals("")){
                b.webView.visibility = View.VISIBLE
                b.webView.webViewClient = WebViewClient()
                b.webView.loadUrl(url.toString())
            }
        }
    }

    fun showGui(){
        b.llBtns.visibility = View.VISIBLE
        b.tvLogo.visibility = View.VISIBLE
        b.llBlack.visibility = View.INVISIBLE
    }

    fun hideGui(){
        b.llBtns.visibility = View.INVISIBLE
        b.tvLogo.visibility = View.INVISIBLE
        b.llBlack.visibility = View.VISIBLE
    }

    @SuppressLint("SetTextI18n")
    fun showSettings(){
        hideGui()
        b.llSettings.visibility = View.VISIBLE

        //Загружаем Storage
        val settings = getSharedPreferences(SettingsHandler.STORAGE_NAME, Context.MODE_PRIVATE)
        val settingsEditor = settings!!.edit()

        b.btSettingsBack.setOnClickListener {
            hideSettings()
            changeTheme(-1)
        }

        b.btExit.setOnClickListener {
            finishAffinity()
        }

        //Звук
        //Звук
        //Звук
        b.btSound.setOnClickListener {
            val sound = SettingsHandler.instance.sound
            SettingsHandler.instance.sound = !sound

            settingsEditor!!.putBoolean(SettingsHandler.SOUND, SettingsHandler.instance.sound)
            settingsEditor.apply()

            updateUiSound()
        }

        //Тема
        //Тема
        //Тема
        b.btTheme.setOnClickListener {
            val currentTheme = SettingsHandler.instance.theme

            SettingsHandler.instance.theme = when(currentTheme){
                SettingsHandler.THEME_DAY -> SettingsHandler.THEME_NIGHT
                SettingsHandler.THEME_NIGHT -> SettingsHandler.THEME_DAY
                else -> SettingsHandler.THEME_DAY
            }

            settingsEditor!!.putInt(SettingsHandler.THEME, SettingsHandler.instance.theme)
            settingsEditor.apply()

            updateUiTheme()
        }
    }

    fun hideSettings(){
        b.llSettings.visibility = View.GONE
        showGui()
    }

    override fun onResume() {
        super.onResume()
        hideSettings()
    }

    fun startGame(){
        hideGui()
        Handler().postDelayed({
            val activity = Intent(this, GameActivity ::class.java)
            startActivity(activity)
        },500)
    }

    //Отображаем на экране какая тема выбрана
    @SuppressLint("SetTextI18n")
    fun updateUiTheme(){
        val currentTheme = SettingsHandler.instance.theme

        when(currentTheme){
            SettingsHandler.THEME_DAY -> b.tvThemeInd.text = "Day"
            SettingsHandler.THEME_NIGHT -> b.tvThemeInd.text = "Night"
        }
    }

    //Отображаем на экране статус звука
    @SuppressLint("SetTextI18n")
    fun updateUiSound(){
        val sound = SettingsHandler.instance.sound
        if(sound){
            b.tvSoundInd.text = "On"
        }else{
            b.tvSoundInd.text = "Off"
        }
    }

    fun changeTheme(mode : Int){
        val theme : Int
        if(mode == -1){ //Если передаем -1, тема ставится автоматически по настройкам
            theme = SettingsHandler.instance.theme
        }else{
            theme = mode
        }

        when(theme){
            SettingsHandler.THEME_DAY ->{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                b.back.setImageResource(R.drawable.back)
            }
            SettingsHandler.THEME_NIGHT ->{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                b.back.setImageResource(R.drawable.back_night)
            }
        }

    }
}