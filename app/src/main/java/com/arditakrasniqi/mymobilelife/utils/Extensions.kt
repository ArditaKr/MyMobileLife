package com.arditakrasniqi.mymobilelife.utils

import android.view.View
import com.arditakrasniqi.mymobilelife.interfaces.OnSingleClickListener

fun View.setOnSingleClickListener(l: (View) -> Unit) {
    setOnClickListener(OnSingleClickListener(l))
}