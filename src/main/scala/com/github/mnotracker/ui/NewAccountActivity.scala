package com.github.mnotracker.ui

import android.app.Activity

import com.github.mnotracker.{R, TypedFindView}

class NewAccountActivity extends Activity with TypedFindView with ActivityUtils {

  import android.os.Bundle
  import android.view.View
  import android.widget.Button
  import android.widget.EditText
  import android.widget.RadioButton
  import android.widget.RadioGroup

  import com.github.mnotracker.Logs.logd
  import com.github.mnotracker.Settings

  override def onCreate(bundle: Bundle) = {
    logd("NewAccountActivity.onCreate")
    super.onCreate(bundle)
    setContentView(R.layout.new_account)

    prepareButtons()
    update()
  }

  def update() = {
    logd("NewAccountActivity.update")
    // TODO: validation checks
  }

  def onOperatorClick(view: View) = update()

  private def prepareButtons() = {
    setButtonHandler(
      find[Button](R.id.button_login),
      () => {
        import MainActivity.{restartApplication, Tab}

        Settings.addAccount(
          phoneNumber = phoneNumber(),
          password = password(),
          operator = operator()
        )

        restartApplication(Some(this), Tab.Settings)
      }
    )
  }

  private def phoneNumber() = find[EditText](R.id.edit_phone_number).getText().toString()
  private def password() = find[EditText](R.id.edit_password).getText().toString()

  private def operator() = find[RadioGroup](R.id.radio_group_operators).getCheckedRadioButtonId() match {
    case R.id.radio_megafon => Settings.OPERATORS.MEGAFON
    case R.id.radio_mts => Settings.OPERATORS.MTS
    case R.id.radio_beeline => Settings.OPERATORS.BEELINE
    case R.id.radio_tele2 => Settings.OPERATORS.TELE2
  }

}
