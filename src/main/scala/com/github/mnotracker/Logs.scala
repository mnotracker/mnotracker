package com.github.mnotracker

object Logs {

  import android.util.Log

  private val LOG_TAG = "mnotracker"

  def loge(text: String) = Log.e(LOG_TAG, text)
  def logw(text: String) = Log.w(LOG_TAG, text)
  def logi(text: String) = Log.i(LOG_TAG, text)
  def logd(text: String) = Log.d(LOG_TAG, text)

}
