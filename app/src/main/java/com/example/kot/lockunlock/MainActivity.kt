package com.example.kot.lockunlock

import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    private var mgr: DevicePolicyManager? = null
    private var cn: ComponentName? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startService(Intent(this,LockUnlockService::class.java))
        cn = ComponentName(this, AdminReceiver::class.java)
        mgr = getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        lockMeNow()

        disable_lock_unlock_btn.setOnClickListener {
            stopService(Intent(this,LockUnlockService::class.java))
            System.exit(-1)
        }
    }

    private fun lockMeNow() = if (mgr?.isAdminActive(cn) == true) {
    } else {
        val intent = Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN)
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, cn)
        startActivity(intent)
    }

}
