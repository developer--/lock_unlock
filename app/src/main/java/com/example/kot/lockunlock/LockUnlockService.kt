package com.example.kot.lockunlock

import android.app.Service
import android.app.admin.DevicePolicyManager
import android.content.Context
import android.content.Intent
import android.os.IBinder
import android.support.v4.media.VolumeProviderCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import android.os.PowerManager
import android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
import safety.com.br.android_shake_detector.core.ShakeDetector
import safety.com.br.android_shake_detector.core.ShakeOptions




/**
 * Created by kot on 2/6/18.
 */
class LockUnlockService : Service() {

    private val mgr: DevicePolicyManager by lazy { getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager }
    private lateinit var shakeDetector: ShakeDetector

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private var mediaSession: MediaSessionCompat? = null


    override fun onCreate() {
        super.onCreate()

        val options = ShakeOptions()
                .background(true)
                .interval(1000)
                .shakeCount(2)
                .sensibility(2.0f)

        this.shakeDetector =  ShakeDetector(options).start(this)


        mediaSession = MediaSessionCompat(this, "PlayerService");
        mediaSession!!.setFlags(MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS or
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);
        mediaSession!!.setPlaybackState(PlaybackStateCompat.Builder()
                .setState(PlaybackStateCompat.STATE_PLAYING, 0, 0f) //you simulate a player which plays something.
                .build())
        val myVolumeProvider = object : VolumeProviderCompat(VolumeProviderCompat.VOLUME_CONTROL_RELATIVE, /*max volume*/100, /*initial volume level*/50) {
            override fun onAdjustVolume(direction: Int) {
                Log.e("ACTION", direction.toString())
                when (direction) {
                    1 -> mgr.lockNow()
                    -1 -> {
                        val pm = getSystemService(Context.POWER_SERVICE) as PowerManager
                        val wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP, "CHESS")
                        wl.acquire()
                    }
                }
            }
        }
        mediaSession!!.setPlaybackToRemote(myVolumeProvider)
        mediaSession!!.isActive = true
    }
}