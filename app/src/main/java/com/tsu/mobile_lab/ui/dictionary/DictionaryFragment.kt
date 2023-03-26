package com.tsu.mobile_lab.ui.dictionary

import android.content.DialogInterface
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.tsu.mobile_lab.*
import com.tsu.mobile_lab.databinding.FragmentDictionaryBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DictionaryFragment : Fragment() {

    private var _binding: FragmentDictionaryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private fun makeDialog(message: String) {
        val dialogBuilder =
            AlertDialog.Builder(this@DictionaryFragment.requireActivity())
        dialogBuilder.setTitle("Alert")
        dialogBuilder.setMessage(message)
        dialogBuilder.setNeutralButton("Ok")
        { _: DialogInterface, _: Int -> }
        dialogBuilder.show()
    }

    private fun makeRecyclerViewElement(meaning: String, example: String) : SpannableString {
        val exampleText = "Example: "

        if (example != "") {
            val lenWordInfo: Int = meaning.count() + 2
            val totalWordInfo = meaning + newLine + newLine + exampleText + example
            val coloredExample = SpannableString(totalWordInfo)
            val fcsBlue = ForegroundColorSpan(Color.parseColor("#65AAEA"))

            coloredExample.setSpan(
                fcsBlue,
                lenWordInfo,
                lenWordInfo + 9,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )

            return coloredExample
        }

        else return SpannableString(meaning)
    }

    private val newLine: String = "\n"
    private val errorMessage = "Error during getting data from a server was occurred! " + newLine + newLine +
            "Please, check your word is correct or your internet connection is able. " + newLine + newLine +
            "If you are sure it's not your fault, then, please, try input your request a bit later."

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDictionaryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        var userWord = ""
        var transcription = ""
        var audio = ""
        var partOfSpeech = ""
        val list = mutableListOf<SpannableString>()
        val listOfMeanings = mutableListOf<String>()
        val listOfExamples = mutableListOf<String>()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.dictionaryapi.dev/api/v2/entries/en/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(DictionaryAPI::class.java)

        val database = Room.databaseBuilder(activity!!.applicationContext, WordDB::class.java, "words_db")
            .fallbackToDestructiveMigration().build()

        binding.meanRecyclerView.layoutManager =
            LinearLayoutManager(this@DictionaryFragment.requireActivity())
        binding.meanRecyclerView.adapter = MeaningsListAdapter(list)

        binding.soundButton.setOnClickListener {
            val mediaPlayer = MediaPlayer()
            mediaPlayer.setDataSource(audio)

            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    mediaPlayer.prepare()
                    mediaPlayer.start()
                }
                catch (_: Throwable) {
                    withContext(Dispatchers.Main) {
                        binding.soundButton.visibility = View.INVISIBLE
                        makeDialog(errorMessage)
                    }
                }
            }
        }

        binding.addButton.setOnClickListener{

            lifecycleScope.launch(Dispatchers.IO) {
                //database.wordDao().delete(WordEntityDB("","","cro"))
                val w: WordEntityDB? = database.wordDao().findWord(userWord)
                //Log.d("Fragment", w.toString())

                if (w!=null) {
                    withContext(Dispatchers.Main) {
                        makeDialog("Word exists in your dictionary already!")
                    }
                }

                else if (transcription == "" && partOfSpeech == "") {
                    withContext(Dispatchers.Main) {
                        makeDialog("Can't add new word to your dictionary!" + newLine + newLine +
                                "Please, check your typed word is correct or " +
                                "your internet connection is able")
                    }
                }
                else {
                    database.wordDao()
                        .insert(WordEntityDB(transcription, partOfSpeech, userWord))

                    var i = 0
                    listOfMeanings.forEach{
                        database.meaningDao().insert(MeaningEntityDB(userWord, it, listOfExamples[i]))
                        i++
                    }
                }
            }
        }

        binding.searchButton.setOnClickListener {

            userWord = binding.wordEditText.text.toString()
            transcription = ""
            partOfSpeech = ""
            listOfExamples.clear()
            listOfMeanings.clear()

            lifecycleScope.launch(Dispatchers.IO) {
                try {
                    val result = service.getWordInfo(userWord)

                    list.clear()
                    audio = ""

                    //transcription = result[0].phonetic ?: ""

                    result[0].phonetics.forEach {
                        if (audio != "" && transcription != "") return@forEach
                        if (it.audio != "") audio = it.audio

                        if (it.text != "") {
                            transcription = it.text ?: transcription

                            if (transcription != "")
                                transcription = transcription.substring(1, transcription.length - 1)
                        }
                    }

                    result[0].meanings[0].definitions.forEach {
                        val meaning = it.definition
                        listOfMeanings.add(meaning)

                        val example = it.example ?: ""
                        listOfExamples.add(example)

                        list.add(makeRecyclerViewElement(meaning, example))
                    }

                    userWord = userWord.replaceFirstChar {
                        it.uppercase()
                    }

                    partOfSpeech = result[0].meanings[0].partOfSpeech.replaceFirstChar {
                        it.uppercase()
                    }

                    withContext(Dispatchers.Main) {
                        binding.userWordtextView.text = userWord
                        binding.partSpTextView.text = partOfSpeech
                        if (transcription != "")
                            binding.transcriptionTextView.text = "[" + transcription + "]"
                        else binding.transcriptionTextView.text =  transcription
                        binding.meanRecyclerView.adapter = MeaningsListAdapter(list)

                        if (audio == "") binding.soundButton.visibility = View.INVISIBLE
                        else binding.soundButton.visibility = View.VISIBLE
                    }
                }

                catch (_ : Throwable) {

                    val w: WordEntityDB? = database.wordDao().findWord(userWord)
                    if (w != null) {
                        list.clear()
                        val m = database.meaningDao().getWordInfo(userWord)
                        m.forEach {
                            list.add(makeRecyclerViewElement(it.Meaning, it.Example))
                        }

                        withContext(Dispatchers.Main) {
                            binding.userWordtextView.text = w.Word
                            binding.partSpTextView.text = w.PartOfSpeech
                            if (w.Transcription != "")
                                binding.transcriptionTextView.text = "[" + w.Transcription + "]"
                            else binding.transcriptionTextView.text =  w.Transcription
                            binding.soundButton.visibility = View.INVISIBLE
                            binding.meanRecyclerView.adapter = MeaningsListAdapter(list)
                        }
                    }

                    else {
                        withContext(Dispatchers.Main) {
                            makeDialog(errorMessage)
                        }
                    }
                }
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}