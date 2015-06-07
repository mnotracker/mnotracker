package com.github.mnotracker.ui

object Notifications {

  import android.content.Context

  import com.github.mnotracker.Logs.{logi, loge}

  def notify(context: Context, text: String) =
    try {
      import android.app.Notification
      import android.app.NotificationManager
      import android.app.PendingIntent
      import android.content.Intent

      import com.github.mnotracker.R

      logi(s"notification '$text'")

      val requestCode = 0
      val flags = 0
      val id = 0

      val pintent: PendingIntent = PendingIntent.getActivity(context, requestCode, new Intent(context, classOf[MainActivity]), flags)

      val nb = new Notification.Builder(context)
        .setContentTitle(context.getString(R.string.app_name))
        .setContentText(text)
        .setContentIntent(pintent)
        .setSmallIcon(R.drawable.ic_launcher)
        //.setLargeIcon(aBitmap)

      val notif: Notification = nb.build()
      notif.flags |= Notification.FLAG_AUTO_CANCEL

      val service = context.getSystemService(Context.NOTIFICATION_SERVICE)
      service match {
        case nm: NotificationManager => nm.notify(id, notif)
        case None => loge("NotificationManager is not found")
      }
    } catch {
      case ex: Throwable => loge(s"failed to notify ${ex.getMessage}")
    }

}
