package com.malik_isr.english.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.malik_isr.english.R
import com.malik_isr.english.databinding.ActivityMainBinding
import com.malik_isr.english.fragments.AddSentence
import com.malik_isr.english.fragments.CollectSentences
import com.malik_isr.english.fragments.FragmentManager
import com.malik_isr.english.fragments.WordTranslation

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setButtomNavListener()
        FragmentManager.setFragment(WordTranslation.newInstance(), this@MainActivity)
    }

    private fun setButtomNavListener(){
        binding.bNav.setOnItemSelectedListener{
            when(it.itemId){
                R.id.word ->{
                    FragmentManager.setFragment(WordTranslation.newInstance(), this@MainActivity)
                }
                R.id.sentences ->{
                    FragmentManager.setFragment(CollectSentences.newInstance(), this@MainActivity)
                }
                R.id.setting ->{
                    FragmentManager.setFragment(AddSentence.newInstance(), this@MainActivity)
                }
            }
            true
        }
    }
}