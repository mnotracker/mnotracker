package com.github.mnotracker

import android.content.Context

class ContextUtils(context: Context) {

  import com.github.mnotracker.Logs.loge

  import scala.util.Try

  def phoneNumber() =
    Try {
      import android.telephony.TelephonyManager
      val service = context.getSystemService(Context.TELEPHONY_SERVICE)
      service match {
        case tm: TelephonyManager => Option[String](tm.getLine1Number) getOrElse ""
        case _ => ""
      }
    } getOrElse {
      ""
    }

  def appVersion() =
    Try {
      context
        .getPackageManager()
        .getPackageInfo(context.getPackageName(), 0)
        .versionName
    } getOrElse {
      "(unknown version)"
    }

  def isNetworkOn() = {
    import android.net.ConnectivityManager
    import android.net.NetworkInfo
    val service = context.getSystemService(Context.CONNECTIVITY_SERVICE)
    service match {
      case cm: ConnectivityManager =>
        Option[NetworkInfo](cm.getActiveNetworkInfo()) match {
          case Some(info) =>
            info.getType() match {
              case netType: Int =>
                val wrongNetType =
                  Settings.onlyViaWifi(context) &&
                  netType != ConnectivityManager.TYPE_WIFI &&
                  netType != ConnectivityManager.TYPE_WIMAX
                if (wrongNetType)
                  false
                else
                  info.isConnectedOrConnecting()
            }
          case None => false
        }
      case None =>
        loge("ConnectivityManager not found")
        false
    }
  }

}

object ContextUtils {

  import android.os.{Handler, Looper}

  def apply(context: Context) = new ContextUtils(context)

  lazy val handler = new Handler(Looper.getMainLooper)
  lazy val uiThread = Looper.getMainLooper.getThread

  def runOnUi(f: => Unit): Unit =
    if (uiThread == Thread.currentThread)
      f
    else
      handler
        .post(new Runnable() {
          def run() { f }
        })
}
