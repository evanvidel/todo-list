package br.com.franco.todolist.datasource

import android.util.Log
import br.com.franco.todolist.model.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions

object FirebaseHelper {

    private val db by lazy { FirebaseFirestore.getInstance() }

    fun create(item: Task) {
        db.collection(COLLECTION_NAME).add(item)

    }

    fun read(onSuccess: (list: List<Task?>) -> Unit) {
        db.collection(COLLECTION_NAME).get().addOnSuccessListener { querySnapshot: QuerySnapshot ->
            val list = querySnapshot.documents.map { it.toObject(Task::class.java) }
            onSuccess(list)
        }.addOnFailureListener {

        }
    }

    fun update(reference: String, task: Task, onSuccess: () -> Unit) {
        db.collection(COLLECTION_NAME).document(reference).set(task, SetOptions.merge())
            .addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener {

            }
    }

    // não mexer
    fun getDocumentById(id: Int, onSuccess: (String) -> Unit) {
        db.collection(COLLECTION_NAME).whereEqualTo("id", id).get().addOnSuccessListener {
            onSuccess.invoke(it.documents[0].id)
            Log.i("TAG", "getDocumentById: ${it.documents[0].id}")
        }
    }


    fun filter(onSuccess: (list: List<Task?>) -> Unit) {
        db.collection(COLLECTION_NAME).whereEqualTo("checked", false).whereEqualTo("hour", "").get()
            .addOnSuccessListener { querySnapshot ->
                val list = querySnapshot.documents.map { it.toObject(Task::class.java) }
                onSuccess(list)
            }
    }

    fun delete(item: String) {
        db.collection(COLLECTION_NAME).document(item).delete()
        Log.i("TAG", "função delete: $item")

    }

    private const val COLLECTION_NAME = "Tarefas"
}






