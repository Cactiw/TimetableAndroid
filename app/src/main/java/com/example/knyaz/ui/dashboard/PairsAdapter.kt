package com.example.knyaz.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.knyaz.R
import com.example.knyaz.models.PairsCell

abstract class PairsHolder(view: View): RecyclerView.ViewHolder(view) {

    class PairHolder(
        inflater: LayoutInflater,
        container: ViewGroup,
        onClickListener: (cell: PairsCell.PairCell) -> Unit
    ): PairsHolder(inflater.inflate(R.layout.pair_view_item, container, false)) {
        private val beginTextView = itemView.findViewById<TextView>(R.id.begin_tv)
        private val subjectTextView = itemView.findViewById<TextView>(R.id.subject_tv)

        private var cell: PairsCell.PairCell? = null

        init {
            itemView.setOnClickListener { _ ->
                cell?.let { onClickListener(it) }
            }
        }

        override fun bind(pairsCell: PairsCell) {
            cell = pairsCell as PairsCell.PairCell
            beginTextView.text = cell?.beginTime
            subjectTextView.text = cell?.subject
        }
    }

    class LineHolder(
        inflater: LayoutInflater,
        container: ViewGroup
    ): PairsHolder(inflater.inflate(R.layout.line_view_item, container, false)) {
        override fun bind(pairsCell: PairsCell) = Unit
    }

    abstract fun bind(pairsCell: PairsCell)
}

class PairsAdapter(
    private val inflater: LayoutInflater,
    private val onClickListener: (cell: PairsCell.PairCell) -> Unit
): ListAdapter<PairsCell, PairsHolder>(PairsCell.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PairsHolder = when(viewType) {
        0 -> PairsHolder.PairHolder(inflater, parent, onClickListener)
        1 -> PairsHolder.LineHolder(inflater, parent)
        else -> PairsHolder.LineHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: PairsHolder, position: Int) =
        holder.bind(getItem(position))

    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is PairsCell.PairCell -> 0
        is PairsCell.LinePairsCell -> 1
        else -> -1
    }
}