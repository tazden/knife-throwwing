package xyz.genscode.dagger

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.remoteConfig
import xyz.genscode.dagger.databinding.ActivitySplashBinding
import java.io.*



class SplashActivity : AppCompatActivity() {
    lateinit var b : ActivitySplashBinding
    var url = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(b.root)


        if(!isInternet()) start()

        FirebaseApp.initializeApp(this)
        val remoteConfig = Firebase.remoteConfig


        val defaultConfig = hashMapOf(
            "redirect" to false
        )

        remoteConfig.setDefaultsAsync(defaultConfig as Map<String, Any>)

        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Конфигурация успешно получена и активирована
                    val redirect = remoteConfig.getBoolean("redirect")
                    println("redirect: $redirect")
                    println("redirect: $redirect")
                    println("redirect: $redirect")

                    if(redirect){
                        url = remoteConfig.getString("url")
                    }

                    start()
                } else {
                    // Ошибка получения конфигурации
                    start()
                }
            }
    }

    fun start(){
        val activity = Intent(this, MainMenuActivity::class.java)
        activity.putExtra("url", url)
        startActivity(activity)
        finish()
    }

    fun isInternet() : Boolean{
        val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            return capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }

        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

}