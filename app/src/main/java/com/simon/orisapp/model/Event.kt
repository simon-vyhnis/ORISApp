package com.simon.orisapp.model

import com.google.gson.annotations.SerializedName

data class Event(@SerializedName("Data") val data : EventData?){
    var successful = false
}

data class EventData(@SerializedName("Name") val name : String,
                     @SerializedName("Date") val date : String,
                     @SerializedName("Place") val place : String,
                     @SerializedName("Links") val links : Map<String, Link>,
                     @SerializedName("Documents") val docs : Map<String, Link>
)
