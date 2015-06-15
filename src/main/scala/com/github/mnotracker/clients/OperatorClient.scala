package com.github.mnotracker.clients

import android.content.Context
import android.webkit.{WebView, WebViewClient}

abstract class OperatorClient(implicit ctx: Context) extends WebView(ctx) {

  import android.webkit.JavascriptInterface

  import com.github.mnotracker.Logs.logd

  @volatile private var jsResult: String = ""

  def openLoginPage(): Unit
  def startSync(): Unit
  val beforeGetRequestCallbacks: Map[String, () => Unit]
  val afterAnyRequestCallbacks: Map[String, () => Unit]

  def getInputFieldByName(name: String, itemNumber: Int = 0): String = runAndReturnJs(s"document.getElementsByTagName('$name')[$itemNumber].value")
  def setInputFieldByName(name: String, value: String, itemNumber: Int = 0): Unit = runJs(s"document.getElementsByTagName('$name')[$itemNumber].value = $value")
  def enableInputFieldByName(name: String, enabled: Boolean, itemNumber: Int = 0): Unit =
    runJs(s"document.getElementsByTagName('$name')[$itemNumber].disabled = $enabled === false")

  def runJs(js: String): Unit = loadUrl(s"javascript:$js;")
  def runAndReturnJs(js: String): String = {
    runJsInterfaceMethod(s"setJsResult($js);")
    jsResult
  }
  def runJsInterfaceMethod(methodWithArgs: String): Unit = runJs(s"window.${JS_INTERFACE}.${methodWithArgs}")

  val JS_INTERFACE = "JS_INTERFACE"  // TODO: name can be randomized
  lazy val wvClient = new WebViewClient() {

    override def shouldOverrideUrlLoading(view: WebView, url: String): Boolean = {
      if (beforeGetRequestCallbacks contains url) {
        logd(s"beforeGetRequestCallbacks($url)()")
        beforeGetRequestCallbacks(url)()
      }
      false
    }

    override def onPageFinished(view: WebView, url: String): Unit = {
      logd(s"onPageFinished $url")
      runJsInterfaceMethod(s"processHTML('$url','<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>')")  // TODO: use callback
      if (afterAnyRequestCallbacks contains url) {
        logd(s"afterAnyRequestCallbacks ($url)()")
        afterAnyRequestCallbacks(url)()
      }
    }

  }

  private def initWebView(): Unit = {
    // setClickable(false)
    getSettings().setJavaScriptEnabled(true)
    addJavascriptInterface(this, JS_INTERFACE)
    setWebViewClient(wvClient);
  }

  @JavascriptInterface def processHTML(url: String, html: String): Unit =
    logd(s"processHTML '$url' => $html")

  @JavascriptInterface def setJsResult(result: String): Unit = {
    jsResult = result
  }

  initWebView()

}
