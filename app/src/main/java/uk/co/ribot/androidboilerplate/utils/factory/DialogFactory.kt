package uk.co.ribot.androidboilerplate.utils.factory

import android.app.Dialog
import android.content.Context
import android.support.annotation.StringRes
import android.support.v7.app.AlertDialog
import uk.co.ribot.androidboilerplate.R

fun createSimpleOkErrorDialog(context: Context, title: String, message: String): Dialog {
    val alertDialog = AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(message)
            .setNeutralButton(R.string.dialog_action_ok, null)
    return alertDialog.create()
}

fun createSimpleOkErrorDialog(context: Context,
                              @StringRes titleResource: Int,
                              @StringRes messageResource: Int): Dialog {

    return createSimpleOkErrorDialog(context,
            context.getString(titleResource),
            context.getString(messageResource))
}

fun createGenericErrorDialog(context: Context, message: String): Dialog {
    val alertDialog = AlertDialog.Builder(context)
            .setTitle(context.getString(R.string.dialog_error_title))
            .setMessage(message)
            .setNeutralButton(R.string.dialog_action_ok, null)
    return alertDialog.create()
}

fun createGenericErrorDialog(context: Context, @StringRes messageResource: Int): Dialog {
    return createGenericErrorDialog(context, context.getString(messageResource))
}
