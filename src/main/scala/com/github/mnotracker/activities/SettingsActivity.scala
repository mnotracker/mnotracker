package com.github.mnotracker.activities

import android.app.Activity
import com.github.mnotracker.{R, TypedViewHolder}

class SettingsActivity extends Activity with TypedViewHolder {

  import android.os.Bundle

  override def onCreate(bundle: Bundle) = {
    super.onCreate(bundle)
    setContentView(R.layout.settings)
  }

}
