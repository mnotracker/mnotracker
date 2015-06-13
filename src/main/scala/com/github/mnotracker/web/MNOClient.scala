package com.github.mnotracker.web

import android.content.Context
import android.webkit.{WebView, WebViewClient}

abstract class MNOClient(implicit ctx: Context) extends WebView(ctx) {

  import android.webkit.JavascriptInterface

  import com.github.mnotracker.Logs.logd

  val JS_INTERFACE = "HTMLOUT"

  def startSync()

  lazy val wvClient = new WebViewClient() {

    override def onPageFinished(view: WebView, url: String): Unit = {
      logd(s"onPageFinished $url")
      view.loadUrl(s"javascript:window.${JS_INTERFACE}.processHTML('$url','<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
    }

  }

  private def initWebView(): Unit = {
    setClickable(false)
    getSettings().setJavaScriptEnabled(true)
    addJavascriptInterface(this, JS_INTERFACE)
    setWebViewClient(wvClient);
  }

  @JavascriptInterface
  def processHTML(url: String, html: String): Unit = logd(s"processHTML '$url' => $html")

  initWebView()

}
