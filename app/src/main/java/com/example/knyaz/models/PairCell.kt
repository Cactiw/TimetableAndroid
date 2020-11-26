package com.example.knyaz.models

import androidx.recyclerview.widget.DiffUtil
import org.json.JSONObject

sealed class PairsCell {

    data class PairCell(val id: Int, val subject: String, val beginTime: String): PairsCell() {
        constructor(json: JSONObject) : this(
            json.getInt("id"),
            json.getString("subject"),
            json.getString("begin_time")
        )
    }

    object LinePairsCell: PairsCell()

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<PairsCell> = object : DiffUtil.ItemCallback<PairsCell>() {
            override fun areItemsTheSame(oldItem: PairsCell, newItem: PairsCell): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: PairsCell, newItem: PairsCell): Boolean {
                return oldItem == newItem
            }
        }
    }
}
