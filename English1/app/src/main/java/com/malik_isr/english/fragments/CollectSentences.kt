package com.malik_isr.english.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.malik_isr.english.R
import com.malik_isr.english.activities.MainApp
import com.malik_isr.english.adapter.WordAdapter
import com.malik_isr.english.databinding.FragmentCollectSentencesBinding
import com.malik_isr.english.db.MainViewModel
import com.malik_isr.english.entities.Sentences

class CollectSentences : Fragment(), WordAdapter.Listener {
    private lateinit var binding: FragmentCollectSentencesBinding
    private var adapter = WordAdapter(this@CollectSentences)
    private var sentenceCheck = ""
    private lateinit var listObserve: List<Sentences>
    lateinit var allSent: List<Sentences>
    private var firstSent = false
    private var correctly = 0
    private var errors = 0

    private val listEngRusSentences = mutableListOf<Sentences>()

    private val mainViewModel: MainViewModel by activityViewModels {
        MainViewModel.MainViewModelFactory((context?.applicationContext as MainApp).database)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentCollectSentencesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init()
        observer()
        setCurEr()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun init() = with(binding){
        rcWords.layoutManager = GridLayoutManager(activity, 2)              // инициализируем наш адаптер и присваиваем клики
        rcWords.adapter = adapter
        backSpace.setOnClickListener {
            backSpace()
        }
        checkSentence.setOnClickListener {
            firstSent = if (collectSent.text.trim() == sentenceCheck.trim()){
                checkSent("Верно!")
                correctly++
                setCurEr()
                true
            }else{
                checkSent("Не верно!")
                errors++
                setCurEr()
                false
            }
        }
        bNext.setOnClickListener {
            if (firstSent) {
                updateAllElement()
                firstSent = false
            } else {
                checkSent("Вы не дали правильный ответ!")
                errors++
                setCurEr()
                updateAllElement()
            }
        }
        bReset.setOnClickListener {
            onClickReset()
        }
    }

    private fun checkSent(trueFalse: String){  // выводит Toast сообщение правильно собрано предложение или нет
        val toast = Toast.makeText(requireContext(), trueFalse, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.BOTTOM or Gravity.CENTER, 0,180)
        toast.show()
    }

    companion object {
        @JvmStatic
        fun newInstance() = CollectSentences()
    }

    @SuppressLint("SetTextI18n")
    override fun clickWord(word: String)= with(binding) {
        collectSent.text = "${collectSent.text} $word"          // добавляем слово в TextView при нажатии на слово из RecyclerView
    }

    private fun backSpace()= with(binding){
        val text = collectSent.text.toString()                          // удаляем последнее слово из TextView
        val parts = text.split(" ")
        val newText = parts.dropLast(1).joinToString("   ")
        collectSent.text = newText
    }

    private fun updateAllElement()= with(binding){
        val randSent = allSent.random()                 // обнавляем все элементы, втавляя туда другое предложение
        rusSentence.text = randSent.Russian
        sentenceCheck = randSent.English

        val words = randSent.English.split(" ")
        val randListWords = words.shuffled()
        adapter.addWordArray(randListWords)
        collectSent.text = ""
    }

    private fun allSent(): List<Sentences> {   // соберает все предложения в один массив, то есть предложения из бд и из ресурсов
        val arrayEnglishSentencesResource = resources.getStringArray(R.array.englishSentence)
        val listRussianSentencesResource = resources.getStringArray(R.array.russianSentence)

        for (i in arrayEnglishSentencesResource.indices) {
            val sent =
                Sentences(null, arrayEnglishSentencesResource[i], listRussianSentencesResource[i])
            listEngRusSentences.add(sent)
        }
        return listEngRusSentences + listObserve
    }

    private fun observer(){ //берёт данные из бд, в нашем случае это предложения для перевода
        mainViewModel.allSentences.observe(viewLifecycleOwner) {
            listObserve = it
            allSent = allSent()
            updateAllElement()
        }
    }

    private fun setCurEr() = with(binding) {
        tvCorrectly.text = "Correctly: $correctly"                  // устанавливает счёт
        tvErrors.text = "Errors: $errors"
    }

    private fun onClickReset(){ // обнуляет счёт
        correctly = 0
        errors = 0
        setCurEr()
    }
}