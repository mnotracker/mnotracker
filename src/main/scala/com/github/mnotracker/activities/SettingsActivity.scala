package com.github.mnotracker.activities

import android.app.Activity
import com.github.mnotracker.{R, TypedViewHolder}

class SettingsActivity extends Activity with TypedViewHolder {

  import android.os.Bundle
  import com.github.mnotracker.Common._

  override def onCreate(bundle: Bundle) = {
    logd("SettingsActivity.onCreate")
    super.onCreate(bundle)
    setContentView(R.layout.settings)
  }

}
