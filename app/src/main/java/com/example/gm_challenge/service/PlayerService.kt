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
import com.example.gm_challenge.model.data.item.Track
import com.example.gm_challenge.service.MessageEvent.Companion.NEXT
import com.example.gm_challenge.service.MessageEvent.Companion.PREVIOUS
import com.example.gm_challenge.view.MainActivity
import com.example.gm_challenge.view.fragment.ItemFragment.Companion.PLAY_TRACK
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class PlayerService : Service() {
    private val LOG_TAG = "ForegroundService"

    companion object {
        var IS_SERVICE_RUNNING = false
    }

    private var track: Track? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this)
        }

        when (intent.action) {
            Constants.ACTION.STARTFOREGROUND_ACTION -> {
                Log.i(LOG_TAG, "Received Start Foreground Intent ")
                if (intent.extras.containsKey(PLAY_TRACK)) {
                    track = intent.extras.getParcelable(PLAY_TRACK)
                }
                showNotification(track)
            }
            Constants.ACTION.PREV_ACTION -> {
                EventBus.getDefault().post(MessageEvent(PREVIOUS))
            }
            Constants.ACTION.PLAY_ACTION -> {
                track?.isPlaying = true
                showNotification(track)
            }
            Constants.ACTION.PAUSE_ACTION -> {
                track?.isPlaying = false
                showNotification(track)
            }
            Constants.ACTION.NEXT_ACTION -> {
                EventBus.getDefault().post(MessageEvent(NEXT))
            }
            Constants.ACTION.STOPFOREGROUND_ACTION -> {
                stopForeground(true)
                stopSelf()
            }
        }
        return START_STICKY
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(track: Track?) {
        this.track = track
        showNotification(track)
    }

    private fun showNotification(track: Track?) {
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

        val playOrPauseIntent = Intent(this, PlayerService::class.java)
        var playOrPauseString = ""
        if (track?.isPlaying == true) {
            playOrPauseString = "Pause"
            playOrPauseIntent.action = Constants.ACTION.PAUSE_ACTION
        } else {
            playOrPauseString = "Play"
            playOrPauseIntent.action = Constants.ACTION.PLAY_ACTION
        }
        val pplayPauseIntent = PendingIntent.getService(
            this, 0,
            playOrPauseIntent, 0
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
            .setContentTitle(track?.artist?.name?:"Uknown artist")
            .setContentText(track?.name?:"Uknown song")
            .setSmallIcon(R.drawable.ic_btn_speak_now)
            .setLargeIcon(Bitmap.createScaledBitmap(icon, 128, 128, false))
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .addAction(
                R.drawable.ic_media_previous, "Previous",
                ppreviousIntent
            )
            .addAction(
                R.drawable.ic_media_play, playOrPauseString,
                pplayPauseIntent
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
        EventBus.getDefault().unregister(this)
        Toast.makeText(this, "Service Destroyed!", Toast.LENGTH_SHORT).show()
    }

    override fun onBind(intent: Intent?): IBinder? { // Used only in case if services are bound (Bound Services).
        return null
    }
}
