package com.github.mnotracker.receivers

import android.content.BroadcastReceiver

class NetworkChangeReceiver extends BroadcastReceiver {

  import android.content.Context
  import android.content.Intent

  import com.github.mnotracker.Logs.logd

  override def onReceive(context: Context, intent: Intent) = {
    import com.github.mnotracker.ContextUtils

    implicit val ctx: Context = context

    val isNetworkOn = ContextUtils.isNetworkOn()
    logd(s"NetworkChangeReceiver.onReceive on=$isNetworkOn")
  }

}
