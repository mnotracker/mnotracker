package com.github.mnotracker

object Settings {

  import android.preference.PreferenceManager
  import android.content.Context

  val ONLY_BAD_NEWS = "only_bad_news"
  val DARK_THEME_ON = "dark_theme_on"
  val ONLY_VIA_WIFI = "only_via_wifi"
  val TELEMETRY_ON = "telemetry_on"

  private def sharedPreferences(context: Context) =
    PreferenceManager.getDefaultSharedPreferences(context)

  def onlyBadNews()(implicit ctx: Context) = sharedPreferences(ctx).getBoolean(ONLY_BAD_NEWS, true)
  def darkThemeOn()(implicit ctx: Context) = sharedPreferences(ctx).getBoolean(DARK_THEME_ON, false)
  def onlyViaWifi()(implicit ctx: Context) = sharedPreferences(ctx).getBoolean(ONLY_VIA_WIFI, false)
  def telemetryOn()(implicit ctx: Context) = sharedPreferences(ctx).getBoolean(TELEMETRY_ON, true)

}
