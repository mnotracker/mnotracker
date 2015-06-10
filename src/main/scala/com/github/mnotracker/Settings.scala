package com.github.mnotracker

object Settings {

  import android.preference.PreferenceManager
  import android.content.Context

  import org.json.JSONObject

  import scala.collection.JavaConversions.{asScalaIterator, asScalaSet, setAsJavaSet}

  val ONLY_BAD_NEWS = "only_bad_news"
  val DARK_THEME_ON = "dark_theme_on"
  val ONLY_VIA_WIFI = "only_via_wifi"
  val TELEMETRY_ON = "telemetry_on"

  val ACCOUNTS = "accounts"
  val PASSWORD = "password"
  val OPERATOR = "operator"
  val ENABLED = "enabled"

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
    val jsonArray = new JSONObject()
      .put(PASSWORD, password)
      .put(OPERATOR, operator)
      .put(ENABLED, true)
      //.put(STATE, )
      //.put(LAST_DATA_FETCH, )

    sharedPreferences()
      .edit()
      .putString(phoneNumber, jsonArray.toString())
      .putStringSet(ACCOUNTS, getStringSet(ACCOUNTS) ++ Set(phoneNumber))
      .commit()
  }

  private def getAccounts()(implicit ctx: Context): Map[String, Map[String, Any]] =
    getStringSet(ACCOUNTS)
      .map(account => (account, getAccount(account)))
      .toMap

  private def getAccount(account: String)(implicit ctx: Context): Map[String, Any] = {
    val obj = new JSONObject(sharedPreferences().getString(account, ""))
    asScalaIterator(obj.keys())
    .map(
      name => (
        name,
        name match {
          case ENABLED => obj.getBoolean(name)
          case _ => obj.getString(name)
        }
      )
    ).toMap
  }

  private def sharedPreferences()(implicit ctx: Context) = PreferenceManager.getDefaultSharedPreferences(ctx)

  private def getBoolean(key: String, default: Boolean)(implicit ctx: Context) = sharedPreferences().getBoolean(key, default)
  private def getString(key: String, default: String)(implicit ctx: Context) = sharedPreferences().getString(key, default)
  private def getStringSet(key: String, default: Set[String] = Set[String]())
                          (implicit ctx: Context): Set[String] = {
    val javaSet = sharedPreferences().getStringSet(key, setAsJavaSet(default))
    val mutableSet = asScalaSet(javaSet)
    mutableSet.toSet
  }

}
