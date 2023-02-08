package com.uz.mycards.utils

import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import com.uz.mycards.R

fun Fragment.snackMessage(message: String) {
    Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).apply {
        setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.color_snack_default))
            .setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
    }.show()
}