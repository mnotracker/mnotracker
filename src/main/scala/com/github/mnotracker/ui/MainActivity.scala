package com.github.mnotracker.ui

import android.app.Activity
import android.content.Context
import android.support.v4.app.FragmentActivity

import com.github.mnotracker.{R, TypedFindView}
import com.github.mnotracker.Logs.logd

import scala.util.Try

class MainActivity extends FragmentActivity with TypedFindView with ActivityUtils {

  import android.os.Bundle
  import android.support.v4.view.ViewPager

  private lazy val pager = find[ViewPager](R.id.pager)

  override def onCreate(bundle: Bundle) = {
    logd("MainActivity.onCreate")
    super.onCreate(bundle)

    MainActivity.mainActivity = Some(this)

    setContentView(R.layout.main)
    createTabs()
    maybeRestoreTab()
  }

  override def onResume() = {
    logd("MainActivity.onResume")
    super.onResume()

    Notifications.cancelAll(this)
  }

  override def onDestroy() = {
    logd("MainActivity.onDestroy")
    MainActivity.mainActivity = None
    super.onDestroy()
  }

  private def createTabs() = {
    val adapter = new MainPagerAdapter(getSupportFragmentManager(), this)
    pager setAdapter adapter
  }

  private def maybeRestoreTab() = Try {
    logd(s"restoreTab extras=${getIntent().getExtras()}")
    val tabId = getIntent()
      .getExtras()
      .getInt(MainActivity.TAB, MainActivity.Tab.Services.id)
    pager setCurrentItem tabId
    logd("restoreTab success")
  }

}

object MainActivity {

  val TAB = "tab"

  object Tab extends Enumeration {
    type Tab = Value
    val Services, Settings, About = Value
  }

  private var mainActivity: Option[MainActivity] = None

  def restartApplication(currentActivity: Option[Activity], tab: Tab.Tab)(implicit ctx: Context) = {
    import android.content.Intent

    logd(s"MainActivity.restartApplication mainActivity=$mainActivity")
    val appContext = ctx.getApplicationContext()
    val intent = new Intent(appContext, classOf[MainActivity])
    intent.putExtra(TAB, tab.id)
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    currentActivity foreach { _.finish() }
    mainActivity foreach { _.finish() }
    appContext.startActivity(intent)
  }

}
