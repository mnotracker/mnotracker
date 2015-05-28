package com.github.mnotracker.ui

import android.app.Activity

trait ActivityUtils <: Activity {

  import android.view.View

  def find[V <: View](id: Int): V = findViewById(id).asInstanceOf[V]

  def updateTheme() = {
    import com.github.mnotracker.{R, Settings}
    val theme =
      if (Settings.darkThemeOn(this)) R.style.MyTheme_Dark
      else R.style.MyTheme_Light
    setTheme(theme)
  }

}
