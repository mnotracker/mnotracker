package com.github.mnotracker

object Common {

  import android.content.Context
  import android.util.Log

  import scala.util.Try

  private val LOG_TAG = "mnotracker"

  def loge(text: String) = Log.e(LOG_TAG, text)
  def logw(text: String) = Log.w(LOG_TAG, text)
  def logi(text: String) = Log.i(LOG_TAG, text)
  def logd(text: String) = Log.d(LOG_TAG, text)

  def phoneNumber(context: Context) =
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

  def appVersion(context: Context) =
    Try {
      context
        .getPackageManager()
        .getPackageInfo(context.getPackageName(), 0)
        .versionName
    } getOrElse {
      "(unknown version)"
    }

  def isNetworkOn(context: Context) = {
    import android.net.ConnectivityManager
    import android.net.NetworkInfo
    val service = context.getSystemService(Context.CONNECTIVITY_SERVICE)
    service match {
      case cm: ConnectivityManager =>
        Option[NetworkInfo](cm.getActiveNetworkInfo()) match {
          case Some(info) =>
            info.getType() match {
              case netType: Int =>
                if (Settings.onlyViaWifi(context) && netType != ConnectivityManager.TYPE_WIFI && netType != ConnectivityManager.TYPE_WIMAX)
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
