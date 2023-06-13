package xyz.genscode.dagger

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.graphics.Rect
import android.hardware.HardwareBuffer
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import xyz.genscode.dagger.databinding.ActivityMainBinding

class GameActivity : AppCompatActivity() {
    lateinit var b : ActivityMainBinding
    var screenWidth : Int = 0
    var screenHeight : Int = 0

    var needleOnStart : ImageView? = null
    lateinit var needlesInBall : ArrayList<ImageView>

    lateinit var ballRotation : ObjectAnimator
    lateinit var interpolator: LinearInterpolator

    var lost = false //проиграл

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityMainBinding.inflate(layoutInflater)
        setContentView(b.root)

        needlesInBall = ArrayList()

        interpolator = LinearInterpolator()

        val displayMetrics = resources.displayMetrics
        screenWidth = displayMetrics.widthPixels
        screenHeight = displayMetrics.heightPixels

        ballRotation = ObjectAnimator.ofFloat(b.ballContainer, View.ROTATION, 0f, 360f)
        ballRotation.duration = 3000 // Длительность анимации в миллисекундах
        ballRotation.repeatCount = ObjectAnimator.INFINITE // Бесконечное повторение анимации
        ballRotation.interpolator = interpolator
        ballRotation.start()

        loadGui()
        startLevel()
    }

    fun startLevel(){
        Handler().postDelayed({
            spawnNeedle()
        },1000)
        b.container.setOnClickListener {
            b.container.isClickable = false
            startNeedle()
        }
    }

    fun loadGui(){
        Handler().postDelayed({
            b.tvLoading.visibility = View.GONE
            b.llBlack.visibility = View.GONE
        }, 1000)
    }

    fun showMenu(){

    }

    fun hideMenu(){

    }

    fun animate(needle : ImageView){
        val needle1Rotation = ObjectAnimator.ofFloat(needle, View.ROTATION, 0f, 360f)
        needle.pivotY = -(b.ballContainer.height/2f-b.needle1.height/3)
        needle1Rotation.duration = 3000 // Длительность анимации в миллисекундах
        needle1Rotation.repeatCount = ObjectAnimator.INFINITE // Бесконечное повторение анимации

        needle1Rotation.interpolator = interpolator
        needle1Rotation.start()

        needlesInBall.add(needle)
    }

    fun startNeedle(){
        val animator = ValueAnimator.ofFloat(needleOnStart!!.y, (screenHeight/2f)+(b.ballContainer.height/3f))
        animator.duration = 100 //Длительность анимации в миллисекундах
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
                    spawnNeedle()
                    ballDamage()
                }else{
                    breakNeedle()
                }
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationRepeat(animation: Animator) {}
        })

        animator.start()
    }
    fun ballDamage(){
        b.ballimg.animate().scaleX(1.05f).scaleY(1.05f).setDuration(100).start()
        Handler().postDelayed({
            b.ballimg.animate().scaleX(1.00f).scaleY(1.00f).setDuration(100).start()
        },100)
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
                    return true // Столкновение произошло
                }
            }
        }
        return false // Столкновение не произошло
    }

    fun spawnNeedle(){
        b.container.isClickable = false

        //Создаем иглу
        val needle = ImageView(this)
        needle.setImageResource(R.drawable.pin)
        needle.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        needle.x = (screenWidth/2f) - (b.needle1.width/2f)
        needle.y = (screenHeight) - (screenHeight/6f)
        needle.scaleX = 0.9f
        needle.scaleY = 0.9f

        needleOnStart = needle
        b.container.isClickable = true

        b.container.addView(needle)

    }

    fun breakNeedle(){
        needleOnStart!!.animate().yBy(200f).xBy(-40f).rotation(-40f).alpha(0f).setDuration(150).start()
        Handler().postDelayed({removeNeedle();}, 150)
    }

    fun removeNeedle(){
        b.container.removeView(needleOnStart)
    }

}