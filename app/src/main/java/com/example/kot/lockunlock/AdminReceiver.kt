package com.example.kot.lockunlock

import android.app.admin.DeviceAdminReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class AdminReceiver : DeviceAdminReceiver() {

    /** Called when this application is approved to be a device administrator.  */
    override fun onEnabled(context: Context, intent: Intent) {
        super.onEnabled(context, intent)
        Toast.makeText(context, "onEnabled",
                Toast.LENGTH_LONG).show()
        Log.d(TAG, "onEnabled")
    }

    /** Called when this application is no longer the device administrator.  */
    override fun onDisabled(context: Context, intent: Intent) {
        super.onDisabled(context, intent)
        Toast.makeText(context, "onDisabled",
                Toast.LENGTH_LONG).show()
        Log.d(TAG, "onDisabled")
    }

    companion object {
        internal val TAG = "AdminReceiver"
    }

}