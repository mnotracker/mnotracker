package com.github.mnotracker.ui

import android.content.Context
import android.os.Bundle

import com.github.mnotracker.R

class SettingsFragment(context: Context) extends BaseFragment(context) {

  import android.view.LayoutInflater
  import android.view.ViewGroup

  import com.github.mnotracker.Common.logd

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle) = {
    logd("SettingsFragment.onCreateView")
    inflater.inflate(R.layout.settings, container, false)
  }

  override def title() = context.getString(R.string.settings)

}
