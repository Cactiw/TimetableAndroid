package com.example.knyaz.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.volley.*
import com.example.knyaz.Globals
import com.example.knyaz.models.PairsCell
import com.example.knyaz.tools.EncodedRequest
import org.json.JSONArray
import org.json.JSONObject


class DashboardViewModel : ViewModel() {

    private val _resultLiveData = MutableLiveData<List<PairsCell>>()
    val resultLiveData: LiveData<List<PairsCell>> = _resultLiveData

    private val _errorLiveData = MutableLiveData<String>()
    val errorLiveData: LiveData<String> = _errorLiveData

    private val _toastLiveData = MutableLiveData<String>()
    val toastLiveData: LiveData<String> = _toastLiveData

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

                    val pairs = mutableListOf<PairsCell>()
                    for (i in 0 until jsonArray.length()) {
                        pairs.add(PairsCell.PairCell(jsonArray.get(i) as JSONObject))
                        pairs.add(PairsCell.LinePairsCell)
                    }

                    pairs.removeAt(pairs.size - 1)
                    _resultLiveData.value = pairs
                },
                { error: VolleyError ->
                    _errorLiveData.value = error.message
                }
            )
        )
    }

    fun onCellClicked(cell: PairsCell.PairCell) {
        _toastLiveData.value = "${cell.subject} ${cell.beginTime}"
    }
}