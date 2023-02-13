package com.malik_isr.english.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.malik_isr.english.R
import com.malik_isr.english.databinding.WordItemBinding

class WordAdapter(private val listener: Listener): RecyclerView.Adapter<WordAdapter.WordHolder>() {

    private var wordList = ArrayList<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.word_item, parent, false)
        return WordHolder(view)
    }

    override fun onBindViewHolder(holder: WordHolder, position: Int) {
        holder.bind(wordList[position], listener)
    }

    class WordHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = WordItemBinding.bind(view)
        fun bind(word: String, listener: Listener) = with(binding) {
            wordSent.text = word
            wordSent.setOnClickListener {
                listener.clickWord(word)
            }
        }
    }

    override fun getItemCount(): Int {
        return wordList.size
    }

    fun addWordArray(words: List<String>) {
        val wordList1 = ArrayList<String>()
        for (word in words) {
            wordList1.add(word)
            notifyDataSetChanged()
        }
        wordList = wordList1
    }

    interface Listener {
        fun clickWord(word: String)
    }
}