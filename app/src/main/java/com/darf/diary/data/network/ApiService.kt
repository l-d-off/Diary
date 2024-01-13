package com.darf.diary.data.network

import android.content.Context
import android.util.Log
import com.darf.diary.data.network.model.EventDto
import com.google.gson.Gson
import java.io.IOException

object ApiService {
    fun getEvent(context: Context): EventDto {
        lateinit var json: String

        try {
            json = context.assets.open("events.json").bufferedReader().use {
                it.readText()
            }
        } catch (e: IOException) {
            Log.d("MyTag", e.message.toString())
        }
        return Gson().fromJson(json, EventDto::class.java)
    }
}