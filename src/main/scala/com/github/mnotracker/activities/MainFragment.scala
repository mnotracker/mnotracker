package com.github.mnotracker.activities

import android.content.Context
import android.os.Bundle

import com.github.mnotracker.R

class MainFragment(context: Context) extends BaseFragment(context) {

  import android.view.LayoutInflater
  import android.view.ViewGroup

  import com.github.mnotracker.Common._

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle) = {
    logd("MainFragment.onCreateView")
    inflater.inflate(R.layout.settings, container, false)
  }

  override def title() = context.getString(R.string.services)

}
