package com.sohezsoft.student.data.repository.db.preference

import android.content.Context

class TokenPreference(private val context: Context){
    private val sharedPreferences = context.getSharedPreferences("Token", Context.MODE_PRIVATE)

    fun setToken(token:String){
        sharedPreferences.edit().putString("token", token).apply()
    }

    fun getToken(): String?{
       return sharedPreferences.getString("token", null)
    }

    fun isToken():Boolean{
        return sharedPreferences.contains("token")
    }

    fun removeToken(){
         sharedPreferences.edit().remove("token").clear().apply()
    }
}