package com.simon.orisapp.model

import com.google.gson.annotations.SerializedName

data class EventData(@SerializedName("Name") val name:String,
                     @SerializedName("Date") val date:String,
                     @SerializedName("Place") val place:String,
                     @SerializedName("Links") val links:Map<String, Link>
                     )
