package com.github.mnotracker.ui

import android.content.Context
import android.os.Bundle

import com.github.mnotracker.R

class AboutFragment(context: Context) extends BaseFragment(context) {

  import android.view.LayoutInflater
  import android.view.ViewGroup

  import com.github.mnotracker.Common._

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle) = {
    logd("AboutFragment.onCreateView")
    inflater.inflate(R.layout.about, container, false)
  }

  override def title() = context.getString(R.string.about)

}
