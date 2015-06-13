package com.github.mnotracker.web

import android.content.Context

class Tele2Client()(implicit ctx: Context) extends MNOClient {

  override def startSync(): Unit = loadUrl("https://login.tele2.ru/")

}
