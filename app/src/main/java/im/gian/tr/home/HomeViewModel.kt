package im.gian.tr.home

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import im.gian.tr.R
import im.gian.tr.model.Restaurant
import im.gian.tr.model.UserType


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

    //User type
    private val _userType = MutableLiveData<UserType>(UserType.USER)
    val userType: LiveData<UserType>
        get() = _userType

    fun fetchUserType(){
        db.collection("users").document(user.uid.toString()).get().addOnSuccessListener {
            when(it.get("type")){
                "user" -> _userType.value = UserType.USER
                "restaurant" -> _userType.value = UserType.RESTAURANT
                "producer" -> _userType.value = UserType.PRODUCER
            }
        }
    }

    //User location
    private val _userLocation = MutableLiveData<Location>()
    val userLocation: LiveData<Location>
        get() = _userLocation

    fun fetchLocation(context: Context){
        val locationClient = LocationServices.getFusedLocationProviderClient(context)
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    if (location == null) {
                        val locationCallback = object : LocationCallback() {
                            override fun onLocationResult(locationResult: LocationResult?) {
                                if (locationResult != null && locationResult.locations.isNotEmpty()) {
                                    val newLocation = locationResult.locations[0]
                                    _userLocation.value = newLocation
                                }
                            }
                        }

                        locationClient.requestLocationUpdates(LocationRequest.create(), locationCallback, null)
                    } else {
                        _userLocation.value = location
                    }
                }
        }
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
                if(index == it.documents.size - 1) //If last update list with local one
                    _restaurants.value = restaurantList
            }
        }
    }

    //Saved
    private val _saved = MutableLiveData<List<Restaurant>>(listOf(Restaurant("Caricamento...","Caricamento...")))
    val saved: LiveData<List<Restaurant>>
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
                    if(index == savedPath.size - 1) //If last update list with local one
                        _saved.value = savedList
                }
            }
        }
    }

    //Profile
    fun getUserRestaurant(onSuccessListener: OnSuccessListener<DocumentSnapshot>) {
        db.collection("restaurants").document(user.uid!!).get().addOnSuccessListener(onSuccessListener)
    }
}