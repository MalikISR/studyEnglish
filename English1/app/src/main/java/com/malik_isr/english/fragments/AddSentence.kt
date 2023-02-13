package com.malik_isr.english.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.malik_isr.english.activities.MainApp
import com.malik_isr.english.databinding.FragmentAddSentanceBinding
import com.malik_isr.english.db.MainViewModel
import com.malik_isr.english.entities.Sentences


class AddSentence : Fragment() {
    private lateinit var binding: FragmentAddSentanceBinding
    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAddSentanceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        addSent()
        super.onViewCreated(view, savedInstanceState)
    }

    // добавляет прелодения для перевода в нашу бд в формате "Sentence"
    private fun addSent() = with(binding){
        addSent.setOnClickListener {
            val sent =  Sentences(null, sentEng.text.toString(), sentRus.text.toString())
            mainViewModel.insertSentence(sent)
            sentEng.setText("")
            sentRus.setText("")
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = AddSentence()
    }
}