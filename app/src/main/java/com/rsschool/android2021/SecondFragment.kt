package com.rsschool.android2021

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.rsschool.android2021.listeners.BackClickListener
import com.rsschool.android2021.listeners.OnSecondFragmentClickListener
import kotlin.random.Random

class SecondFragment : Fragment() {

    private var backButton: Button? = null
    private var result: TextView? = null

    private var backClickListener: BackClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        result = view.findViewById(R.id.result)
        backButton = view.findViewById(R.id.back)

        val min = arguments?.getInt(MIN_VALUE_KEY) ?: 0
        val max = arguments?.getInt(MAX_VALUE_KEY) ?: 0

        val res = generate(min, max)
        result?.text = res.toString()

        backButton?.setOnClickListener {
           backClickListener?.onBackClick(res)
        }
    }

    private fun generate(min: Int, max: Int): Int {
        if (max <= min) {
            return min
        }

        return Random.nextInt(min, max)
    }

    companion object {

        @JvmStatic
        fun newInstance(min: Int, max: Int): SecondFragment {
            val fragment = SecondFragment()
            val args = Bundle().apply {
                putInt(MIN_VALUE_KEY, min)
                putInt(MAX_VALUE_KEY, max)
            }
            fragment.arguments = args

            return fragment
        }

        private const val MIN_VALUE_KEY = "MIN_VALUE"
        private const val MAX_VALUE_KEY = "MAX_VALUE"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        backClickListener = context as MainActivity
    }

    override fun onDetach() {
        super.onDetach()
        backClickListener = null
    }


}