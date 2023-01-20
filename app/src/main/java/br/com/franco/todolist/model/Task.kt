package br.com.franco.todolist.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Task(
    val title: String = "",
    val hour: String = "",
    val date: String = "",
    val id: Int = 0
) : Parcelable
