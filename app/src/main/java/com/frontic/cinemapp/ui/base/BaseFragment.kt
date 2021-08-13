package com.frontic.cinemapp.ui.base

import android.app.AlertDialog
import android.view.View
import androidx.fragment.app.Fragment
import com.frontic.cinemapp.R

/**
 * Base Fragment to be inherited by others fragments.
 *
 * @author Christopher Paulino.
 */
abstract class BaseFragment : Fragment() {


    open fun initializeVariables(view: View) {
    }

    /**
     * Shows Alert dialog with a specific message.
     */
    open fun showAlertMessage(message: String) {
        val alertDialog = AlertDialog.Builder(context).apply {
            setTitle(getString(R.string.atention_message))
            setMessage(message)
        }

        alertDialog.create().show()
    }
}