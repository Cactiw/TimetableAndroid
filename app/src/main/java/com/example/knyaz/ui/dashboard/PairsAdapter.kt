package com.example.knyaz.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.knyaz.R
import com.example.knyaz.models.Pair

class PairsHolder(
    inflater: LayoutInflater,
    container: ViewGroup
): RecyclerView.ViewHolder(inflater.inflate(R.layout.pair_view_item, container, false)) {

    private val beginTextView = itemView.findViewById<TextView>(R.id.begin_tv)
    private val subjectTextView = itemView.findViewById<TextView>(R.id.subject_tv)

    fun bind(pair: Pair) {
        beginTextView.text = pair.beginTime
        subjectTextView.text = pair.subject
    }
}

class PairsAdapter(private val inflater: LayoutInflater): ListAdapter<Pair, PairsHolder>(Pair.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PairsHolder =
        PairsHolder(inflater, parent)

    override fun onBindViewHolder(holder: PairsHolder, position: Int) =
        holder.bind(getItem(position))

}