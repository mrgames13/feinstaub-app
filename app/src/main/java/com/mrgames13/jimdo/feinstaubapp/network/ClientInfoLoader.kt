/*
 * Copyright © Marc Auberer 2017 - 2020. All rights reserved
 */

package com.mrgames13.jimdo.feinstaubapp.network

import android.content.Context
import android.util.Log
import com.mrgames13.jimdo.feinstaubapp.R
import com.mrgames13.jimdo.feinstaubapp.model.other.ClientInfo
import com.mrgames13.jimdo.feinstaubapp.shared.Constants
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

suspend fun loadClientInfo(context: Context): ClientInfo? {
    try {
        val response = networkClient.get<HttpStatement>(context.getString(R.string.api_root) + "client/" + context.getString(R.string.client_name_short)).execute()
        if(response.status == HttpStatusCode.OK) {
            val responseContent = URLDecoder.decode(response.readText(), StandardCharsets.UTF_8.name())
            return Json.decodeFromString(responseContent)
        } else {
            Log.e(Constants.TAG, response.status.toString())
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}