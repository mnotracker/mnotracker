package com.github.mnotracker.ui

import android.app.Activity

import com.github.mnotracker.{R, TypedViewHolder}

class NewAccountActivity extends Activity with TypedViewHolder {

  import android.os.Bundle

  import com.github.mnotracker.Common.logd

  override def onCreate(bundle: Bundle) = {
    logd(s"NewAccountActivity.onCreate")
    super.onCreate(bundle)
    setContentView(R.layout.accounts)
  }

}
