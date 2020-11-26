package com.example.knyaz.tools

import com.android.volley.NetworkResponse
import com.android.volley.Response
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.StringRequest
import java.io.UnsupportedEncodingException

class EncodedRequest(
    method: Int,
    url: String,
    listener: Response.Listener<String>,
    errorListener: Response.ErrorListener?
): StringRequest(method, url, listener, errorListener) {

    override fun parseNetworkResponse(response: NetworkResponse?): Response<String> {
        var strUTF8: String? = null
        try {
            strUTF8 = String(response!!.data, charset("UTF-8"))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return Response.success(
            strUTF8,
            HttpHeaderParser.parseCacheHeaders(response)
        )
    }
}