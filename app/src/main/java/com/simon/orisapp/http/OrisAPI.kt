package com.simon.orisapp.http


import com.simon.orisapp.model.AdditionalUserInfo
import com.simon.orisapp.model.Event
import com.simon.orisapp.model.EventData
import com.simon.orisapp.model.EventList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface OrisAPI {

    @GET("API?format=json&method=getUser")
    fun getUser(@Query("rgnum") registration : String) : Call<AdditionalUserInfo>

    @GET("API?format=json&method=getUserEventEntries")
    fun getUserEvents(@Query("userid") id : String) : Call<String>

    @GET("API?format=json&method=getEvent")
    fun getEventData(@Query("id") id : String) : Call<Event>

    @GET("API/?format=json&method=getEventList")
    fun getEventList(@Query("datefrom") dateFrom : String, @Query("dateto") dateTo : String) : Call<EventList>
}
