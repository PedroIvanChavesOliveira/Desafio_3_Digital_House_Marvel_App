package com.example.desafiodigitalhouse.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Thumbnail(
        val extension: String?,
        var path: String?
): Parcelable