package com.amier.ruangaji.Model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Quran(val nomor:String,val nama:String,val arti:String,val type:String,val ayat:String):Parcelable {
    constructor():this("","","","","")
}