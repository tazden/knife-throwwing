package pugliasf.nbetvas.knifevipusk

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.Rect
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.github.jinatonic.confetti.CommonConfetti
import pugliasf.nbetvas.knifevipusk.data.SettingsHandler
import pugliasf.nbetvas.knifevipusk.databinding.ActivityMainBinding
import pugliasf.nbetvas.knifevipusk.utils.Levels
import pugliasf.nbetvas.knifevipusk.utils.Sound
import java.util.*


class GameActivity : AppCompatActivity() {
    lateinit var b : ActivityMainBinding
    var screenWidth : Int = 0
    var screenHeight : Int = 0

    var needleOnStart : ImageView? = null
    lateinit var needlesInBall : ArrayList<ImageView>
    lateinit var volumeTextView:TextView
    lateinit var etap:TextView
    lateinit var scoreTextView:TextView
    lateinit var ballRotation : ObjectAnimator
    lateinit var interpolator: LinearInterpolator
    lateinit var life1:ImageView
    lateinit var life2:ImageView
    lateinit var life3:ImageView
    lateinit var life4:ImageView
    lateinit var life5:ImageView
    lateinit var sec:ImageView
    lateinit var thir:ImageView
    lateinit var four:ImageView
    lateinit var first:ImageView
    lateinit var five:ImageView
    lateinit var timer : Timer
    lateinit var sound: Sound
    var left=30
    var rigt=100
    var lost = false //проиграл
    var level = 1
    var time = 0
    var hits = 0
    var hitsLimit = 0

    var xBall = 0f
    var yBall = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        overridePendingTransition(0, 0)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        changeTheme(-1)
        volumeTextView = findViewById<TextView>(R.id.volume)
        scoreTextView = findViewById<TextView>(R.id.textscore)
        needlesInBall = ArrayList()
        sound = Sound(this)

        interpolator = LinearInterpolator()
        life1=findViewById<ImageView>(R.id.life1)
        etap=findViewById(R.id.etap)
         life2=findViewById<ImageView>(R.id.life2)
         life3=findViewById<ImageView>(R.id.life3)
         life4=findViewById<ImageView>(R.id.life4)
         life5=findViewById<ImageView>(R.id.life5)
        first=findViewById(R.id.first)
        five=findViewById(R.id.five)
        sec=findViewById(R.id.second)
        thir=findViewById(R.id.third)
        four=findViewById(R.id.four)
        val displayMetrics = resources.displayMetrics
        screenWidth = displayMetrics.widthPixels
        screenHeight = displayMetrics.heightPixels



        showGui()
        startLevel(1)
    }

    @SuppressLint("SetTextI18n")
    fun startLevel(level : Int){
        b.container.setOnClickListener {}
        lost = false
        time = 0
        hits = 0

        b.container.isClickable = false
        b.ballContainer.visibility = View.INVISIBLE
        when(level){
            1 -> {
                etap.text="Этап 1"
                b.ballimg.setImageResource(R.drawable.tree_cutting)
                b.ballimgPart1.setImageResource(R.drawable.mask_group)
                b.ballimgPart2.setImageResource(R.drawable.mask_group__3_)
                ballRotation = ObjectAnimator.ofFloat(b.ballimg, View.ROTATION, 0f, 360f)
                ballRotation.duration = 3000 // Длительность анимации в миллисекундах
                ballRotation.repeatCount = ObjectAnimator.INFINITE // Бесконечное повторение анимации
                ballRotation.interpolator = interpolator
                ballRotation.start()


            }
            2 -> {
                etap.text="Этап 2"
                sec.setImageResource(R.drawable.ellipse_1)
                b.ballimg.setImageResource(R.drawable.tree_cutting)
                b.ballimgPart1.setImageResource(R.drawable.mask_group)
                b.ballimgPart2.setImageResource(R.drawable.mask_group__3_)
                ballRotation = ObjectAnimator.ofFloat(b.ballimg, View.ROTATION, 0f, -1f*360f)
                ballRotation.duration = 1500 // Длительность анимации в миллисекундах
                ballRotation.repeatCount = ObjectAnimator.INFINITE // Бесконечное повторение анимации
                ballRotation.interpolator = interpolator
                ballRotation.start()
            }
            3 -> {
                etap.text="Этап 3"
                thir.setImageResource(R.drawable.ellipse_1)
                b.ballimg.setImageResource(R.drawable.tree_cutting)
                b.ballimgPart1.setImageResource(R.drawable.mask_group)
                b.ballimgPart2.setImageResource(R.drawable.mask_group__3_)
                ballRotation = ObjectAnimator.ofFloat(b.ballimg, View.ROTATION, 0f, 360f)
                ballRotation.duration = 1000 // Длительность анимации в миллисекундах
                ballRotation.repeatCount = ObjectAnimator.INFINITE // Бесконечное повторение анимации
                ballRotation.interpolator = interpolator
                ballRotation.start()
            }
            4 -> {
                etap.text="Этап 4"
                four.setImageResource(R.drawable.ellipse_1)
                b.ballimg.setImageResource(R.drawable.tree_cutting)
                b.ballimgPart1.setImageResource(R.drawable.mask_group)
                b.ballimgPart2.setImageResource(R.drawable.mask_group__3_)
                ballRotation = ObjectAnimator.ofFloat(b.ballimg, View.ROTATION, 0f, -1f*360f)
                ballRotation.duration = 800 // Длительность анимации в миллисекундах
                ballRotation.repeatCount = ObjectAnimator.INFINITE // Бесконечное повторение анимации
                ballRotation.interpolator = interpolator
                ballRotation.start()
            }
            5 -> {
                etap.text="Этап 5"
                five.setImageResource(R.drawable.ellipse_1)
                b.ballimg.setImageResource(R.drawable.tree_cutting)
                b.ballimgPart1.setImageResource(R.drawable.mask_group)
                b.ballimgPart2.setImageResource(R.drawable.mask_group__3_)
                ballRotation = ObjectAnimator.ofFloat(b.ballimg, View.ROTATION, 0f, -1f*360f)
                ballRotation.duration = 500 // Длительность анимации в миллисекундах
                ballRotation.repeatCount = ObjectAnimator.INFINITE // Бесконечное повторение анимации
                ballRotation.interpolator = interpolator
                ballRotation.start()
            }
        }

        showBall()

        //Сколько нужно попасть
        hitsLimit = Levels().getHits(level)

        //Время для уровня
        val timeLimit = Levels().getTime(level)

        timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            @SuppressLint("SetTextI18n")
            override fun run() {
                runOnUiThread {
                    time++
                    val timeCalc = timeLimit-time
                    var timeString = "$timeCalc"
                    if(timeCalc <= 9) timeString = "0$timeString"

                    if(timeLimit <= time){
                        lost()
                        timer.cancel()
                    }
                    if(lost){
                        timer.cancel()
                    }
                }
            }
        }, 1000, 1000);

        //Запускаем уровень
        Handler().postDelayed({
            xBall = b.ballimg.x
            yBall = b.ballimg.y
            spawnNeedle()

            b.container.setOnClickListener {
                b.container.isClickable = false
                startNeedle()
            }
        },1000)


        println("Started level $level")
        println("Time: $timeLimit")
        println("Hits: $hitsLimit")

    }

    fun nextLevel(){
        level += 1
        if(level > 5){
            showMenu(true)
        }else{
            startLevel(level)
        }

    }


    fun animate(needle : ImageView){
        val needle1Rotation = ObjectAnimator.ofFloat(needle, View.ROTATION, 0f, 360f)
        needle.pivotY = -(b.ballimg.height/2f-b.needle1.height/3)
        if (level==1) {
            needle1Rotation.duration = 3000
        } else if (level==2) {
            needle1Rotation.duration = 1500
        }else if (level==3) {
            needle1Rotation.duration = 1000}
        else if(level==4) {
            needle1Rotation.duration = 500}

        // Длительность анимации в миллисекундах
        needle1Rotation.repeatCount = ObjectAnimator.INFINITE // Бесконечное повторение анимации

        needle1Rotation.interpolator = interpolator
        needle1Rotation.start()

        needlesInBall.add(needle)
    }

    fun startNeedle(){
        val animator = ValueAnimator.ofFloat(needleOnStart!!.y, (screenHeight/2f)+(b.ballimg.height/3f))
        animator.duration = 80 //Длительность анимации в миллисекундах
        animator.interpolator = LinearInterpolator()

        animator.addUpdateListener { valueAnimator ->
            val animatedValue = valueAnimator.animatedValue as Float
            needleOnStart!!.translationY = animatedValue
            if(!lost){
                lost = checkCollision(needleOnStart!!)
            }
        }

        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                if(!lost){
                    animate(needleOnStart!!)
                    ballDamage()
                }else{
                    breakNeedle()
                    lost()
                }
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })

        animator.start()
    }
    fun ballDamage(){
        left+=5
        rigt+=25
        volumeTextView.text=left.toString()
        scoreTextView.text=rigt.toString()
        b.ballimg.animate().scaleX(1.02f).scaleY(1.08f).setDuration(100).start()
        Handler().postDelayed({
            b.ballimg.animate().scaleX(1.00f).scaleY(1.00f).setDuration(100).start()
        },100)

        hits++
        val left = hitsLimit-hits
        if (hits==1){
            life1.setImageResource(R.drawable.knife_one)
        }else if(hits==2){
            life2.setImageResource(R.drawable.knife_one)
        }else if(hits==3){
            life3.setImageResource(R.drawable.knife_one)
        }else if(hits==4){
            life4.setImageResource(R.drawable.knife_one)
        }else if(hits==5){
            life5.setImageResource(R.drawable.knife_one)
        }
        if(hits == hitsLimit){
            timer.cancel()
            life1.setImageResource(R.drawable.knife_one__1_)
            life2.setImageResource(R.drawable.knife_one__1_)
            life3.setImageResource(R.drawable.knife_one__1_)
            life4.setImageResource(R.drawable.knife_one__1_)
            life5.setImageResource(R.drawable.knife_one__1_)

            breakBoll()

            Handler().postDelayed({
                nextLevel()
            }, 700)
        }else{
            spawnNeedle()
        }

        when(level){
            1 -> sound.play(sound.AUDIO_HITTING_BALL)
            2 -> sound.play(sound.AUDIO_HITTING_BALL)
            3 -> sound.play(sound.AUDIO_HITTING_STEEL)
            4 -> sound.play(sound.AUDIO_HITTING_STEEL)
            5 -> sound.play(sound.AUDIO_HITTING_STEEL)
        }

        if(hitsLimit/2 < hits){
            when(level){
                1 -> b.ballimg.setImageResource(R.drawable.tree_cutting)
                2 -> b.ballimg.setImageResource(R.drawable.tree_cutting)
                3 -> b.ballimg.setImageResource(R.drawable.tree_cutting)
                4 -> b.ballimg.setImageResource(R.drawable.tree_cutting)
                5 -> b.ballimg.setImageResource(R.drawable.tree_cutting)
            }
        }else if(hitsLimit/3 <= hits){
            when(level){
                1 -> b.ballimg.setImageResource(R.drawable.tree_cutting)
                2 -> b.ballimg.setImageResource(R.drawable.tree_cutting)
                3 -> b.ballimg.setImageResource(R.drawable.tree_cutting)
                4 -> b.ballimg.setImageResource(R.drawable.tree_cutting)
                5 -> b.ballimg.setImageResource(R.drawable.tree_cutting)

            }
        }

        val explosion = CommonConfetti.explosion(b.container, screenWidth/2, screenHeight/2+b.ballimg.height/2, intArrayOf(Color.parseColor("#20000000")))
        explosion.confettiManager
            .setEmissionDuration(50)
            .setEmissionRate(200f)
            .animate();
    }




    fun checkCollision(needle: ImageView): Boolean {
        val rectNeedle = Rect()
        needle.getHitRect(rectNeedle)
        val newWidth = rectNeedle.width()/12
        rectNeedle.right = rectNeedle.left + newWidth
        for (otherNeedle in needlesInBall) {
            if (otherNeedle !== needle) {
                val rectOtherNeedle = Rect()
                otherNeedle.getHitRect(rectOtherNeedle)

                val newWidth2 = rectOtherNeedle.width()/12
                rectOtherNeedle.right = rectOtherNeedle.left + newWidth2
                if (Rect.intersects(rectNeedle, rectOtherNeedle)) {
                    return true //Столкновение произошло
                }
            }
        }
        return false //Столкновение не произошло
    }

    fun spawnNeedle(){
        b.container.isClickable = false

        //Создаем иглу
        val needle = ImageView(this)
        needle.setImageResource(R.drawable.knife_main)
        needle.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        needle.maxWidth = b.needle1.width
        needle.maxHeight = b.needle1.height
        needle.x = (screenWidth/2f) - (b.needle1.width/2f)
        needle.y = (screenHeight) - (screenHeight/5f)

        needle.layoutParams = RelativeLayout.LayoutParams(248, 248);

        needleOnStart = needle
        b.container.isClickable = true

        b.container.addView(needle)
    }

    private fun breakBoll(){
        for (needle in needlesInBall){
            needle.animate().yBy(350f).alpha(0f).setDuration(250).start()
            Handler().postDelayed({
                removeNeedle(needle)
            },350)
        }

        b.ballimg.visibility = View.GONE
        b.ballimgPart1.animate().rotation(-35f).xBy(-100f).start()
        b.ballimgPart1.animate().rotation(-100f).xBy(-300f).setDuration(700).start()
        b.ballimgPart1.animate().yBy(screenHeight-screenHeight/2f).setDuration(700).start()
        b.ballimgPart1.animate().alpha(0f).setDuration(350).start()

        b.ballimgPart2.animate().rotation(35f).xBy(100f).start()
        b.ballimgPart2.animate().rotation(100f).xBy(300f).setDuration(700).start()
        b.ballimgPart2.animate().yBy(screenHeight-screenHeight/2f).setDuration(700).start()
        b.ballimgPart2.animate().alpha(0f).setDuration(350).start()

        val explosion = CommonConfetti.explosion(b.container, screenWidth/2, screenHeight/2,
            intArrayOf(
                Color.parseColor("#20000000"),
                Color.parseColor("#10000000"),
                Color.parseColor("#20ffffff"),
                Color.parseColor("#10ffffff")
            )
        )
        explosion.oneShot()

        when(level){
            1 -> sound.play(sound.AUDIO_DESTROYING_STEEL)
            2 -> sound.play(sound.AUDIO_DESTROYING_STEEL)
            3 -> sound.play(sound.AUDIO_DESTROYING_STEEL)
            4 -> sound.play(sound.AUDIO_DESTROYING_DIAMOND)
            5 -> sound.play(sound.AUDIO_DESTROYING_DIAMOND)
        }

    }

    fun breakNeedle(){
        needleOnStart!!.animate().yBy(200f).xBy(-40f).rotation(-40f).alpha(0f).setDuration(150).start()
        Handler().postDelayed({removeNeedle();}, 150)
        sound.play(sound.AUDIO_HITTING_NEEDLES)
    }
    private fun removeNeedle(needle: ImageView){
        b.container.removeView(needle)
    }
    private fun removeNeedle(){
        b.container.removeView(needleOnStart)
    }

    private fun showBall(){
        if(xBall != 0f) {
            b.ballimgPart1.animate().x(xBall).y(yBall).rotation(0f).alpha(1f).setDuration(1).start()
            b.ballimgPart2.animate().x(xBall).y(yBall).rotation(0f).alpha(1f).setDuration(1).start()
            b.ballimg.visibility = View.VISIBLE
        }
        b.ballContainer.scaleX = 0.6f; b.ballContainer.scaleY = 0.6f
        b.ballContainer.animate().scaleY(1f).scaleX(1f).start()
        b.ballContainer.visibility = View.VISIBLE
    }

    private fun showGui(){
        Handler().postDelayed({
            b.tvLoading.visibility = View.GONE
            b.llBlack.visibility = View.GONE

        }, 1000)
    }

    fun hideGui(){
        b.llBlack.visibility = View.VISIBLE

    }

    fun showMenu(isWin : Boolean){
        hideGui()
        b.llMenu.visibility = View.VISIBLE

        if(isWin){
            b.tvLost.text="You Win!"
        }else{
            b.tvLost.text="You Lost!"
        }

        b.btRestart.setOnClickListener {
            recreate()
        }
        b.btBack.setOnClickListener {
            finish()
            overridePendingTransition(0, 0)
        }
    }

    fun lost(){
        lost = true
        Handler().postDelayed({
            showMenu(false)
        },1000)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(0, 0)
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
            }
            SettingsHandler.THEME_NIGHT ->{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        }
    }
}