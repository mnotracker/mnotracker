package com.github.mnotracker.receivers

import android.content.BroadcastReceiver

class NetworkChangeReceiver extends BroadcastReceiver {

  import android.content.Context
  import android.content.Intent
  import com.github.mnotracker.Common._

  override def onReceive(context: Context, intent: Intent) = {
    logi("NetworkChangeReceiver.onReceive on=" + isNetworkOn(context))
  }

}
