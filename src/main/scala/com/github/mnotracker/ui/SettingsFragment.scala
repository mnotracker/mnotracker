package com.github.mnotracker.ui

import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.preference.Preference.OnPreferenceClickListener
import android.support.v4.preference.PreferenceFragment

import com.github.mnotracker.{R, Settings}

class SettingsFragment extends PreferenceFragment
                       with OnSharedPreferenceChangeListener with OnPreferenceClickListener {

  import android.content.{Context, Intent, SharedPreferences}
  import android.os.Bundle
  import android.preference.{Preference, PreferenceCategory}
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
    case Settings.DARK_THEME_ON => MainActivity.restartApplication(Some(getActivity()), MainActivity.Tab.Settings)
    case _ => // TODO
  }

  override def onPreferenceClick(pref: Preference): Boolean = {
    val phoneNumber = pref.getKey()
    logd(s"SettingsFragment.onPreferenceClick '$phoneNumber'")
    val intent = new Intent(getActivity(), classOf[NewAccountActivity])
    intent.putExtra(NewAccountActivity.PHONE_NUMBER, phoneNumber)
    getActivity().startActivity(intent)
    true
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
      pref.setOnPreferenceClickListener(this)
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

}
