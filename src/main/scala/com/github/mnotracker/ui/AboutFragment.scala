package com.github.mnotracker.ui

import android.support.v4.app.Fragment

import com.github.mnotracker.R

class AboutFragment extends Fragment {

  import android.os.Bundle
  import android.view.LayoutInflater
  import android.view.ViewGroup

  import com.github.mnotracker.Logs.logd

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle) = {
    logd("AboutFragment.onCreateView")
    inflater.inflate(R.layout.about, container, false)
  }

}

object AboutFragment {
  val titleStringId: Int = R.string.about
}
