package com.github.mnotracker

import android.app.Activity
import android.os.Bundle

class MainActivity extends Activity with TypedViewHolder {

  override def onCreate(bundle: Bundle) = {
    super.onCreate(bundle)
    setContentView(R.layout.main)
  }

}
