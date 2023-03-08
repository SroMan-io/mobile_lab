package com.tsu.mobile_lab.ui.dictionary

import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract.Colors
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.savedstate.R
import com.tsu.mobile_lab.databinding.FragmentDictionaryBinding

class DictionaryFragment : Fragment() {

    private var _binding: FragmentDictionaryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(DictionaryViewModel::class.java)

        _binding = FragmentDictionaryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val newLine: String = "\n"
        var wordInfo: String = "The practice or skill of preparing food by combining, mixing, and heating ingredients."
        val lenWordInfo: Int = wordInfo.count() + 2
        //val info = wordInfo.substring("skill")
        var exampleInfo: String = "Example: he developed an interest in cooking."
        val totalWordInfo = wordInfo + newLine + newLine + exampleInfo
        var coloredExample = SpannableString(totalWordInfo)
        var fcsBlue = ForegroundColorSpan(
            Color.parseColor("#65AAEA")
        )

        coloredExample.setSpan(fcsBlue, lenWordInfo, lenWordInfo + 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)


        binding.textView10.text = coloredExample
        binding.textView11.text = coloredExample
        /*val textView: TextView = binding.textView10
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}