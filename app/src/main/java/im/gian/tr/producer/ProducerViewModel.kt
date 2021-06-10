package im.gian.tr.producer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import im.gian.tr.model.Certification
import im.gian.tr.model.Producer

class ProducerViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val user = Firebase.auth

    //Producer
    private val _producer = MutableLiveData<Producer>(Producer("Caricamento..."))
    val producer: LiveData<Producer>
        get() = _producer

    fun fetchProducer(id: String?) {
        db.collection("producers").document(id!!).get().addOnSuccessListener {
            val producer = it.toObject(Producer::class.java)
            producer?.id = it.id
            _producer.value = producer!!
        }
    }

    fun editProducerName(newName: String) {
        val prod = _producer.value
        prod?.name = newName
        _producer.value = prod!!

        db.collection("producers").document(_producer.value?.id!!).update("name", newName)
    }

    fun editProducerCity(newCity: String) {
        val prod = _producer.value
        prod?.city = newCity
        _producer.value = prod!!

        db.collection("producers").document(_producer.value?.id!!).update("city", newCity)
    }

    fun editProducerDescription(newDes: String) {
        val prod = _producer.value
        prod?.description = newDes
        _producer.value = prod!!

        db.collection("producers").document(_producer.value?.id!!).update("description", newDes)
    }

    //Saved
    /*private val _saved = MutableLiveData<Boolean>(false)
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
    }*/

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
                certification.id = document.id
                certificationList.add(certification)
                if(index == ids.size - 1)  //If last update list with local one
                    _certifications.value = certificationList
            }
        }
    }

}