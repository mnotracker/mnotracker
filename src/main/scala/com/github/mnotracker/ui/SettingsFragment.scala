package com.github.mnotracker.ui

import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.support.v4.preference.PreferenceFragment

import com.github.mnotracker.{R, Settings}

class SettingsFragment extends PreferenceFragment with OnSharedPreferenceChangeListener {

  import android.content.{Context, SharedPreferences}
  import android.os.Bundle
  import android.preference.{Preference, PreferenceCategory, SwitchPreference}
  import android.view.LayoutInflater
  import android.view.ViewGroup

  import com.github.mnotracker.Logs.logd

  private implicit def ctx: Context = getActivity()

  override def onCreate(savedInstanceState: Bundle) = {
    logd("SettingsFragment.onCreate")
    super.onCreate(savedInstanceState)
    addPreferencesFromResource(R.xml.preferences)
    addAccounts()
  }

  override def onResume() = {
    logd("SettingsFragment.onResume")
    super.onResume()

    if (SettingsFragment.dirtyAccounts) {
      SettingsFragment.dirtyAccounts = false
      MainActivity restoreTo MainActivity.settingsTab
      restartApplication()
    }

    getPreferenceManager()
      .getSharedPreferences()
      .registerOnSharedPreferenceChangeListener(this)
  }

  override def onPause() = {
    logd("SettingsFragment.onPause")
    super.onPause()
    getPreferenceManager()
      .getSharedPreferences()
      .unregisterOnSharedPreferenceChangeListener(this)
  }

  override def onSharedPreferenceChanged(sharedPref: SharedPreferences, key: String) = key match {
    case Settings.DARK_THEME_ON => restartApplication()
    case _ => // TODO
  }

  private def restartApplication() = {
    import android.content.Intent

    logd("SettingsFragment.restartApplication")
    val activity = getActivity()
    val intent = new Intent(activity, classOf[MainActivity])
    activity.finish()
    startActivity(intent)
  }

  private def addAccounts() = {
    val category = accountsCategory()
    for {
      phoneNumber <- Settings.accounts()
      operatorText = Settings.accountOperatorText(phoneNumber)
      enabled = Settings.isAccountEnabled(phoneNumber)
      title = s"$operatorText $phoneNumber"
    } yield {
      logd(s"adding preference '$phoneNumber'")
      val pref = new Preference(getActivity())
      pref.setKey(phoneNumber)
      pref.setTitle(title)
      if (!enabled) {
        pref.setSummary(getString(R.string.off))
      }
      category.addPreference(pref)
    }
  }

  private def accountsCategory() = findPref[PreferenceCategory]("accounts_category")
  private def findPref[P <: Preference](key: String): P = findPreference(key).asInstanceOf[P]

}

object SettingsFragment {
  val titleStringId: Int = R.string.settings
  var dirtyAccounts = false
}
