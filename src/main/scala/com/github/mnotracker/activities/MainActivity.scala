package com.github.mnotracker.activities

import android.support.v4.app.FragmentActivity

import com.github.mnotracker.{R, TypedViewHolder}

class MainActivity extends FragmentActivity with TypedViewHolder {

  import android.content.Context
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

  private val pagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), this)

  override def onCreate(bundle: Bundle) = {
    logd("MainActivity.onCreate")
    super.onCreate(bundle)
    setContentView(R.layout.main)
    createTabs()
  }

  /*override def onCreateOptionsMenu(menu: Menu): Boolean = {
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
  }*/

  private def createTabs() = {
    import android.support.v4.view.ViewPager
    val viewPager = (findViewById(R.id.pager)) match {
      case vp: ViewPager => vp
    }
    viewPager.setAdapter(pagerAdapter)
  }

}
