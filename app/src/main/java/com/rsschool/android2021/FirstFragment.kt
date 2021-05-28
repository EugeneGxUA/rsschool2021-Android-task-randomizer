package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.rsschool.android2021.listeners.OnSecondFragmentClickListener

class FirstFragment : Fragment() {

    private var generateButton: Button? = null
    private var previousResult: TextView? = null

    private var onSecondFragmentClickListener: OnSecondFragmentClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previousResult = view.findViewById(R.id.previous_result)
        generateButton = view.findViewById(R.id.generate)

        val result = arguments?.getInt(PREVIOUS_RESULT_KEY)
        previousResult?.text = "Previous result: ${result.toString()}"

        val minEt = view.findViewById<EditText>(R.id.min_value)
        val maxEt = view.findViewById<EditText>(R.id.max_value)

        generateButton?.setOnClickListener {
            if (minEt.text.isNullOrEmpty() || maxEt.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "You need fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val min = minEt.text.toString().toInt()
            val max = maxEt.text.toString().toInt()

            if (min < 0 || max < min || max < 0 || min > Int.MAX_VALUE || max > Int.MAX_VALUE || min == max) {
                Toast.makeText(requireContext(), "You fill invalid data, check it", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            
            onSecondFragmentClickListener?.onSecondFragmentClick(min, max)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(previousResult: Int): FirstFragment {
            val fragment = FirstFragment()
            val args = Bundle()
            args.putInt(PREVIOUS_RESULT_KEY, previousResult)
            fragment.arguments = args
            return fragment
        }

        private const val PREVIOUS_RESULT_KEY = "PREVIOUS_RESULT"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onSecondFragmentClickListener = context as MainActivity
    }

    override fun onDetach() {
        super.onDetach()
        onSecondFragmentClickListener = null
    }
}