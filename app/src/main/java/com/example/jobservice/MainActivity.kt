package com.example.jobservice

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.*
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager


class MainActivity : AppCompatActivity() {

    var bustart:Button? = null
    var random_image:ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bustart = findViewById(R.id.bu_start)
        random_image = findViewById(R.id.random_image)

        bustart!!.setOnClickListener {
            val cn = ComponentName(this ,MyJobService()::class.java)
            var jobinformation:JobInfo? = null
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N)
            {
                jobinformation = JobInfo.Builder(189 ,cn)
                    .setPeriodic(6000)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .build()
            }
            else
            {
                jobinformation = JobInfo.Builder(189 ,cn)
                    .setMinimumLatency(6000)
                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                    .build()
            }
            val scheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
            scheduler.schedule(jobinformation)
        }
        LocalBroadcastManager.getInstance(applicationContext)
            .registerReceiver(Ischanged, IntentFilter("my.action.string"))

        Ischanged.goAsync()

    }

    private val Ischanged: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Toast.makeText(context ,intent.extras?.getString("counter") ,Toast.LENGTH_LONG).show()

            val i = intent.extras!!.getString("counter")

            random_image?.setBackgroundResource(context.resources.getIdentifier("img$i" ,"drawable" ,context.packageName))
        }
    }


}