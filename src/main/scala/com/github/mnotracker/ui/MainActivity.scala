package com.github.mnotracker.ui

import android.support.v4.app.FragmentActivity

import com.github.mnotracker.{R, TypedFindView}

class MainActivity extends FragmentActivity with TypedFindView with ActivityUtils {

  import android.os.Bundle
  import android.support.v4.view.ViewPager

  import com.github.mnotracker.Logs.logd

  private lazy val pager = find[ViewPager](R.id.pager)

  override def onCreate(bundle: Bundle) = {
    logd("MainActivity.onCreate")
    super.onCreate(bundle)

    setContentView(R.layout.main)
    createTabs()

    if (MainActivity.needRestoreToTab()) {
      pager setCurrentItem MainActivity.settingsTab
      MainActivity.resetRestoreToTab()
    }
    /*{
      import scala.concurrent.Future
      import scala.concurrent.ExecutionContext.Implicits.global
      Future {
        Thread.sleep(5000)
        import com.github.mnotracker.ContextUtils
        ContextUtils runOnUIThread { Notifications.notify(getApplicationContext(), "Yep") }
      }
    }*/
  }

  override def onResume() = {
    logd("MainActivity.onResume")
    super.onResume()

    Notifications.cancelAll(this)
  }

  private def createTabs() = {
    val adapter = new MainPagerAdapter(getSupportFragmentManager(), this)
    pager setAdapter adapter
  }

}

object MainActivity {

  val (noneTab, servicesTab, settingsTab, aboutTab) = (-1, 0, 1, 2)
  private var restoreToTab = noneTab

  def restoreTo(tab: Int) = restoreToTab = tab
  def resetRestoreToTab() = restoreToTab = noneTab
  def needRestoreToTab() = restoreToTab != noneTab

}
