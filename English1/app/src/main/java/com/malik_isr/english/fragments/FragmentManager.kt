package com.malik_isr.english.fragments

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.malik_isr.english.R

object FragmentManager { // нужен для того чтобы установить нужный нам фрагмент
    var currentFrag: Fragment? = null

    fun setFragment(newFrag: Fragment, activity: AppCompatActivity){
        val transaction = activity.supportFragmentManager.beginTransaction()
        transaction.replace(R.id.placeHolder, newFrag)
        transaction.commit()
        currentFrag = newFrag
    }
}