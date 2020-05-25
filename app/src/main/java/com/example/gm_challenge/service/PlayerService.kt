package com.example.gm_challenge.service

import android.R
import android.app.*
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.gm_challenge.view.MainActivity

class PlayerService : Service() {
    private val LOG_TAG = "ForegroundService"

    companion object {
        var IS_SERVICE_RUNNING = false
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        when (intent.action) {
            Constants.ACTION.STARTFOREGROUND_ACTION -> {
                Log.i(LOG_TAG, "Received Start Foreground Intent ")
                showNotification()
                Toast.makeText(this, "Service Started!", Toast.LENGTH_SHORT).show()
            }
            Constants.ACTION.PREV_ACTION -> {
                Log.i(LOG_TAG, "Clicked Previous")
                Toast.makeText(this, "Clicked Previous!", Toast.LENGTH_SHORT)
                    .show()
            }
            Constants.ACTION.PLAY_ACTION -> {
                Log.i(LOG_TAG, "Clicked Play")
                Toast.makeText(this, "Clicked Play!", Toast.LENGTH_SHORT).show()
            }
            Constants.ACTION.NEXT_ACTION -> {
                Log.i(LOG_TAG, "Clicked Next")
                Toast.makeText(this, "Clicked Next!", Toast.LENGTH_SHORT).show()
            }
            Constants.ACTION.STOPFOREGROUND_ACTION -> {
                Log.i(LOG_TAG, "Received Stop Foreground Intent")
                stopForeground(true)
                stopSelf()
            }
        }
        return START_STICKY
    }


    private fun showNotification() {
        val notificationIntent = Intent(this, MainActivity::class.java)
        notificationIntent.action = Constants.ACTION.MAIN_ACTION
        notificationIntent.flags = (Intent.FLAG_ACTIVITY_NEW_TASK
                or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        val pendingIntent = PendingIntent.getActivity(
            this, 0,
            notificationIntent, 0
        )
        val previousIntent = Intent(this, PlayerService::class.java)
        previousIntent.action = Constants.ACTION.PREV_ACTION
        val ppreviousIntent = PendingIntent.getService(
            this, 0,
            previousIntent, 0
        )
        val playIntent = Intent(this, PlayerService::class.java)
        playIntent.action = Constants.ACTION.PLAY_ACTION
        val pplayIntent = PendingIntent.getService(
            this, 0,
            playIntent, 0
        )
        val nextIntent = Intent(this, PlayerService::class.java)
        nextIntent.action = Constants.ACTION.NEXT_ACTION
        val pnextIntent = PendingIntent.getService(
            this, 0,
            nextIntent, 0
        )
        val icon = BitmapFactory.decodeResource(
            resources,
            R.drawable.ic_btn_speak_now
        )
        createNotificationChannel()
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Michael Jackson")
            .setContentText("Billie Jean")
            .setSmallIcon(R.drawable.ic_btn_speak_now)
            .setLargeIcon(Bitmap.createScaledBitmap(icon, 128, 128, false))
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .addAction(
                R.drawable.ic_media_previous, "Previous",
                ppreviousIntent
            )
            .addAction(
                R.drawable.ic_media_play, "Play",
                pplayIntent
            )
            .addAction(
                R.drawable.ic_media_next, "Next",
                pnextIntent
            ).build()
        startForeground(
            Constants.NOTIFICATION_ID.FOREGROUND_SERVICE,
            notification
        )
    }

    private val CHANNEL_ID = "ForegroundService Kotlin"
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID, "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT)

            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Service Destroyed!", Toast.LENGTH_SHORT).show()
    }

    override fun onBind(intent: Intent?): IBinder? { // Used only in case if services are bound (Bound Services).
        return null
    }
}
