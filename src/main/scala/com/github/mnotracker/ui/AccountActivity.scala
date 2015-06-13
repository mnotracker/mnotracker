package com.github.mnotracker.ui

import android.app.Activity

import com.github.mnotracker.{R, TypedFindView}

class AccountActivity extends Activity with TypedFindView with ActivityUtils {

  import android.os.Bundle
  import android.view.View
  import android.widget.{Button, EditText, RadioButton, RadioGroup, Switch}

  import com.github.mnotracker.Logs.{logd, loge}
  import com.github.mnotracker.Settings

  import scala.util.Try

  implicit lazy val activity = this

  override def onCreate(bundle: Bundle): Unit = {
    logd("AccountActivity.onCreate")
    super.onCreate(bundle)
    setContentView(R.layout.account)

    maybeLoadAccountData()
    prepareButtons()
    update()
  }

  def update(): Unit = {
    logd("AccountActivity.update")
    // TODO: validation checks: phone number already exists, no password...
  }

  def onOperatorClick(view: View): Unit = update()

  lazy val phoneNumberExtra: Option[String] = Try {
    val value = getIntent()
      .getExtras()
      .getString(AccountActivity.PHONE_NUMBER, "")
    if (value.isEmpty) throw new NoSuchElementException
    else value
  }.toOption

  private def maybeLoadAccountData() = phoneNumberExtra match {
    case Some(phoneNumber) =>
      loadAccountData(phoneNumber)
      logd("maybeLoadAccountData success")
    case None =>
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
  }

  private def prepareButtons() = {

    setButtonHandler(
      find[Button](R.id.button_login),
      () =>
        if(Settings.addAccount(phoneNumber = phoneNumber(), password = password(), operator = operator(), enabled = enabled())) {
          // TODO: toast
          MainActivity.restartApplication(Some(this), MainActivity.Tab.Settings)
          // TODO: start login process
        } else {
          loge("failed to create account")
        }
    )

    phoneNumberExtra match {
      case Some(phoneNumber) =>
        find[Button](R.id.button_login) setText R.string.apply
        setDeleteButtonHandler(phoneNumber)
      case None =>
        find[Button](R.id.button_delete) setVisibility View.INVISIBLE
    }

    def setDeleteButtonHandler(phoneNumber: String) = {
      val button = find[Button](R.id.button_delete)
      val message = getString(R.string.delete_account_confirmation).format(phoneNumber)
      val (yes, no) = (getString(R.string.yes), getString(R.string.no))
      setButtonHandler(button,
        () => AlertDialogFragment(
          message,
          (yes, () =>
            if (Settings.deleteAccount(phoneNumber)) {
              // TODO: toast
              MainActivity.restartApplication(Some(this), MainActivity.Tab.Settings)
            } else {
              loge("failed to delete account")
            }
          ),
          (no, () => ())
        )
      )
    }

  }

  private def phoneNumber() = find[EditText](R.id.edit_phone_number).getText().toString()
  private def password() = find[EditText](R.id.edit_password).getText().toString()

  private def operator() = operatorRadioIdToString(
    find[RadioGroup](R.id.radio_group_operators).getCheckedRadioButtonId()
  )

  private def enabled() = find[Switch](R.id.switch_account_enable).isChecked()

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
