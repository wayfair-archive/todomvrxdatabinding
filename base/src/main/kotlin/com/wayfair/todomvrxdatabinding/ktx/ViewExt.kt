package com.wayfair.todomvrxdatabinding.ktx

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.StringRes
import com.google.android.material.snackbar.Snackbar

typealias SnackbarAction = Pair<CharSequence, () -> Unit>

fun View.showSnackbar(
    @StringRes textRes: Int,
    @Snackbar.Duration duration: Int = Snackbar.LENGTH_SHORT,
    action: SnackbarAction? = null
) = showSnackbar(resources.getString(textRes), duration, action)

fun View.showSnackbar(
    text: CharSequence,
    @Snackbar.Duration duration: Int = Snackbar.LENGTH_SHORT,
    action: SnackbarAction? = null
) = Snackbar.make(this, text, duration)
    .apply {
        action?.let { (actionText, action) ->
            setAction(actionText) { action() }
        }
    }.show()

/**
 * Close keyboard and clear focus on the receiver view.
 */
fun View.hideKeyboard() {
    val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    if (this is ViewGroup) {
        imm.hideSoftInputFromWindow(focusedChild?.windowToken, 0)
    } else {
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
    clearFocus()
}