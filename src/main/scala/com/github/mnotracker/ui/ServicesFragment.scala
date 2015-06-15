package com.github.mnotracker.ui

import android.content.Context
import android.support.v4.app.Fragment

import com.github.mnotracker.Logs.logd
import com.github.mnotracker.R
import com.github.mnotracker.clients.{MTSClient, Tele2Client}

class ServicesFragment extends Fragment {

  import android.os.Bundle
  import android.view.{LayoutInflater, View, ViewGroup}

  implicit lazy val ctx: Context = getActivity()
  lazy val client = new MTSClient()

  override def onCreateView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle): View = {
    logd("ServicesFragment.onCreateView")

    // client.openLoginPage()

    inflater.inflate(R.layout.services, container, false)
  }

}

object ServicesFragment {

  val titleStringId: Int = R.string.services

}
