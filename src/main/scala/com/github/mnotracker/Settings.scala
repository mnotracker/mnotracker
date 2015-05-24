package com.github.mnotracker

object Settings {

  import android.preference.PreferenceManager
  import android.content.Context

  val ONLY_BAD_NEWS = "only_bad_news"
  val DARK_THEME_ON = "dark_theme_on"
  val ONLY_VIA_WIFI = "only_via_wifi"
  val TELEMETRY_ON = "telemetry_on"

  private def sharedPreferences(context: Context) = PreferenceManager.getDefaultSharedPreferences(context)

  def onlyBadNews(context: Context) = sharedPreferences(context).getBoolean(ONLY_BAD_NEWS, true)
  def darkThemeOn(context: Context) = sharedPreferences(context).getBoolean(DARK_THEME_ON, false)
  def onlyViaWifi(context: Context) = sharedPreferences(context).getBoolean(ONLY_VIA_WIFI, false)
  def telemetryOn(context: Context) = sharedPreferences(context).getBoolean(TELEMETRY_ON, true)

}
