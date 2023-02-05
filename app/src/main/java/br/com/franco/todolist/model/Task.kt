package br.com.franco.todolist.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Task(
    var title: String = "",
    var hour: String = "",
    var date: String = "",
    var id: Int = 0,
    var isChecked: Boolean = false
) : Parcelable
