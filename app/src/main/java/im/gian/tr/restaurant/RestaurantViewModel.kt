package im.gian.tr.restaurant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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

}