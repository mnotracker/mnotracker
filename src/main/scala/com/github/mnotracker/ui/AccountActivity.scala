package com.github.mnotracker.ui

import android.app.Activity

import com.github.mnotracker.{R, TypedFindView}

class AccountActivity extends Activity with TypedFindView with ActivityUtils {

  import android.os.Bundle
  import android.view.View
  import android.widget.{Button, EditText, RadioButton, RadioGroup, Switch}

  import com.github.mnotracker.Logs.logd
  import com.github.mnotracker.Settings

  import scala.util.Try

  override def onCreate(bundle: Bundle) = {
    logd("AccountActivity.onCreate")
    super.onCreate(bundle)
    setContentView(R.layout.account)

    maybeLoadAccountData()
    prepareButtons()
    update()
  }

  def update() = {
    logd("AccountActivity.update")
    // TODO: validation checks
  }

  def onOperatorClick(view: View) = update()

  private def maybeLoadAccountData() = Try {
    logd(s"maybeLoadAccountData extras=${getIntent().getExtras()}")
    val phoneNumber = getIntent()
      .getExtras()
      .getString(AccountActivity.PHONE_NUMBER, "")
    loadAccountData(phoneNumber)
    logd("maybeLoadAccountData success")
  } getOrElse {
    logd("new account")
    find[Switch](R.id.switch_account_enable) setVisibility View.INVISIBLE
  }

  private def loadAccountData(phoneNumber: String) = {
    val editPhoneNumber = find[EditText](R.id.edit_phone_number)
    editPhoneNumber setEnabled false
    editPhoneNumber setText phoneNumber

    find[EditText](R.id.edit_password) setText Settings.accountPassword(phoneNumber)
    find[RadioGroup](R.id.radio_group_operators) check operatorStringToRadioId(Settings.accountOperator(phoneNumber))
    find[Switch](R.id.switch_account_enable) setChecked Settings.isAccountEnabled(phoneNumber)
    find[Button](R.id.button_signup) setVisibility View.INVISIBLE
  }

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

        // TODO: start login process
      }
    )
  }

  private def phoneNumber() = find[EditText](R.id.edit_phone_number).getText().toString()
  private def password() = find[EditText](R.id.edit_password).getText().toString()

  private def operator() = operatorRadioIdToString(
    find[RadioGroup](R.id.radio_group_operators).getCheckedRadioButtonId()
  )

  private def operatorRadioIdToString(id: Int): String = id match {
    case R.id.radio_megafon => Settings.OPERATORS.MEGAFON
    case R.id.radio_mts => Settings.OPERATORS.MTS
    case R.id.radio_beeline => Settings.OPERATORS.BEELINE
    case R.id.radio_tele2 => Settings.OPERATORS.TELE2
  }

  private def operatorStringToRadioId(op: String): Int = op match {
    case Settings.OPERATORS.MEGAFON => R.id.radio_megafon
    case Settings.OPERATORS.MTS => R.id.radio_mts
    case Settings.OPERATORS.BEELINE => R.id.radio_beeline
    case Settings.OPERATORS.TELE2 => R.id.radio_tele2
  }

}

object AccountActivity {

  val PHONE_NUMBER = "phoneNumber"

}
