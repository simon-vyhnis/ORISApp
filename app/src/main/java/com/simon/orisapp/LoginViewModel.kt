package com.simon.orisapp

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.simon.orisapp.http.OrisAPI
import com.simon.orisapp.http.ServiceBuilder
import com.simon.orisapp.model.AdditionalUserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    fun getAdditionalUserInfo(registration: String):LiveData<AdditionalUserInfo>{
        val request = ServiceBuilder.buildService(OrisAPI::class.java)
        val call = request.getUser(registration)

        call.enqueue(object : Callback<AdditionalUserInfo>{
            override fun onResponse(call: Call<AdditionalUserInfo>, response: Response<AdditionalUserInfo>) {
                TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<AdditionalUserInfo>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
        return additionalUserInfo
    }
    private val additionalUserInfo = MutableLiveData<AdditionalUserInfo>()

    fun savePersonalInfo(registration: String, username:String, password:String, userId: String){
        val preferences = getApplication<Application>()
            .getSharedPreferences(getApplication<Application>().resources.getString(R.string.preferences_file), Context.MODE_PRIVATE)
        val editor = preferences.edit()
        editor.putString("userid", userId)
        editor.putString("registration", registration)
        editor.putString("username", username)
        editor.putString("password", password)
        editor.commit()
    }
}