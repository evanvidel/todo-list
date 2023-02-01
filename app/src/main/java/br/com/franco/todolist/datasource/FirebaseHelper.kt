package br.com.franco.todolist.datasource

import android.util.Log
import br.com.franco.todolist.model.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirebaseHelper {

    private val db = FirebaseFirestore.getInstance()

    fun create(item: Task) {
        db.collection(COLLECTION_NAME).add(item)
        Log.i("TAG", "create: $item")
    }

    fun read(onSuccess: (list: List<Task?>) -> Unit) {
        db.collection(COLLECTION_NAME).get().addOnSuccessListener { querySnapshot ->
            val list = querySnapshot.documents.map { it.toObject(Task::class.java) }
            onSuccess(list)
        }.addOnFailureListener {

        }
    }
    // documentID = getDocumentByTitle(task.title)
    // update(documentId, task)
    // firebaseHelper.getDocumentByTitle(task.title) {
    //      firebaseHelper.update(it, task)
    //  }

    fun update(id: String, task: Task) {
        db.collection(COLLECTION_NAME).document(id).set(task, SetOptions.merge())
            .addOnSuccessListener {

            }.addOnFailureListener {

            }
    }

    // não mexer
    fun getDocumentByTitle(title: String, onSuccess: (String) -> Unit) {
        db.collection(COLLECTION_NAME)
            .whereEqualTo("title", title)
            .get()
            .addOnSuccessListener {
                onSuccess.invoke(it.documents[0].id)
            }
    }

    fun delete(document: String) {
        db.collection(COLLECTION_NAME).document(document).delete()
            .addOnSuccessListener {

            }.addOnFailureListener {

            }
    }

    companion object {
        const val COLLECTION_NAME = "Tarefas"
    }
}






