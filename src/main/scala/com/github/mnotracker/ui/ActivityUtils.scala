package com.github.mnotracker.ui

import android.app.Activity

trait ActivityUtils <: Activity {

  import android.view.View

  def find[V <: View](id: Int): V = findViewById(id).asInstanceOf[V]

}
