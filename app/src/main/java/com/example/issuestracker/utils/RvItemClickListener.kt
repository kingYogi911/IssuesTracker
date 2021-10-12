package com.example.issuestracker.utils

import android.view.View

interface RvItemClickListener {
    fun onItemClicked(position:Int,view: View)
}