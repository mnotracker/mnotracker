package com.github.mnotracker.ui

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import com.github.mnotracker.R

import scala.collection.immutable.Vector

class MainPagerAdapter(val fm: FragmentManager, val context: Context) extends FragmentPagerAdapter(fm) {

  lazy val fragments = Vector(
    new MainFragment(context),
    new SettingsFragment(context),
    new AboutFragment(context)
  )

  override def getCount() = fragments.length
  override def getItem(position: Int): BaseFragment = fragments(position)
  override def getPageTitle(position: Int) = getItem(position).title()

}
