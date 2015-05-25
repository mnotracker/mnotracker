package com.github.mnotracker.activities

import android.app.Activity
import com.github.mnotracker.{R, TypedViewHolder}

class MainActivity extends Activity with TypedViewHolder {

  import android.content.Intent
  import android.os.Bundle
  import android.view.Menu
  import android.view.MenuItem
  import android.view.View

  import com.github.mnotracker.Common._

  import scala.util.Try

  private var globalScheme = R.style.MyTheme_Dark
  /*private val menuIcons = (
    R.style.MyTheme_Dark -> (
      R.drawable.ic_action_about_dark,
      R.drawable.ic_action_settings_dark
    ),
    R.style.MyTheme_Light -> (
        R.drawable.ic_action_about,
        R.drawable.ic_action_settings
    )
  )*/

  override def onCreate(bundle: Bundle) = {
    logd("MainActivity.onCreate")
    super.onCreate(bundle)
    setContentView(R.layout.main)
    //createTabs()
  }

  override def onCreateOptionsMenu(menu: Menu): Boolean = {
    def updateMenuItem(menuItemId: Int, iconId: Int) = Try {
      menu.findItem(menuItemId).setIcon(iconId)
    }
    updateMenuItem(R.id.about_MenuItem, R.drawable.ic_action_about)
    updateMenuItem(R.id.settings_MenuItem, R.drawable.ic_action_settings)
    getMenuInflater().inflate(R.menu.main_menu, menu)
    true
  }

  override def onOptionsItemSelected(item: MenuItem): Boolean = item.getItemId() match {
    case R.id.settings_MenuItem =>
      startActivity(new Intent(this, classOf[SettingsActivity]))
      true
    case R.id.about_MenuItem =>
      startActivity(new Intent(this, classOf[AboutActivity]))
      true
    case _ =>
      super.onOptionsItemSelected(item)
  }

  /*private def createTabs() = {
    import android.support.v4.view.PagerAdapter
    import android.support.v4.view.ViewPager
    import android.view.LayoutInflater
    import android.view.ViewGroup
    val pageAdapter = new PagerAdapter {
      override def getCount() = 1
      override def initiateItem(container: ViewGroup, position: Int) = {
        val view = getLayoutInflater().inflate(R.layout.pager_item, container, false)
        container.addView(view)
        view
      }
      override def isViewFromObject(view: View, obj: Object) = obj == view
    }
  }

  private def createTabs() = {
    import android.app.ActionBar
    import android.app.FragmentTransaction
    val SETTINGS_TAB_DESCR = "settings_tab"
    val bar = getActionBar()
    bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS)

    val tabListener = new ActionBar.TabListener {
      override def onTabSelected(tab: ActionBar.Tab, ft: FragmentTransaction) = {}
      override def onTabUnselected(tab: ActionBar.Tab, ft: FragmentTransaction) = {}
      override def onTabReselected(tab: ActionBar.Tab, ft: FragmentTransaction) = {}
    }
    bar.addTab(
      bar.newTab()
        .setText(getString(R.string.settings))
        .setContentDescription(SETTINGS_TAB_DESCR)
        .setTabListener(tabListener)
    )
  }*/

}
