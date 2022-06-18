package com.example.brandat.ui.fragments.myOrder

import android.os.Parcel
import android.os.Parcelable

data class OrderModel(
    var customerName: String?,
    var orderAddress: String?,
    var orderStatus: String?,
    val orderTotalPrice: String?,
    val orderDate: String?,
    var orderImage:Int?,
    val itemsNumber: String?,

    ):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readInt(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(customerName)
        parcel.writeString(orderAddress)
        parcel.writeString(orderStatus)
        parcel.writeString(orderTotalPrice)
        parcel.writeString(orderDate)
        orderImage?.let { parcel.writeInt(it) }
        parcel.writeString(itemsNumber)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OrderModel> {
        override fun createFromParcel(parcel: Parcel): OrderModel {
            return OrderModel(parcel)
        }

        override fun newArray(size: Int): Array<OrderModel?> {
            return arrayOfNulls(size)
        }
    }


}