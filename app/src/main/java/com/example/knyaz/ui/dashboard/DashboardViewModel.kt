package com.example.knyaz.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.*
import com.android.volley.toolbox.HttpHeaderParser
import com.android.volley.toolbox.StringRequest
import com.example.knyaz.Globals
import com.example.knyaz.models.Pair
import com.example.knyaz.tools.EncodedRequest
import org.json.JSONArray
import org.json.JSONObject
import java.io.UnsupportedEncodingException


class DashboardViewModel : ViewModel() {

    private val _resultLiveData = MutableLiveData<List<Pair>>()
    val resultLiveData: LiveData<List<Pair>> = _resultLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    lateinit var queue: RequestQueue

    fun reset(queue: RequestQueue) {
        this.queue = queue
    }

    fun loadPairs(groupNumber: Int) {
        queue.add(
            EncodedRequest(
                Request.Method.GET,
                Globals.HOST + "/pairs/by_group/$groupNumber",
                { response: String ->
                    val jsonArray = JSONArray(String(response.toByteArray(), charset("UTF-8")))

                    val pairs = mutableListOf<Pair>()
                    for (i in 0 until jsonArray.length()) {
                        pairs.add(Pair(jsonArray.get(i) as JSONObject))
                    }

                    _resultLiveData.value = pairs
                },
                { error: VolleyError ->
                    _errorLiveData.value = error.message
                }
            )
        )
    }
}