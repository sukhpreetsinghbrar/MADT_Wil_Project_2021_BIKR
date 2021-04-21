package com.lambton.mohitmvvmfirebase.utils

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by nir21 on 23-01-2018.
 */
fun AppCompatActivity.replaceFragmenty(args: Bundle?,fragment: androidx.fragment.app.Fragment,
                                       allowStateLoss: Boolean = false,
                                       @IdRes containerViewId: Int) {
//    val args = Bundle()
//    args.putInt("position", 1)
    fragment.setArguments(args)
    val ft = supportFragmentManager
            .beginTransaction()
            .replace(containerViewId, fragment)
    if (!supportFragmentManager.isStateSaved) {
        ft.commit()
    } else if (allowStateLoss) {
        ft.commitAllowingStateLoss()
    }
}