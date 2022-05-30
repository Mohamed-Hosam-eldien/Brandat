package com.example.brandat.ui.fragments.address

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AddressModel(
    @PrimaryKey
    var countryName:String
    ,var Governorate:String
    , var street:String )
