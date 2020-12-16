package com.example.desafiodigitalhouse.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Item(
    val name: String?,
    val resourceURI: String?,
    val type: String?,
    val role: String?
): Parcelable