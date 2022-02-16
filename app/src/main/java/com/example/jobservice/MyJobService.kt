package com.example.jobservice

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager


class MyJobService:JobService() {

    override fun onStartJob(params: JobParameters?): Boolean {
        Thread(Runnable {
            for (i in 0..4)
            {
                Log.d("i->" ,i.toString())
                val intent = Intent("my.action.string")
                intent.putExtra("counter" ,i.toString())
                LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intent)
                Thread.sleep(5000)
            }
        }).start()
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {

        return true
    }
}