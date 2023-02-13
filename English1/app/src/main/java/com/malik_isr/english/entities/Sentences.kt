package com.malik_isr.english.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sentences")
data class Sentences(
    @PrimaryKey(autoGenerate = true)
    val id:Int?,

    @ColumnInfo(name = "english")
    val English:String,

    @ColumnInfo(name = "russian")
    val Russian:String,
):java.io.Serializable
