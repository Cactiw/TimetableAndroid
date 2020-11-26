package com.example.knyaz.models

import androidx.recyclerview.widget.DiffUtil
import org.json.JSONObject


data class Pair(val id: Int, val subject: String, val beginTime: String) {

    constructor(json: JSONObject) : this(
        json.getInt("id"),
        json.getString("subject"),
        json.getString("begin_time")
    )

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Pair> = object : DiffUtil.ItemCallback<Pair>() {
            override fun areItemsTheSame(oldItem: Pair, newItem: Pair): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Pair, newItem: Pair): Boolean {
                return oldItem == newItem
            }
        }
    }

}