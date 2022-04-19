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
import java.time.LocalDate
import java.util.*

class EventListViewModel(application: Application) : AndroidViewModel(application) {
    var selectedEventId : String? = null

    fun getEventList(): LiveData<EventList> {
        val request = ServiceBuilder.buildService(OrisAPI::class.java)
        val today = LocalDate.now()
        val dateFrom = today.minusWeeks(2)
        val dateTo = today.plusWeeks(5)
        val call = request.getEventList(dateFrom.toString(), dateTo.toString())

        call.enqueue(object : Callback<EventList> {
            override fun onResponse(call: Call<EventList>, response: Response<EventList>) {
                if(response.isSuccessful) {
                    response.body()?.successful = true
                    eventListData.postValue(response.body())
                }else{
                    eventListData.postValue(EventList(null))
                }
            }

            override fun onFailure(call: Call<EventList>, t: Throwable) {
                t.printStackTrace()
                eventListData.postValue(EventList(null))
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
                        response.body()?.successful =  true
                        eventData.postValue(response.body())
                    } else {
                        eventData.postValue(Event(null))
                    }
                }

                override fun onFailure(call: Call<Event>, t: Throwable) {
                    t.printStackTrace()
                    eventData.postValue(Event(null))
                }
            })
        }
        return eventData
    }
    private val eventData = MutableLiveData<Event>()
}