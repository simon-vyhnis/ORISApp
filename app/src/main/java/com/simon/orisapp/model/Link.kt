package com.simon.orisapp.model

import com.google.gson.annotations.SerializedName

data class Link(@SerializedName("Url") val url: String, @SerializedName("SourceType") val sourceType: List<SourceType>)

data class SourceType(@SerializedName("NameCZ") val name:String)
