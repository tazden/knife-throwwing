package pugliasf.nbetvas.knifevipusk

import android.content.Context
import android.content.Intent

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

import pugliasf.nbetvas.knifevipusk.databinding.ActivitySplashBinding
import pugliasf.nbetvas.knifevipusk.utils.BonusManager
import java.io.*



class SplashActivity : AppCompatActivity() {
    lateinit var b : ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(b.root)
        val save1=findViewById<ImageView>(R.id.save)
        save1.setOnClickListener(){
            val intent=Intent(this,MainMenuActivity::class.java)
            startActivity(intent)
        }


        val daysCount = BonusManager.getDaysCount(this)
        val okday1=findViewById<ImageView>(R.id.okday1)
        val okday2=findViewById<ImageView>(R.id.okday2)
        val okday3=findViewById<ImageView>(R.id.okday3)
        val okday4=findViewById<ImageView>(R.id.okday4)
        val okday5=findViewById<ImageView>(R.id.okday5)
        val okday6=findViewById<ImageView>(R.id.okday6)
        val okday7=findViewById<ImageView>(R.id.okday7)
        if (daysCount==1){
            okday1.visibility= View.VISIBLE
        } else if (daysCount==2){
            okday2.visibility= View.VISIBLE
        }else if (daysCount==3){
            okday3.visibility= View.VISIBLE
        }else if (daysCount==4){
            okday4.visibility= View.VISIBLE
        }else if (daysCount==5){
            okday5.visibility= View.VISIBLE
        }else if (daysCount==6){
            okday6.visibility= View.VISIBLE
        }else if (daysCount==7){
            okday7.visibility= View.VISIBLE
        }






    }

    fun start(){
        val activity = Intent(this, MainMenuActivity::class.java)
        startActivity(activity)
        finish()
    }



}