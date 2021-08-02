package com.example.studyapp

import android.graphics.Bitmap

interface DownloadImageCallback {

    fun success(bitmap: Bitmap)

    fun failed()
}