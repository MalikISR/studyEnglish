package com.malik_isr.english.db

import android.util.Log
import androidx.lifecycle.*
import com.malik_isr.english.entities.Sentences
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class MainViewModel(dataBase: MainDataBase): ViewModel() {
    private val dao = dataBase.getDao()
    var  allSentences: LiveData<List<Sentences>> = dao.getAllSentences().asLiveData()

    fun insertSentence(sentence: Sentences) = viewModelScope.launch {
        dao.insertSentence(sentence)
        //Log.d("My", "${sentence.id} ${sentence.English} ${sentence.Russian}")
    }

    fun allSentAsSent(){

    }

    class MainViewModelFactory(private val dataBase: MainDataBase) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if(modelClass.isAssignableFrom(MainViewModel::class.java)){
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(dataBase) as T
            }
            throw IllegalArgumentException("Unknown ViewModelClass")
        }
    }
}