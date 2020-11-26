package com.example.knyaz.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.Volley
import com.example.knyaz.MainViewModel
import com.example.knyaz.R
import com.example.knyaz.models.Pair

class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_dashboard, container, false)

    override fun onStart() {
        super.onStart()

        val view = requireView()
        val context = requireContext()

        val mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        val user = mainViewModel.user

        val dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        dashboardViewModel.reset(Volley.newRequestQueue(context))

        dashboardViewModel.loadPairs(user.group)

        val textView: TextView = view.findViewById(R.id.text_dashboard)

        val recycler = view.findViewById<RecyclerView>(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(context)

        dashboardViewModel.errorLiveData.observe(viewLifecycleOwner) { error: String ->
            textView.text = error
            textView.visibility = View.VISIBLE
            recycler.visibility = View.GONE
        }

        dashboardViewModel.resultLiveData.observe(viewLifecycleOwner) { response: List<Pair> ->
            textView.text = response.toString()
            textView.visibility = View.GONE
            recycler.visibility = View.VISIBLE

            val adapter = recycler.adapter
            if (adapter == null) {
                val newAdapter = PairsAdapter(LayoutInflater.from(context))
                newAdapter.submitList(response)
                recycler.adapter = newAdapter
            } else {
                (adapter as PairsAdapter).submitList(response)
            }
        }
    }
}