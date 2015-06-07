package com.github.mnotracker.ui

import android.app.Activity

trait ActivityUtils <: Activity {

  import android.content.Context
  import android.view.View

  implicit val ctx: Context = this

  def find[V <: View](id: Int): V = findViewById(id).asInstanceOf[V]

  def updateTheme() = {
    import com.github.mnotracker.{R, Settings}
    val theme =
      if (Settings.darkThemeOn()) R.style.MyTheme_Dark
      else R.style.MyTheme_Light
    setTheme(theme)
  }

}
