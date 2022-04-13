package com.simon.orisapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.simon.orisapp.http.OrisAPI
import com.simon.orisapp.http.ServiceBuilder
import com.simon.orisapp.model.Event
import com.simon.orisapp.model.EventData
import com.simon.orisapp.model.EventList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EventListViewModel(application: Application) : AndroidViewModel(application) {
    var selectedEventId : String? = null

    fun getEventList(): LiveData<EventList> {
        val request = ServiceBuilder.buildService(OrisAPI::class.java)
        val call = request.getEventList()

        call.enqueue(object : Callback<EventList> {
            override fun onResponse(call: Call<EventList>, response: Response<EventList>) {
                if(response.isSuccessful) {
                    eventListData.postValue(response.body())
                }else{
                    TODO("Not yet implemented")
                }
            }

            override fun onFailure(call: Call<EventList>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
        return eventListData
    }
    private val eventListData = MutableLiveData<EventList>()

    fun getEventDetails() : LiveData<Event> {
        val request = ServiceBuilder.buildService(OrisAPI::class.java)
         selectedEventId?.let {
             val call = request.getEventData(it)

             call.enqueue(object : Callback<Event> {
                override fun onResponse(call: Call<Event>, response: Response<Event>) {
                    if (response.isSuccessful) {
                        eventData.postValue(response.body())
                    } else {
                        TODO("Not yet implemented")
                    }
                }

                override fun onFailure(call: Call<Event>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }
        return eventData
    }
    private val eventData = MutableLiveData<Event>()
}