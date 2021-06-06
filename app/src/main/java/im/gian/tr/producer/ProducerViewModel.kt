package im.gian.tr.producer

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import im.gian.tr.model.Certification
import im.gian.tr.model.Item
import im.gian.tr.model.Producer
import im.gian.tr.model.Restaurant

class ProducerViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val user = Firebase.auth

    //Restaurant
    private val _producer = MutableLiveData<Producer>(Producer("Caricamento..."))
    val producer: LiveData<Producer>
        get() = _producer

    fun fetchProducer(id: String?) {
        db.collection("producers").document(id!!).get().addOnSuccessListener {
            _producer.value = it.toObject(Producer::class.java)
            _producer.value?.id = it.id
        }
    }

    //Menu
    private val _products = MutableLiveData<List<Item>>(mutableListOf(Item("Caricamento...", listOf())))
    val products: LiveData<List<Item>>
        get() = _products

    fun fetchProducts(){
        val menuList = mutableListOf<Item>()

        db.collection("producers/${_producer.value?.id}/products").get().addOnSuccessListener {
            it.documents.forEachIndexed { index, document ->
                val item = document.toObject(Item::class.java)!!
                menuList.add(item)
                if(index == it.documents.size - 1)  //If last update list with local one
                    _products.value = menuList
            }
        }
    }

    //Saved
    private val _saved = MutableLiveData<Boolean>(false)
    val saved: LiveData<Boolean>
        get() = _saved

    fun setSaved(new: Boolean){
        _saved.value = new
    }

    fun addSaved(){
        db.collection("users").document(user.uid!!).update("saved", FieldValue.arrayUnion("producers/${producer.value?.id}"))
    }

    fun removeSaved(){
        db.collection("users").document(user.uid!!).update("saved", FieldValue.arrayRemove("producers/${producer.value?.id}"))
    }

    //Certifications
    private val _certifications = MutableLiveData<List<Certification>>(listOf(Certification("Caricamento...")))
    val certifications: LiveData<List<Certification>>
        get() = _certifications

    fun fetchCertifications(){
        val certificationList = mutableListOf<Certification>()
        val ids = _producer.value?.certifications

        ids?.forEachIndexed { index, s ->
            db.collection("certifications").document(s).get().addOnSuccessListener { document ->
                val certification = document.toObject(Certification::class.java)!!
                certificationList.add(certification)
                if(index == ids.size - 1)  //If last update list with local one
                    _certifications.value = certificationList
            }
        }
    }

}