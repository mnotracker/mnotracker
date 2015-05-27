package com.github.mnotracker.ui

import android.content.SharedPreferences.OnSharedPreferenceChangeListener
import android.support.v4.preference.PreferenceFragment

import com.github.mnotracker.R

class SettingsFragment extends PreferenceFragment with OnSharedPreferenceChangeListener {

  import android.content.SharedPreferences
  import android.os.Bundle
  import android.view.LayoutInflater
  import android.view.ViewGroup

  import com.github.mnotracker.Logs.logd

  override def onCreate(savedInstanceState: Bundle) = {
    logd("SettingsFragment.onCreate")
    super.onCreate(savedInstanceState)
    addPreferencesFromResource(R.xml.preferences)
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

  override def onSharedPreferenceChanged(sharedPref: SharedPreferences, key: String) = {
    logd(s"SettingsFragment.onSharedPreferenceChanged $key")
  }

}

object SettingsFragment {
  val titleStringId: Int = R.string.settings
}
