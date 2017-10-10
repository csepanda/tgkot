package com.csepanda.tgbot

import com.github.salomonbrys.kotson.jsonObject
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import java.lang.reflect.Type

private val gson = GsonBuilder()
        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .create()

internal class UndefinedContentTypeException : RuntimeException()
internal class UnsupportedContentTypeException(msg: String) : RuntimeException(msg)

internal fun constructRequest(uri: String, vararg values: Pair<String, *>): HttpRequestMessage {
    val body = jsonObject(values.asIterable())
    return constructRequest(uri, body.toString())
}

internal fun constructRequest(uri: String, obj: Any): HttpRequestMessage {
    val body = gson.toJson(obj)
    return constructRequest(uri, body.toString())
}

internal fun constructRequest(uri: String, json: String): HttpRequestMessage {
    val headers = hashMapOf("Content-Type" to listOf("application/json"))
    return HttpRequestMessage(uri, "POST", json.toByteArray(), headers)
}

internal fun parseResponse(response: HttpResponseMessage, type: Type): Any {
    if (response.headers["Content-Type"]!!.size != 1) {
        throw UndefinedContentTypeException()
    }

    when (response.headers["Content-Type"]!![0]) {
        "application/json" -> return gson.fromJson(String(response.content!!), type)
        else -> throw UnsupportedContentTypeException(response.headers["Content-Type"]!![0])
    }
}

