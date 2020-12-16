package com.example.desafiodigitalhouse.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Date(
    val date: String,
    val type: String
): Parcelable