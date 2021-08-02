package com.example.studyapp

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.bumptech.glide.RequestManager
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private companion object{
        //const val URL_FOR_DOWNLOAD_IMAGE = "https://zavistnik.com/wp-content/uploads/2020/03/Android-kursy-zastavka.jpg"
        const val URL_FOR_DOWNLOAD_IMAGE = "https://images7.alphacoders.com/109/1092420.jpg"
        const val url_new = "https://images4.alphacoders.com/109/1095230.jpg"
    }

    private lateinit var textView: TextView
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initImageView()
        downloadImageByGlide()

    }

    fun initImageView(){
        imageView = findViewById(R.id.id_imageview_icon)
        imageView.setImageResource(R.drawable.original)
    }

    fun downloadImageByGlide(){
        val requestManager: RequestManager = Glide.with(this)
        val loadResult: RequestBuilder<Drawable> = requestManager.load(URL_FOR_DOWNLOAD_IMAGE)
        val resultToRound = loadResult.circleCrop()
        val holder = ResourcesCompat.getDrawable(resources, android.R.drawable.ic_media_pause, null)
        if (holder != null) {
            holder.setTint(resources.getColor(R.color.redColorTitle))
        }
        //val addPlaceholder = resultToRound.placeholder(ResourcesCompat.getDrawable(resources, android.R.drawable.ic_media_pause, null))
        val addPlaceholder = resultToRound.placeholder(holder)
        val addError = addPlaceholder.error(ResourcesCompat.getDrawable(resources, android.R.drawable.ic_dialog_alert, null))
        addError.into(imageView)
    }

    /*fun downloadImageByPicasso(){
        val requestCreator: RequestCreator = Picasso.get().load(URL_FOR_DOWNLOAD_IMAGE)
        requestCreator.centerCrop()
        requestCreator.resize(720, 1080)
        requestCreator.placeholder(android.R.drawable.ic_media_pause)
        requestCreator.error(android.R.drawable.ic_dialog_alert)

        requestCreator.into(imageView)
    }*/

    fun downloadImage(){
        val newThread = createAndGetNewThread()
        newThread.start()
    }

    fun createAndGetNewThread(): ThreadForDownloadImage{
        val callBackForDownloadThread = getNewCallBackForDownloadImage()
        val newThreadForDownloadImage = ThreadForDownloadImage(URL_FOR_DOWNLOAD_IMAGE, callBackForDownloadThread)
        return newThreadForDownloadImage
    }

    fun getNewCallBackForDownloadImage(): DownloadImageCallback{

        class myRunnableForSetImage(private val bitmap: Bitmap) : Runnable{
            override fun run() {
                imageView.setImageBitmap(bitmap)
            }
        }

        class myRunnableForShowError() : Runnable{
            override fun run() {
                Snackbar.make(imageView, "Ошибка загрузки", Snackbar.LENGTH_SHORT).show()
            }
        }

        val newCallBack = object : DownloadImageCallback{
            override fun success(bitmap: Bitmap) {
                runOnUiThread(myRunnableForSetImage(bitmap))

            }

            override fun failed() {
                runOnUiThread(myRunnableForShowError())
            }
        }

        return newCallBack
    }
}

