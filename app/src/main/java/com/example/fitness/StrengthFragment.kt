package com.example.fitness

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.Fragment

class StrengthFragment : Fragment() {
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>
    private val workoutLogs = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getStringArrayList("logs")?.let {
            workoutLogs.addAll(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_strength, container, false)
        listView = view.findViewById(R.id.listView_strength)
        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, workoutLogs)
        listView.adapter = adapter
        return view
    }

    companion object {
        fun newInstance(logs: ArrayList<String>): StrengthFragment {
            val fragment = StrengthFragment()
            val args = Bundle()
            args.putStringArrayList("logs", logs)
            fragment.arguments = args
            return fragment
        }
    }
}
