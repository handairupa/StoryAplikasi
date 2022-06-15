package com.about.app_substoryapp.Helper

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

class Constant {

    companion object {

        val PREF_IS_LOGIN = "PREF_IS_LOGIN"
        val PREF_NAME = "PREF_USERNAME"
        val PREF_USERID = "PREF_USERID"
        val PREF_TOKEN = "PREF_TOKEN"
    }

    @Parcelize
    data class Constant(
        val name: String?,
        val deskripsi: String?,
        val imageurl: String?
    ) : Parcelable
}