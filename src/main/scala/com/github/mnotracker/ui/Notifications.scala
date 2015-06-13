package com.github.mnotracker.ui

object Notifications {

  import android.app.NotificationManager
  import android.content.Context

  import com.github.mnotracker.Logs.{logi, loge}

  private val notificationId = 0

  def notify(context: Context, text: String): Unit =
    try {
      import android.app.Notification
      import android.app.PendingIntent
      import android.content.Intent

      import com.github.mnotracker.ContextUtils.getString
      import com.github.mnotracker.R

      logi(s"notification '$text'")

      implicit val ctx = context

      val requestCode = 0
      val flags = PendingIntent.FLAG_UPDATE_CURRENT
      val pintent: PendingIntent = PendingIntent.getActivity(context, requestCode, new Intent(context, classOf[MainActivity]), flags)

      val nb = new Notification.Builder(context)
        .setContentTitle(getString(R.string.app_name))
        .setContentText(text)
        .setContentIntent(pintent)
        .setSmallIcon(R.drawable.ic_launcher)
        // .setLargeIcon(aBitmap) // TODO

      val notif: Notification = nb.build()
      notif.flags |= Notification.FLAG_AUTO_CANCEL
      notificationManager(context).foreach(_.notify(notificationId, notif))
    } catch {
      case ex: Throwable => loge(s"failed to notify ${ex.getMessage}")
    }

    // def cancel(context: Context)(id: Int): Unit = notificationManager(context).foreach { _.cancel(id) }
    def cancelAll(context: Context): Unit = notificationManager(context).foreach { _.cancelAll() }

    private def notificationManager(context: Context): Option[NotificationManager] = {
      val service = context.getSystemService(Context.NOTIFICATION_SERVICE)
      service match {
        case nm: NotificationManager => Option[NotificationManager](nm)
        case None => None
      }
    }

}
