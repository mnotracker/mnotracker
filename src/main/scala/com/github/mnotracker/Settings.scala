package com.github.mnotracker

object Settings {

  import android.preference.PreferenceManager
  import android.content.Context

  import com.github.mnotracker.Logs.{logd, loge}

  import org.json.JSONObject

  import scala.collection.JavaConversions.{asScalaIterator, asScalaSet, setAsJavaSet}
  import scala.util.Try

  val ONLY_BAD_NEWS = "only_bad_news"
  val DARK_THEME_ON = "dark_theme_on"
  val ONLY_VIA_WIFI = "only_via_wifi"
  val TELEMETRY_ON = "telemetry_on"

  val ACCOUNTS = "accounts"
  val PASSWORD = "password"
  val OPERATOR = "operator"
  val ENABLED = "enabled"

  object OPERATORS {
    val MEGAFON = "megafon"
    val MTS = "mts"
    val BEELINE = "beeline"
    val TELE2 = "tele2"
  }

  def onlyBadNews()(implicit ctx: Context) = getBoolean(ONLY_BAD_NEWS, true)
  def darkThemeOn()(implicit ctx: Context) = getBoolean(DARK_THEME_ON, false)
  def onlyViaWifi()(implicit ctx: Context) = getBoolean(ONLY_VIA_WIFI, false)
  def telemetryOn()(implicit ctx: Context) = getBoolean(TELEMETRY_ON, true)

  def addAccount(phoneNumber: String, password: String, operator: String)(implicit ctx: Context): Unit = {
    logd(s"addAccount '$phoneNumber' '$password' '$operator'")

    val jsonArray = new JSONObject()
      .put(PASSWORD, password)
      .put(OPERATOR, operator)
      .put(ENABLED, true)
      //.put(STATE, )
      //.put(LAST_DATA_FETCH, )

    val success = sharedPreferences()
      .edit()
      .putString(phoneNumber, jsonArray.toString())
      .putStringSet(ACCOUNTS, getStringSet(ACCOUNTS) ++ Set(phoneNumber))
      .commit()

    logd("accounts commit " + (if (success) "OK" else "FAILED"))
  }

  def accounts()(implicit ctx: Context) = getStringSet(ACCOUNTS)
  def isAccountEnabled(account: String)(implicit ctx: Context) = accountObject(account).getBoolean(ENABLED)
  def accountPassword(account: String)(implicit ctx: Context) = accountObject(account).getString(PASSWORD)
  def accountOperator(account: String)(implicit ctx: Context) = accountObject(account).getString(OPERATOR)
  def accountOperatorText(account: String)(implicit ctx: Context) = ctx.getString(
    accountOperator(account) match {
      case Settings.OPERATORS.MEGAFON => R.string.megafon
      case Settings.OPERATORS.MTS => R.string.mts
      case Settings.OPERATORS.BEELINE => R.string.beeline
      case Settings.OPERATORS.TELE2 => R.string.tele2
    }
  )

  private def accountObject(account: String)(implicit ctx: Context) = new JSONObject(sharedPreferences().getString(account, ""))

  private def sharedPreferences()(implicit ctx: Context) = PreferenceManager.getDefaultSharedPreferences(ctx)

  private def getBoolean(key: String, default: Boolean)(implicit ctx: Context) = sharedPreferences().getBoolean(key, default)
  private def getString(key: String, default: String)(implicit ctx: Context) = sharedPreferences().getString(key, default)

  private def getStringSet(key: String, default: Set[String] = Set[String]())(implicit ctx: Context): Set[String] = {
    val javaSet = sharedPreferences().getStringSet(key, setAsJavaSet(default))
    val mutableSet = asScalaSet(javaSet)
    mutableSet.toSet
  }

}
