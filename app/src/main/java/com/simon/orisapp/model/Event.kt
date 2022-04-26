package com.simon.orisapp.model

import com.google.gson.annotations.SerializedName

data class Event(@SerializedName("Data") val data : EventData?){
    var successful = false
}

data class EventData(@SerializedName("Name") val name : String,
                     @SerializedName("Date") val date : String,
                     @SerializedName("Place") val place : String,
                     @SerializedName("Map") val map : String,
                     @SerializedName("StartTime") val startTime : String,
                     @SerializedName("Sport") val sport : Sport,
                     @SerializedName("Discipline") val discipline : Sport,
                     @SerializedName("Org1") val org : Org,
                     @SerializedName("Links") val links : Map<String, Link>,
                     @SerializedName("Documents") val docs : Map<String, Link>,
                     @SerializedName("GPSLat") val latitude : Float,
                     @SerializedName("GPSLon") val longitude : Float,
)

data class Sport(@SerializedName("NameCZ") val name: String)
data class Org(@SerializedName("Name") val name: String)
