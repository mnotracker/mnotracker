package com.github.mnotracker.ui

import android.app.Activity

import com.github.mnotracker.{R, TypedViewHolder}

class NewAccountActivity extends Activity with TypedViewHolder with ActivityUtils {

  import android.os.Bundle

  import com.github.mnotracker.Logs.logd

  override def onCreate(bundle: Bundle) = {
    logd(s"NewAccountActivity.onCreate")
    updateTheme()
    super.onCreate(bundle)
    setContentView(R.layout.accounts)
  }

}
