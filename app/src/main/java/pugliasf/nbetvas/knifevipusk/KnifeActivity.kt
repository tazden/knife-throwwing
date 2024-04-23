package pugliasf.nbetvas.knifevipusk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class KnifeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_knife)
        val home=findViewById<ImageView>(R.id.home)
        home.setOnClickListener(){
            val intent=Intent(this,MainMenuActivity::class.java)
            startActivity(intent)
        }
    }
}