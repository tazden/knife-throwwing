package pugliasf.nbetvas.knifevipusk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class PreviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)
        val knife=findViewById<ImageView>(R.id.knife)
        val play=findViewById<ImageView>(R.id.play)
        val home=findViewById<ImageView>(R.id.home)
        knife.setOnClickListener(){
            val intent=Intent(this,KnifeActivity::class.java)
            startActivity(intent)
            finish()
        }
        play.setOnClickListener(){
            val intent=Intent(this,GameActivity::class.java)
            startActivity(intent)
            finish()
        }
        home.setOnClickListener(){
            val intent=Intent(this,MainMenuActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}