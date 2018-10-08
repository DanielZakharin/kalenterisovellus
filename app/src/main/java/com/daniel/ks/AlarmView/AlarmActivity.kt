package com.daniel.ks.AlarmView

import android.arch.lifecycle.Observer
import android.media.MediaPlayer
import com.daniel.ks.R
import com.daniel.ks.Utils.BaseActivity
import com.daniel.ks.databinding.AlarmActivityBinding
import android.media.RingtoneManager
import android.media.Ringtone
import android.os.Bundle
import com.daniel.ks.Utils.ALARM_EVENT_ID


class AlarmActivity : BaseActivity<AlarmActivityBinding>(R.layout.alarm_activity) {
    lateinit var mp : MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent?.getIntExtra(ALARM_EVENT_ID, -1)?.let {
            if(it > 0) {
                vm.DB.eventDao().getEventWithId(it).observe(this, Observer {
                    binding.event = it
                })
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mp = MediaPlayer.create(this, RingtoneManager.getActualDefaultRingtoneUri(applicationContext, RingtoneManager.TYPE_RINGTONE))
        mp.start()
    }

    override fun onPause() {
        super.onPause()
        mp.stop()
    }
}