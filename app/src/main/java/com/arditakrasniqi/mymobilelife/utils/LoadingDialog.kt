package com.arditakrasniqi.mymobilelife.utils

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.arditakrasniqi.mymobilelife.databinding.DialogLoadingBinding

class LoadingDialog(context: Context) : Dialog(context) {

    var binder: DialogLoadingBinding = DialogLoadingBinding.inflate(layoutInflater)

    init {
        setCancelable(false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        setContentView(binder.root)
    }
}