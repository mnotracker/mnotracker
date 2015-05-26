package com.github.mnotracker.ui

import android.support.v4.app.Fragment

import com.github.mnotracker.R

class ServicesFragment extends Fragment {

  import android.os.Bundle
  import android.view.LayoutInflater
  import android.view.ViewGroup

  import com.github.mnotracker.Common.logd

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle) = {
    logd("ServicesFragment.onCreateView")
    inflater.inflate(R.layout.services, container, false)
  }

}

object ServicesFragment {
  val titleStringId: Int = R.string.services
}
