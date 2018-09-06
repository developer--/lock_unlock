package com.example.kot.lockunlock.shake

import android.app.admin.DevicePolicyManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.PowerManager
import android.util.Log

class ShakeReceiver : BroadcastReceiver() {

    companion object {
        var isLocked = false
    }

    override fun onReceive(context: Context, intent: Intent?) {

        if (null != intent && intent.action == "shake.detector") {
            val mgr: DevicePolicyManager by lazy { context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager }

            Log.e("shake", " shake")
            when (isLocked) {
                false -> mgr.lockNow()
                true -> {
                    val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
                    val wl = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK or PowerManager.ACQUIRE_CAUSES_WAKEUP, "CHESS")
                    wl.acquire()
                }
            }
            isLocked = !isLocked
        }
    }
}