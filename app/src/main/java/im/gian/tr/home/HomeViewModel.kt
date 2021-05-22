package im.gian.tr.home

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import im.gian.tr.R
import im.gian.tr.home.model.Restaurant

class HomeViewModel : ViewModel() {
    private val db = Firebase.firestore

    private val _titleTextRes = MutableLiveData<Int>(R.string.home)
    val titleTextRes: LiveData<Int>
        get() = _titleTextRes

    fun setTitleTextRes(textRes: Int){
        _titleTextRes.value = textRes
    }

    private val _userLocation = MutableLiveData<Location>()
    val userLocation: LiveData<Location>
        get() = _userLocation

    fun setUserLocation(location: Location){
        _userLocation.value = location
    }

    private val _restaurants = MutableLiveData<List<Restaurant>>(listOf(Restaurant("Caricamento...","Caricamento...")))
    val restaurants: LiveData<List<Restaurant>>
        get() = _restaurants

    fun fetchRestaurants() {
        db.collection("restaurants").get().addOnSuccessListener {
            _restaurants.value = it.toObjects(Restaurant::class.java)
        }
    }

}