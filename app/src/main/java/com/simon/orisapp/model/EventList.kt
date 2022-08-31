package com.simon.orisapp.model

import com.google.gson.annotations.SerializedName

data class EventList(@SerializedName("Data") val data : Map<String, EventBare>?){
    var successful = false
}

data class EventBare(@SerializedName("Name") val name:String,
                     @SerializedName("Date") val date:String,
                     @SerializedName("ID") val id:String)
