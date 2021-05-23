package im.gian.tr.home

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import im.gian.tr.R
import im.gian.tr.home.model.Restaurant

class HomeViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val user = Firebase.auth

    //Title
    private val _titleTextRes = MutableLiveData<Int>(R.string.home)
    val titleTextRes: LiveData<Int>
        get() = _titleTextRes

    fun setTitleTextRes(textRes: Int){
        _titleTextRes.value = textRes
    }

    //User location
    private val _userLocation = MutableLiveData<Location>()
    val userLocation: LiveData<Location>
        get() = _userLocation

    fun setUserLocation(location: Location){
        _userLocation.value = location
    }

    //Restaurants
    private val _restaurants = MutableLiveData<List<Restaurant>>(listOf(Restaurant("Caricamento...","Caricamento...")))
    val restaurants: LiveData<List<Restaurant>>
        get() = _restaurants

    fun fetchRestaurants() {
        val restaurantList = mutableListOf<Restaurant>()

        db.collection("restaurants").get().addOnSuccessListener {
            it.documents.forEachIndexed { index, document ->
                val restaurant = document.toObject(Restaurant::class.java)!!
                restaurant.id = document.id
                restaurantList.add(restaurant)
                if(index == it.documents.size - 1)
                    _restaurants.value = restaurantList
            }
        }

        /*db.collection("restaurants").get().addOnSuccessListener {
            _restaurants.value = it.toObjects(Restaurant::class.java)
        }*/
    }

    //Saved
    private val _saved = MutableLiveData<MutableList<Restaurant>>(mutableListOf(Restaurant("Caricamento...","Caricamento...")))
    val saved: LiveData<MutableList<Restaurant>>
        get() = _saved

    fun fetchSaved() {
        val savedList = mutableListOf<Restaurant>()

        db.collection("users").document(user.uid!!).get().addOnSuccessListener {
            val savedPath: List<String> = it.get("saved") as List<String>
            savedPath.forEachIndexed { index, path ->
                db.document(path).get().addOnSuccessListener { document ->
                    val restaurant = document.toObject(Restaurant::class.java)!!
                    restaurant.id = document.id
                    savedList.add(restaurant)
                    if(index == savedPath.size - 1)
                        _saved.value = savedList
                }
            }
        }
    }

}