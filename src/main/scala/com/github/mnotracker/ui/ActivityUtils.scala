package com.github.mnotracker.ui

import android.app.Activity

trait ActivityUtils <: Activity {

  import android.content.Context
  import android.os.Bundle
  import android.view.View
  import android.widget.Button

  implicit val ctx: Context = this

  override def onCreate(bundle: Bundle) = {
    updateTheme()
    super.onCreate(bundle)
  }

  def find[V <: View](id: Int): V = findViewById(id).asInstanceOf[V]

  def setButtonHandler(button: Button, handler: () => Unit) =
    button setOnClickListener {
      new View.OnClickListener() {
        override def onClick(v: View) = handler()
      }
    }

  private def updateTheme() = {
    import com.github.mnotracker.{R, Settings}

    val theme =
      if (Settings.darkThemeOn()) R.style.MyTheme_Dark
      else R.style.MyTheme_Light
    setTheme(theme)
  }

}
