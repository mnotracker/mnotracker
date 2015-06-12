package com.github.mnotracker.ui

import android.app.{Activity, Dialog, DialogFragment}

import com.github.mnotracker.R

class AlertDialogFragment(message: String, buttons: (String, () => Unit)*)
                         (implicit activity: Activity)
extends DialogFragment {

  import android.content.DialogInterface
  import android.os.Bundle

  require(buttons.size <= 3)

  override def onCreateDialog(bundle: Bundle): Dialog = {
    import android.app.AlertDialog.Builder

    val builder = new Builder(activity)
      .setMessage(message)
      .setCancelable(true)

    if (buttons.size == 0) builder.setPositiveButton(R.string.ok, clickListener(() => ()))
    if (buttons.size >= 1) builder.setPositiveButton(buttons(0)._1, clickListener(buttons(0)._2))
    if (buttons.size >= 2) builder.setNegativeButton(buttons(1)._1, clickListener(buttons(1)._2))
    if (buttons.size >= 3) builder.setNeutralButton(buttons(2)._1, clickListener(buttons(2)._2))

    builder.create()
  }

  private def clickListener(handler: () => Unit) =
    new DialogInterface.OnClickListener() {
      override def onClick(dialog: DialogInterface, id: Int) = handler()
    }

}

object AlertDialogFragment {

  def apply(message: String, buttons: (String, () => Unit)*)(implicit activity: Activity): Unit =
    new AlertDialogFragment(message, buttons: _*) show (activity.getFragmentManager(), "")

}
