package im.gian.tr.pages.restaurant

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

class RestaurantViewModel : ViewModel() {
    //Title
    private val _test = MutableLiveData<String>()
    val test: LiveData<String>
        get() = _test

    fun setTest(new: String){
        _test.value = new
    }


}