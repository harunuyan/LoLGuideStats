package com.volie.lolguidestats.data.remote.service

import com.volie.lolguidestats.data.model.mode.ModeData
import retrofit2.Response
import retrofit2.http.GET

interface LOLApiGithub {

    @GET("gameModes.json")
    suspend fun getGameModes(): Response<ModeData>
}