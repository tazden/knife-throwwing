package xyz.genscode.dagger.utils

import android.content.Context
import android.media.MediaPlayer
import android.media.SoundPool
import xyz.genscode.dagger.R
import xyz.genscode.dagger.data.SettingsHandler
import kotlin.concurrent.thread

class Sound(private val context : Context){


    var AUDIO_HITTING_NEEDLES : Int
    var AUDIO_HITTING_BALL : Int
    var AUDIO_HITTING_STEEL : Int
    var AUDIO_DESTROYING_STEEL : Int
    var AUDIO_DESTROYING_DIAMOND : Int

    init{
        AUDIO_HITTING_NEEDLES = R.raw.destroying_needles
        AUDIO_HITTING_BALL = R.raw.hit
        AUDIO_HITTING_STEEL = R.raw.hit
        AUDIO_DESTROYING_DIAMOND = R.raw.destroying_diamonds
        AUDIO_DESTROYING_STEEL = R.raw.destroying_steel
    }

    fun play(sound : Int){
        if(!SettingsHandler.instance.sound) return //включен ли звук

        Thread{
            val mediaPlayer = MediaPlayer.create(context, sound)
            mediaPlayer.setVolume(0.15f, 0.15f)

            mediaPlayer.start()

            // Ожидание окончания воспроизведения
            while (mediaPlayer.isPlaying) {
                Thread.sleep(100)
            }

            mediaPlayer.release()
        }.start()
    }

}