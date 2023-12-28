package com.sohezsoft.student.helper

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class HelperClass {

    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    fun isValidPassword(email:String):String?{

        if(!email.contains("[a-z]".toRegex())){
            return  "Enter at least one lowercase letter\n"
        }

        if(!email.contains("[A-Z]".toRegex())){
            return "Enter at least one uppercase letter\n"
        }

        if(!email.contains("[0-9]".toRegex())){
            return "Enter at least one digit\n"
        }

        if (!email.contains("[-+_!@#$%^&*()]".toRegex())){
            return "Enter at least one special character"

        }
        if (email.contains("[ ]".toRegex())){
            return "Password do not contain space"
        }
        if(email.length<8){
            return "Enter minimum length of 8 characters\n"
        }
        return null
    }

    fun isValidEmail(email: String): Boolean {
        return Regex("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$").matches(email)
    }

    fun stringToDoubleArray(instString: String): MutableList<Double>? {
        //ths method is convert the string into list of Double
        //val inputString = "12/33.44/90.99"
        val parts = instString.split('/') //create the list of String
        val result = mutableListOf<Double>()//empty double list
        for (part in parts) {
            try {
                result.add(part.toDouble())
            } catch (e: Exception) {
                //if any exception
                return null
            }
        }
        return result
    }

    fun calculateGrade(score: Double): String {
        return when {
            score >= 90 -> "A"
            score in 80.0..89.9 -> "B"
            score in 70.0..79.9 -> "C"
            score in 60.0..69.9 -> "D"
            else -> "F"
        }
    }

}