package im.gian.tr.restaurant

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import im.gian.tr.model.Certification
import im.gian.tr.model.Restaurant

class RestaurantViewModel : ViewModel() {
    private val db = Firebase.firestore

    //Name
    private val _restaurant = MutableLiveData<Restaurant>(Restaurant("Caricamento..."))
    val restaurant: LiveData<Restaurant>
        get() = _restaurant

    fun setRestaurant(restaurant: Restaurant){
        _restaurant.value = restaurant
    }

    //Certifications
    private val _certifications = MutableLiveData<List<Certification>>(listOf(Certification("Caricamento...")))
    val certifications: LiveData<List<Certification>>
        get() = _certifications

    fun fetchCertifications(){
        val certificationList = mutableListOf<Certification>()
        val ids = _restaurant.value?.certifications

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