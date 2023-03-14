package com.tsu.mobile_lab.ui.dictionary

import android.graphics.Color
import android.os.Bundle
import android.provider.CalendarContract.Colors
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.savedstate.R
import com.tsu.mobile_lab.DictionaryAPI
import com.tsu.mobile_lab.databinding.FragmentDictionaryBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

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
        val exampleText: String = "Example: "
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





        binding.searchButton.setOnClickListener {
            var userWord = binding.wordEditText.text.toString()

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.dictionaryapi.dev/api/v2/entries/en/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(DictionaryAPI::class.java)



            lifecycleScope.launch(Dispatchers.IO) {

                var result = service.getWordInfo(userWord)//.toString()
                var word = result[0]
                var phon = word.phonetics

                withContext(Dispatchers.Main){
                    //binding.textView10.text = result[0].meanings[0].definitions[0].definition
                    //binding.textView11.text = result[0].meanings[0].definitions[1].definition
                    binding.userWordtextView.text = userWord.replaceFirstChar {
                        it.uppercase()
                    }
                    binding.partSpTextView.text = result[0].meanings[0].partOfSpeech.replaceFirstChar {
                         it.uppercase()
                    }

                    var transcription = result[0].phonetic
                    transcription = transcription.substring(1, transcription.length - 1)

                    binding.transcriptionTextView.text = "[" + transcription + "]"


                    var example: String = result[0].meanings[0].definitions[0].example
                    if (example != null){
                        var wordInfo: String = result[0].meanings[0].definitions[0].definition
                        val lenWordInfo: Int = wordInfo.count() + 2
                        //val info = wordInfo.substring("skill")
                        var exampleInfo: String = result[0].meanings[0].definitions[0].example
                        val totalWordInfo = wordInfo + newLine + newLine + exampleText + exampleInfo
                        var coloredExample = SpannableString(totalWordInfo)
                        var fcsBlue = ForegroundColorSpan(
                            Color.parseColor("#65AAEA")
                        )

                        coloredExample.setSpan(fcsBlue, lenWordInfo, lenWordInfo + 9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                        binding.textView10.text = coloredExample
                    }

                    else binding.textView10.text = result[0].meanings[0].definitions[0].definition

                }

                Log.d("Fragment", result.toString())
            }
        }




        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}