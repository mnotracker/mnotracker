package com.github.mnotracker.ui

import android.support.v4.app.Fragment

import com.github.mnotracker.R

class MainFragment extends Fragment {

  import android.os.Bundle
  import android.view.LayoutInflater
  import android.view.ViewGroup

  import com.github.mnotracker.Common.logd

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle) = {
    logd("MainFragment.onCreateView")
    inflater.inflate(R.layout.settings, container, false)
  }

}

object MainFragment {
  val titleStringId: Int = R.string.services
}
