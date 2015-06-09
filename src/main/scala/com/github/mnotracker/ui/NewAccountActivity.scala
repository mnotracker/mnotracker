package com.github.mnotracker.ui

import android.app.Activity

import com.github.mnotracker.{R, TypedFindView}

class NewAccountActivity extends Activity with TypedFindView with ActivityUtils {

  import android.os.Bundle
  import android.view.View

  import com.github.mnotracker.Logs.logd

  override def onCreate(bundle: Bundle) = {
    logd("NewAccountActivity.onCreate")
    updateTheme()
    super.onCreate(bundle)
    setContentView(R.layout.new_account)
  }

  def onMnoClicked(view: View) = {
    import android.widget.RadioButton
    logd("NewAccountActivity.onMnoClicked")
    val id = view.asInstanceOf[RadioButton].getId()
    id match {
      case R.id.radio_megafon =>
      case R.id.radio_mts =>
      case R.id.radio_beeline =>
      case R.id.radio_tele2 =>
    }
  }

}
