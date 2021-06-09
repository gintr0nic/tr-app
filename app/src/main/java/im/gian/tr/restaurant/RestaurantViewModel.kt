package im.gian.tr.restaurant

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
import im.gian.tr.model.Restaurant

class RestaurantViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val user = Firebase.auth

    //Restaurant
    private val _restaurant = MutableLiveData<Restaurant>(Restaurant("Caricamento..."))
    val restaurant: LiveData<Restaurant>
        get() = _restaurant

    fun setRestaurant(restaurant: Restaurant){
        _restaurant.value = restaurant
    }

    fun editRestaurantName(newName: String) {
        val res = _restaurant.value
        res?.name = newName
        _restaurant.value = res!!

        db.collection("restaurants").document(_restaurant.value?.id!!).update("name", newName)
    }

    fun editRestaurantCity(newCity: String) {
        val res = _restaurant.value
        res?.city = newCity
        _restaurant.value = res!!

        db.collection("restaurants").document(_restaurant.value?.id!!).update("city", newCity)
    }

    fun editRestaurantDescription(newDes: String) {
        val res = _restaurant.value
        res?.description = newDes
        _restaurant.value = res!!

        db.collection("restaurants").document(_restaurant.value?.id!!).update("description", newDes)
    }

    //Menu
    private val _menu = MutableLiveData<List<Item>>(mutableListOf(Item("Caricamento...", listOf())))
    val menu: LiveData<List<Item>>
        get() = _menu

    fun fetchMenu(){
        val menuList = mutableListOf<Item>()

        db.collection("restaurants/${_restaurant.value?.id}/menu").get().addOnSuccessListener {
            it.documents.forEachIndexed { index, document ->
                val item = document.toObject(Item::class.java)!!
                menuList.add(item)
                if(index == it.documents.size - 1)  //If last update list with local one
                    _menu.value = menuList
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
        db.collection("users").document(user.uid!!).update("saved", FieldValue.arrayUnion("restaurants/${restaurant.value?.id}"))
    }

    fun removeSaved(){
        db.collection("users").document(user.uid!!).update("saved", FieldValue.arrayRemove("restaurants/${restaurant.value?.id}"))
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
                certification.id = document.id
                certificationList.add(certification)
                if(index == ids.size - 1)  //If last update list with local one
                    _certifications.value = certificationList
            }
        }
    }

}