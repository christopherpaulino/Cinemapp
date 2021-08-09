package com.frontic.cinemapp.ui.base

import android.net.Network
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.frontic.cinemapp.R
import com.frontic.cinemapp.utils.NetworkUtils

abstract class BaseFragment : Fragment() {

    open fun initializeVariables(view: View){
    }

}