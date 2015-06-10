package com.github.mnotracker.ui

import android.support.v4.app.FragmentActivity

import com.github.mnotracker.{R, TypedFindView}

class MainActivity extends FragmentActivity with TypedFindView with ActivityUtils {

  import android.os.Bundle

  import com.github.mnotracker.Logs.logd

  override def onCreate(bundle: Bundle) = {
    logd("MainActivity.onCreate")
    super.onCreate(bundle)

    setContentView(R.layout.main)
    createTabs()

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
    import android.support.v4.view.ViewPager

    val adapter = new MainPagerAdapter(getSupportFragmentManager(), this)
    find[ViewPager](R.id.pager) setAdapter adapter
  }

}
