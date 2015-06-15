package com.github.mnotracker.clients

import android.content.Context

class Tele2Client()(implicit ctx: Context) extends OperatorClient {

  override def openLoginPage(): Unit = ()

  override def startSync(): Unit = loadUrl("https://login.tele2.ru/")

  override val beforeGetRequestCallbacks: Map[String, () => Unit] = Map()

  override val afterAnyRequestCallbacks: Map[String, () => Unit] = Map()

}
