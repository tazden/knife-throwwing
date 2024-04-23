package pugliasf.nbetvas.knifevipusk

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import pugliasf.nbetvas.knifevipusk.data.SettingsHandler
import pugliasf.nbetvas.knifevipusk.databinding.ActivityMainMenuBinding


class MainMenuActivity : AppCompatActivity() {
    lateinit var b : ActivityMainMenuBinding
    private lateinit var timerTextView: TextView
    private val startTime = "07:55:18"
    var count=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        b = ActivityMainMenuBinding.inflate(layoutInflater)
        setContentView(b.root)

        SettingsHandler.instance.getSettings(this)
        updateUiTheme()
        updateUiSound()
        changeTheme(-1)
        timerTextView = findViewById(R.id.timer)

        // Парсим начальное время
        val timeParts = startTime.split(":")
        var hours = timeParts[0].toInt()
        var minutes = timeParts[1].toInt()
        var seconds = timeParts[2].toInt()

        // Обновление времени каждую секунду
        val handler = Handler()
        handler.post(object : Runnable {
            override fun run() {
                // Уменьшаем время на одну секунду
                seconds--
                if (seconds < 0) {
                    minutes--
                    seconds = 59
                }
                if (minutes < 0) {
                    hours--
                    minutes = 59
                }

                // Форматируем время в строку
                val timeString = String.format("%02d:%02d:%02d", hours, minutes, seconds)
                timerTextView.text = timeString

                // Проверяем, если время не истекло, продолжаем обновление
                if (hours > 0 || minutes > 0 || seconds > 0) {
                    handler.postDelayed(this, 1000)
                }
            }
        })
        val volume=findViewById<ImageView>(R.id.volume)
        volume.setOnClickListener(){
            count++
            if (count%2!=0)
                volume.setImageResource(R.drawable.property_1_volume_mute)
            else
                volume.setImageResource(R.drawable.property_1_volume)
        }
        b.knife.setOnClickListener {
            val intent=Intent(this,KnifeActivity::class.java)
            startActivity(intent)
        }

        b.play.setOnClickListener {
            startGame()
        }
        b.gift.setOnClickListener {
            val intent=Intent(this,SplashActivity::class.java)
            startActivity(intent)
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
    }

    fun hideGui(){
        b.llBtns.visibility = View.INVISIBLE
        b.tvLogo.visibility = View.INVISIBLE
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
            val activity = Intent(this, PreviewActivity ::class.java)
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



    }
}