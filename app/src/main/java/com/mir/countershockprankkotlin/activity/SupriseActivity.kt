package com.mir.countershockprankkotlin.activity

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.mir.countershockprankkotlin.*
import com.mir.countershockprankkotlin.helper.ShocklUtils
import com.mir.countershockprankkotlin.model.AudioModel
import com.mir.countershockprankkotlin.model.ImageModel
import com.mir.countershockprankkotlin.storer.AudioStorer
import com.mir.countershockprankkotlin.storer.ImageStorer
import java.io.File

class SupriseActivity : AppCompatActivity() {

    lateinit var imgSuprise:ImageView

    lateinit var photoUri:Uri
    lateinit var soundUri:Uri

    lateinit var tts:TextToSpeech
    lateinit var mediaPlayer:MediaPlayer

    var acceptTouches:Boolean = true

    lateinit var imageModel: ImageModel
    lateinit var audioModel: AudioModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suprise)
        init()
        noBar()
    }


    private fun init() {
        imgSuprise = findViewById(R.id.img_suprise)

        imageModel = ImageStorer(this).getSelectedImage()
        audioModel = AudioStorer(this).getSelectedAudio()

        drawableConfig()
        rawConfig()

        Toast.makeText(this, "Ready", Toast.LENGTH_SHORT).show();
    }

    private fun drawableConfig() {
        if (imageModel.isAsset){
            photoUri = ShocklUtils.getDrawableUri(this, imageModel.imgFileName)
        }else{
            photoUri = Uri.fromFile(File(imageModel.imgFileName))
        }
    }

    private fun rawConfig() {
        if (!audioModel.isTTS){
            soundUri = ShocklUtils.getRawUri(this, audioModel.audioFilename)
        }
    }

    private fun noBar() {
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    private fun showImage() {
        Glide.with(this)
            .load(photoUri)
            .into(imgSuprise)

        imgSuprise.visibility = View.VISIBLE
    }

    private fun playSoundClip() {
        mediaPlayer = MediaPlayer.create(this, soundUri)
        mediaPlayer.setOnCompletionListener {
            finish()
        }
        mediaPlayer.start()
    }

    private fun handleTTS(){
        val toSpeak = audioModel.descriptionMessage
        tts = TextToSpeech(this, object: TextToSpeech.OnInitListener{
            override fun onInit(p0: Int) {
                val params = HashMap<String, String>()

                params[TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID] = "utterId"

                if (p0 == TextToSpeech.SUCCESS){
                    tts.setOnUtteranceCompletedListener {
                        finish()
                    }
                    tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, params)
                }else{
                    finish()
                }
            }

        })
    }

    private fun userTriggeredAction() {
        if (!acceptTouches){
            return
        }
        acceptTouches = false
        if (audioModel.isTTS){
            handleTTS()
        }else{
            playSoundClip()
        }
        showImage()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        userTriggeredAction()
        return super.onTouchEvent(event)
    }

}