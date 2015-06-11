package com.github.mnotracker.ui

import android.app.{Activity, Dialog, DialogFragment}

import com.github.mnotracker.R

class AlertDialogFragment(message: String, buttons: Vector[(String, () => Unit)])
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

    buttons.size match {
      case 0 => builder.setPositiveButton(R.string.ok, clickListener(() => ()))
      case 1 => builder.setPositiveButton(buttons(0)._1, clickListener(buttons(0)._2))
      case 2 =>
        builder.setPositiveButton(buttons(0)._1, clickListener(buttons(0)._2))
        builder.setNegativeButton(buttons(1)._1, clickListener(buttons(1)._2))
      case 3 =>
        builder.setPositiveButton(buttons(0)._1, clickListener(buttons(0)._2))
        builder.setNegativeButton(buttons(1)._1, clickListener(buttons(1)._2))
        builder.setNeutralButton(buttons(2)._1, clickListener(buttons(2)._2))
    }

    builder.create()
  }

  private def clickListener(handler: () => Unit) =
    new DialogInterface.OnClickListener() {
      override def onClick(dialog: DialogInterface, id: Int) = handler()
    }

}

object AlertDialogFragment {

  def apply(message: String, buttons: Vector[(String, () => Unit)])(implicit activity: Activity): Unit =
    new AlertDialogFragment(message, buttons) show (activity.getFragmentManager(), "")

}
