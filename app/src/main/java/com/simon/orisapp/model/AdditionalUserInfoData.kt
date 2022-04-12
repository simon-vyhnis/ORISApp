package com.simon.orisapp.model

import com.google.gson.annotations.SerializedName

data class AdditionalUserInfoData(@SerializedName("FirstName") val firstName:String,
                                  @SerializedName("LastName") val lastName:String,
                                  @SerializedName("ID") val userId:Int)
