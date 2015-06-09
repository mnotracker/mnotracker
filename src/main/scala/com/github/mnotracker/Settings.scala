package com.github.mnotracker

object Settings {

  import android.preference.PreferenceManager
  import android.content.Context

  import scala.collection.JavaConversions._

  val ONLY_BAD_NEWS = "only_bad_news"
  val DARK_THEME_ON = "dark_theme_on"
  val ONLY_VIA_WIFI = "only_via_wifi"
  val TELEMETRY_ON = "telemetry_on"

  def onlyBadNews()(implicit ctx: Context) = getBoolean(ONLY_BAD_NEWS, true)
  def darkThemeOn()(implicit ctx: Context) = getBoolean(DARK_THEME_ON, false)
  def onlyViaWifi()(implicit ctx: Context) = getBoolean(ONLY_VIA_WIFI, false)
  def telemetryOn()(implicit ctx: Context) = getBoolean(TELEMETRY_ON, true)

  class MobileOperator
  case object MegafonMobileOperator extends MobileOperator
  case object MtsMobileOperator extends MobileOperator
  case object BeelineMobileOperator extends MobileOperator
  case object Tele2MobileOperator extends MobileOperator

  def addAccount(phoneNumber: String, password: String, operator: MobileOperator)(implicit ctx: Context) = {
    import org.json.JSONObject

    val jsonArray = new JSONObject()
      .put("password", password) // TODO define string vals
      .put("operator", operator)
      .put("enabled", true)
      //.put("state", )
      //.put("last_data_fetch", )

    sharedPreferences()
      .edit()
      .putString(phoneNumber, jsonArray.toString())
      .putStringSet("accounts", getStringSet("accounts", Set[String]()))
      .commit()
  }

  private def sharedPreferences()(implicit ctx: Context) =
    PreferenceManager.getDefaultSharedPreferences(ctx)

  private def getBoolean(key: String, default: Boolean)(implicit ctx: Context) = sharedPreferences().getBoolean(key, default)
  private def getString(key: String, default: String)(implicit ctx: Context) = sharedPreferences().getString(key, default)
  private def getStringSet(key: String, default: Set[String])(implicit ctx: Context): Set[String] = {
    val javaSet = sharedPreferences().getStringSet(key, setAsJavaSet(default))
    val mutableSet = asScalaSet(javaSet)
    mutableSet.toSet
  }

}
