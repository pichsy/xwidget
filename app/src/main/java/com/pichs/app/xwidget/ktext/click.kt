package com.pichs.app.xwidget.ktext

import android.view.View

fun View.click(listener: View.OnClickListener) {
    setOnClickListener(listener)
}