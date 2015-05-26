package com.github.mnotracker.receivers

import android.content.BroadcastReceiver

class NetworkChangeReceiver extends BroadcastReceiver {

  import android.content.Context
  import android.content.Intent

  import com.github.mnotracker.Common.{logd,isNetworkOn}

  override def onReceive(context: Context, intent: Intent) = {
    logd("NetworkChangeReceiver.onReceive on=" + isNetworkOn(context))
  }

}
