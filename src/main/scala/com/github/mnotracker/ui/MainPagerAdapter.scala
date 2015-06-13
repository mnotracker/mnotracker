package com.github.mnotracker.ui

import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class MainPagerAdapter(fm: FragmentManager, context: Context) extends FragmentPagerAdapter(fm) {

  import android.support.v4.app.Fragment

  import com.github.mnotracker.ContextUtils.getString
  import com.github.mnotracker.R

  import scala.collection.immutable.Vector

  implicit val ctx: Context = context

  lazy val fragments = Vector(
    (new ServicesFragment, ServicesFragment.titleStringId),
    (new SettingsFragment, SettingsFragment.titleStringId),
    (new AboutFragment, AboutFragment.titleStringId)
  )

  override def getCount(): Int = fragments.length
  override def getItem(position: Int): Fragment = fragments(position)._1
  override def getPageTitle(position: Int): String = getString(fragments(position)._2)

}
