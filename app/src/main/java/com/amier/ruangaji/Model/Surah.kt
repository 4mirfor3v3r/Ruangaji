package com.amier.ruangaji.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Surah(val index:String,val title:String,val arti:String, val type:String,val ayat:String): Parcelable {
    constructor():this("","","","","")
}