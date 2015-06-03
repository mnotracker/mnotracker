package com.github.mnotracker

import android.content.Context

import scala.util.Try

object ContextUtils {

  import android.os.{Handler, Looper}

  import com.github.mnotracker.Logs.loge

  import scala.concurrent.{Future, Promise}

  lazy val handler = new Handler(Looper.getMainLooper)
  lazy val uiThread = Looper.getMainLooper.getThread

  def runOnUIThread(f: => Unit): Unit =
    if (uiThread == Thread.currentThread)
      f
    else
      handler post {
        new Runnable() {
          def run() = { f }
        }
      }

  def evalOnUIThread[T](f: => T): Future[T] =
    if (uiThread == Thread.currentThread) {
      Future.fromTry(Try(f))
    } else {
      val p = Promise[T]()
      handler.post(new Runnable() {
        def run() = {
          p.complete(Try(f))
        }
      })
      p.future
    }

  def phoneNumber(implicit context: Context) =
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

  def appVersion(implicit context: Context) =
    Try {
      context
        .getPackageManager()
        .getPackageInfo(context.getPackageName(), 0)
        .versionName
    } getOrElse {
      "(unknown version)"
    }

  def isNetworkOn(implicit context: Context) = {
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
        loge("ConnectivityManager is not found")
        false
    }
  }

}
