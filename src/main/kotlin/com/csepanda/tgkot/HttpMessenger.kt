/* 
 * This Source Code Form is subject to the terms of the Mozilla
 * Public License, v. 2.0. If a copy of the MPL was not distributed
 * with this file, You can obtain one at http://mozilla.org/MPL/2.0/.
 *
 */

package com.csepanda.tgkot

import com.google.gson.Gson

typealias HttpHeaders = Map<String, List<String>>

class InvalidHttpRequestFormat() : RuntimeException()
class HttpResponseMessage(
        val status : Int,
        val headers: HttpHeaders,
        val content: ByteArray?
)
class HttpRequestMessage(
        val uri    : String,
        val method : String,
        val body   : ByteArray?,
        val headers: HttpHeaders?
)

fun httpRequest(requestMessage: HttpRequestMessage): HttpResponseMessage {
    val url = URL(requestMessage.uri)
    val httpCon = url.openConnection() as HttpURLConnection
    httpCon.doOutput      = true
    httpCon.requestMethod = requestMessage.method

    requestMessage.headers!!.forEach { header ->
        for (value in header.value) {
            httpCon.addRequestProperty(header.key, value)
        }
    }

    httpCon.outputStream.use { it.write(requestMessage.body!!) }

    val buffer = ByteArray(httpCon.contentLength)
    val headers = httpCon.headerFields
    val responseCode = httpCon.responseCode

    if (responseCode >= 400) {
        httpCon.errorStream.read(buffer)
    } else {
        httpCon.inputStream.read(buffer)
    }

    httpCon.disconnect()
    return HttpResponseMessage(responseCode, headers, buffer)
}
