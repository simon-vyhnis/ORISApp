package com.simon.orisapp.model

import com.google.gson.annotations.SerializedName

data class EventBare(@SerializedName("Name") val name:String, @SerializedName("Date") val date:String)
