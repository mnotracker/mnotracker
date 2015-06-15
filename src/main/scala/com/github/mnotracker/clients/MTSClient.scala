package com.github.mnotracker.clients

import android.content.Context

class MTSClient()(implicit ctx: Context) extends OperatorClient {

  import com.github.mnotracker.Logs.logd

  override def openLoginPage(): Unit = loadUrl("https://login.mts.ru")

  override def startSync(): Unit = ()

  override val beforeGetRequestCallbacks: Map[String, () => Unit] = Map()

  override val afterAnyRequestCallbacks: Map[String, () => Unit] = Map(
    "https://login.mts.ru/amserver/UI/Login" -> (() => logd("get hidden type '" + getInputFieldByName("csrf.sign") + "'"))
  )

}
