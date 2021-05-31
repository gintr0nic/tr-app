package im.gian.tr.pages.restaurant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import im.gian.tr.model.Restaurant

class RestaurantViewModel : ViewModel() {
    private val db = Firebase.firestore

    //Title
    private val _test = MutableLiveData<String>()
    val test: LiveData<String>
        get() = _test

    fun setTest(new: String){
        _test.value = new
    }

    //Name
    private val _restaurant = MutableLiveData<Restaurant>(Restaurant("Caricamento..."))
    val restaurant: LiveData<Restaurant>
        get() = _restaurant

    fun getRestaurant(id: String){
        db.collection("restaurants").document(id).get().addOnSuccessListener {
            _restaurant.value = it.toObject(Restaurant::class.java)
        }
    }




}