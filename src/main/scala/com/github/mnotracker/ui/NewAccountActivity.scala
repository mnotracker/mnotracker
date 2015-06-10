package com.github.mnotracker.ui

import android.app.Activity

import com.github.mnotracker.{R, TypedFindView}

class NewAccountActivity extends Activity with TypedFindView with ActivityUtils {

  import android.os.Bundle
  import android.view.View
  import android.widget.Button
  import android.widget.RadioButton
  import android.widget.EditText

  import com.github.mnotracker.Logs.logd
  import com.github.mnotracker.Settings

  override def onCreate(bundle: Bundle) = {
    logd("NewAccountActivity.onCreate")
    super.onCreate(bundle)
    setContentView(R.layout.new_account)

    setButtonHandler(
      find[Button](R.id.button_login),
      () => Settings.addAccount(
        phoneNumber = phoneNumber(),
        password = password(),
        operator = operator()
      )
    )
  }

  def onMnoClicked(view: View) = {
    logd("NewAccountActivity.onMnoClicked")

    val operatorRadioButtonId = view.asInstanceOf[RadioButton].getId()
    val selectedOperator = operatorRadioButtonId match {
      case R.id.radio_megafon => Settings.OPERATORS.MEGAFON
      case R.id.radio_mts => Settings.OPERATORS.MTS
      case R.id.radio_beeline => Settings.OPERATORS.BEELINE
      case R.id.radio_tele2 => Settings.OPERATORS.TELE2
    }
  }

  private def phoneNumber() = find[EditText](R.id.edit_phone_number).getText().toString()
  private def password() = find[EditText](R.id.edit_password).getText().toString()
  private def operator() = {
    Settings.OPERATORS.MTS // TODO
  }

}
