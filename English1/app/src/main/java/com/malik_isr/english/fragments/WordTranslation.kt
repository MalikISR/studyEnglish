package com.malik_isr.english.fragments

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.malik_isr.english.R
import com.malik_isr.english.databinding.FragmentWordTranslationBinding

class WordTranslation : Fragment() {
    private lateinit var binding: FragmentWordTranslationBinding
    private lateinit var arrayEnglish:Array<String>
    private lateinit var arrayRussian:Array<String>
    private var start = 0
    private var end = 10
    private var startWord = 0
    private var endWord = 10
    private var nList: List<Int> = listOf(-1,-1,-1,-1)
    private lateinit var nList2: ArrayList<Int>
    private var rand = -1
    private var bool = false
    private var checkR = false
    private var firstClick = false
    private var correctly = 0
    private var errors = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentWordTranslationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arrayEnglish = resources.getStringArray(R.array.english)
        arrayRussian = resources.getStringArray(R.array.russian)
        init()
        setCurEr()
    }

    private fun init()= with(binding){
        binding.quantityWord.text = arrayEnglish.size.toString()
        bStart.setOnClickListener {
            onClickStart()
        }
        bNext.setOnClickListener {
            onClickNext()
        }
        bReset.setOnClickListener {
            onClickReset()
        }
        word1.setOnClickListener {
            onClickWord1(word1)
        }
        word2.setOnClickListener {
            onClickWord2(word2)
        }
        word3.setOnClickListener {
            onClickWord3(word3)
        }
        word4.setOnClickListener {
            onClickWord4(word4)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = WordTranslation()

    }

    private fun onClickStart() = with(binding){
        val value1: Int
        val value2: Int
        if(TextUtils.isEmpty(edOneWord.text) || TextUtils.isEmpty(edEndWord.text)){  // проверям пустые ли поля вода
            value1 = 1
            value2 = 10
            edOneWord.setText("1")
            edEndWord.setText("10")
        } else{
            value1 = edOneWord.text.toString().toInt()
            value2 = edEndWord.text.toString().toInt()
        }

        checkR = chRand.isChecked
        if (value1<value2) {                                // проверяем какое из введённых чисел больше
            start = value1-1
            end = value2-1
            tenWord() // и устанавливаем числа в соответсвующюю прееменную
        }else{
            if (value1>value2) {
                start = value2
                end = value1
                tenWord()
            }
        }
        if (start<0) start = 0                                                  // устанваливае защиту от значений которые не входят в индксы нашего массива
        if (start>arrayEnglish.size -1) start = arrayEnglish.size - 6
        if (end<0) end = 5
        if (end>arrayEnglish.size-1) end = arrayEnglish.size - 1
        rand = start
        random()
    }

    private fun tenWord(){  // и устанавливаем числа в соответсвующюю прееменную
        startWord = if(start<10) 0
        else start - 10
        endWord = if (end>arrayEnglish.size-9) arrayEnglish.size-1
        else end+10
    }

    private fun onClickNext() = with(binding){
        if (firstClick){
            random()
        } else {
            val toast = Toast.makeText(requireContext(), "Вы не выбрали правильный ответ!", Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.BOTTOM or Gravity.CENTER, 0,180)
            toast.show()
        }
    }

    private fun random(){  // берёт рандомное слово из массива, потом берёт ещё 3 рандомных слова, объединяет их в один массив, далее премешивает и выдаёт нам готовый массив

        if (checkR) {
            rand = (start..end).random()
        } else if (rand >= end){
            rand = 0
        }
        else{
            rand++
        }
        Log.d("My","$rand")
        nList2 = (startWord..endWord).shuffled().take(3) as ArrayList<Int>
        when(rand){
            nList2[0]-> nList2[0] = (arrayEnglish.indices).random()
            nList2[1]-> nList2[1] = (arrayEnglish.indices).random()
            nList2[2]-> nList2[2] = (arrayEnglish.indices).random()
        }
        nList2 = nList2.shuffled() as ArrayList<Int>
        nList = listOf(nList2[0],nList2[1],nList2[2],rand)
        nList = nList.shuffled()
        firstClick = false
        checked()
    }

    private fun checked()= with(binding){
        bool = chEng.isChecked                                                  // проверяем включена ли галочка на чекбоксе "English", если включена, то туда наво вставить английское слово, а если нет то русское
        if(!bool) {
            tvWord.text = arrayRussian[rand]
            wordRus()
        }else{
            tvWord.text = arrayEnglish[rand]
            wordEng()
        }
    }

    private fun onClickWord1(view: View){
        greenRed(view, 0)
    }
    private fun onClickWord2(view: View){
        greenRed(view, 1)
    }
    private fun onClickWord3(view: View){
        greenRed(view, 2)
    }
    private fun onClickWord4(view: View){
        greenRed(view, 3)
    }
    private fun greenRed(view: View, index: Int){ // устанавливаем цвет нашему TextView при нажатии, если правильно то зелённый, если не верно то красный
        if (!firstClick) {
            if (nList[index] == rand) {
                view.setBackgroundResource(R.color.green)
                firstClick = true
                correctly++
            } else {
                view.setBackgroundResource(R.color.red)
                errors++
            }
            setCurEr()
        }
    }
    private fun wordRus()= with(binding){
        word1.text = arrayEnglish[nList[0]]
        word2.text = arrayEnglish[nList[1]]
        word3.text = arrayEnglish[nList[2]]
        word4.text = arrayEnglish[nList[3]]
        white()
    }
    private fun wordEng()= with(binding) {
        word1.text = arrayRussian[nList[0]]
        word2.text = arrayRussian[nList[1]]
        word3.text = arrayRussian[nList[2]]
        word4.text = arrayRussian[nList[3]]
        white()
    }
    private fun white()= with(binding){
        word1.setBackgroundResource(R.color.back_ground)
        word2.setBackgroundResource(R.color.back_ground)
        word3.setBackgroundResource(R.color.back_ground)
        word4.setBackgroundResource(R.color.back_ground)
    }
    private fun setCurEr() = with(binding){
        tvCorrectly.text = "Correctly: $correctly"                  // устанваливаем счёт
        tvErrors.text = "Errors: $errors"
    }
    private fun onClickReset(){ //обнуляем счёт
        correctly = 0
        errors = 0
        setCurEr()
    }
}