package com.lambton.bikr.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.lambton.bikr.R


class MyCustomDialog: DialogFragment() {

     interface dialogViewCallBack {
        fun onViewLoaded(textView: TextView?)
    }

    companion object {

        lateinit var dialogViewCallBack: dialogViewCallBack
    }

    fun myCustomDialog(callBack: dialogViewCallBack): MyCustomDialog? {
        dialogViewCallBack = callBack
        return MyCustomDialog()
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.round_corner);
       var view = inflater.inflate(R.layout.custom_dialog_fragment, container, false)
        setupClickListeners(view)
        return view
    }
    private fun setupClickListeners(view1: View) {
        var camera = view1.findViewById(R.id.tv_cam) as TextView
        camera.setOnClickListener(View.OnClickListener {
            dialogViewCallBack.onViewLoaded(camera);
            dismiss()
        })
       var gallery = view1.findViewById(R.id.tv_gallery) as TextView
        gallery.setOnClickListener(View.OnClickListener {
            dialogViewCallBack.onViewLoaded(gallery);
            dismiss()
        })
     var cancel =   view1.findViewById(R.id.tv_cancel) as TextView
        cancel.setOnClickListener(View.OnClickListener {
            dismiss()
        })

    }
    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

}