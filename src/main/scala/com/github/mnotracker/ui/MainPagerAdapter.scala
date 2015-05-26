package com.github.mnotracker.ui

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import com.github.mnotracker.R

import scala.collection.immutable.Vector

class MainPagerAdapter(val fm: FragmentManager, val context: Context) extends FragmentPagerAdapter(fm) {

  lazy val fragments = Vector(
    (new MainFragment, MainFragment.titleStringId),
    (new SettingsFragment, SettingsFragment.titleStringId),
    (new AboutFragment, AboutFragment.titleStringId)
  )

  override def getCount() = fragments.length
  override def getItem(position: Int): Fragment = fragments(position)._1
  override def getPageTitle(position: Int) = context.getString(fragments(position)._2)

}
