package br.com.franco.todolist.datasource

import android.util.Log
import br.com.franco.todolist.model.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.SetOptions
import io.grpc.InternalChannelz.id

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
    // documentID = getDocumentByTitle(task.title)
    // update(documentId, task)
    // firebaseHelper.getDocumentByTitle(task.title) {
    //      firebaseHelper.update(it, task)
    //  }

    fun update(reference: String, task: Task, onSuccess: () -> Unit) {
        db.collection(COLLECTION_NAME).document(reference).set(task, SetOptions.merge())
            .addOnSuccessListener {
                onSuccess()
            }.addOnFailureListener {

            }
    }

    // não mexer
    fun getDocumentById(id: Int, onSuccess: (String) -> Unit) {
        db.collection(COLLECTION_NAME)
            .whereEqualTo("id", id)
            .get()
            .addOnSuccessListener {
                onSuccess.invoke(it.documents[0].id)
            }
    }



    fun filter(onSuccess: (list: List<Task?>) -> Unit) {
        db.collection(COLLECTION_NAME)
            .whereEqualTo("checked", false)
            .whereEqualTo("hour", "")
            .get()
            .addOnSuccessListener { querySnapshot ->
                val list = querySnapshot.documents.map { it.toObject(Task::class.java) }
                onSuccess(list)
            }
    }

    fun delete(task: Task) {
        db.collection(COLLECTION_NAME).document()
            .delete()

        Log.i("TAG", "delete: itens $task")
        /*db.collection(COLLECTION_NAME).document("tarefa13")
            .delete()
        Log.i("TAG", "firebese fun delete: $task")
           // .delete()
          //  .addOnSuccessListener {

         //   }.addOnFailureListener {

         //   }*/
    }

    private const val COLLECTION_NAME = "Tarefas"

}






