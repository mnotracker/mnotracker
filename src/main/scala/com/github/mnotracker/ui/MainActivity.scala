package com.github.mnotracker.ui

import android.support.v4.app.FragmentActivity

import com.github.mnotracker.{R, TypedViewHolder}

class MainActivity extends FragmentActivity with TypedViewHolder with ActivityUtils {

  import android.content.Context
  import android.content.Intent
  import android.os.Bundle
  import android.view.View

  import com.github.mnotracker.Logs.logd

  import scala.util.Try

  private var globalTheme = R.style.MyTheme_Dark

  override def onCreate(bundle: Bundle) = {
    logd("MainActivity.onCreate")
    setTheme(globalTheme)
    super.onCreate(bundle)
    setContentView(R.layout.main)
    createTabs()
  }

  private def createTabs() = {
    import android.support.v4.view.ViewPager
    val adapter = new MainPagerAdapter(getSupportFragmentManager(), this)
    find[ViewPager](R.id.pager) setAdapter adapter
  }

}
