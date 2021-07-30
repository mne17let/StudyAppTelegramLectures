package com.example.studyapp

import java.lang.Exception
import java.net.URL
import java.net.URLConnection

class ThreadForDownloadImage(private val stringURL: String, private val callback: DownloadImageCallback): Thread() {

    override fun run(){
        try{
            val urlClassInstance: URL = URL(stringURL)
            val connection: URLConnection = urlClassInstance.openConnection()
            connection.doInput = true
            connection.connect()
            callback.success()
            connection.getInputStream().close()


        } catch (e: Exception){
            callback.failed()
        }
    }
}