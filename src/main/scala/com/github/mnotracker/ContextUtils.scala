package com.github.mnotracker

import android.content.Context

import scala.util.Try

object ContextUtils {

  import android.os.{Handler, Looper}

  import com.github.mnotracker.Logs.loge

  import scala.concurrent.{Future, Promise}

  lazy val handler = new Handler(Looper.getMainLooper)
  lazy val uiThread = Looper.getMainLooper.getThread

  def getString(id: Int)(implicit ctx: Context): String = ctx.getString(id)

  def runOnUIThread(f: => Unit, delay: Int = 0): Unit =
    if (uiThread == Thread.currentThread) {
      f
    } else {
      val runnable = new Runnable() {
        override def run() = f
      }
      if (delay > 0) {
        handler postDelayed (runnable, delay)
      } else {
        handler post runnable
      }
    }

  def evalOnUIThread[T](f: => T, delay: Int = 0): Future[T] =
    if (uiThread == Thread.currentThread) {
      Future.fromTry(Try(f))
    } else {
      val p = Promise[T]()
      val runnable = new Runnable() {
        override def run() = p.complete(Try(f))
      }
      if (delay > 0) {
        handler postDelayed (runnable, delay)
      } else {
        handler post runnable
      }
      p.future
    }

  def phoneNumber()(implicit ctx: Context): Option[String] =
    Try[String] {
      import android.telephony.TelephonyManager

      def throwEx() = throw new NoSuchElementException
      val service = ctx.getSystemService(Context.TELEPHONY_SERVICE)
      service match {
        case tm: TelephonyManager =>
          val number = tm.getLine1Number
          if (number.isEmpty) throwEx()
          else number
        case None => throwEx()
      }
    }.toOption

  def appVersion()(implicit ctx: Context): String =
    Try[String] {
      ctx
        .getPackageManager()
        .getPackageInfo(ctx.getPackageName(), 0)
        .versionName
    } getOrElse {
      "(unknown version)"
    }

  def isNetworkOn()(implicit ctx: Context): Boolean = Try {
    import android.net.ConnectivityManager
    import android.net.NetworkInfo

    val service = ctx.getSystemService(Context.CONNECTIVITY_SERVICE)
    service match {
      case cm: ConnectivityManager =>
        val info: NetworkInfo = cm.getActiveNetworkInfo()
        lazy val netType = info.getType()
        lazy val isWiFi = netType == ConnectivityManager.TYPE_WIFI || netType == ConnectivityManager.TYPE_WIMAX
        val correctNetType = !Settings.onlyViaWifi() || isWiFi
        correctNetType && info.isConnectedOrConnecting()
      case None => false
    }
  } getOrElse false

}
