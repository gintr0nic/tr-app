package im.gian.tr.model

import android.location.Location
import com.google.firebase.firestore.GeoPoint
import java.io.Serializable
import java.math.RoundingMode

data class Producer (
    val name: String = "",
    val city: String = "",
    val address: String = "",
    val cellphone: String = "",
    val description: String = "",
    val certifications: List<String> = listOf(),
    val location: GeoPoint = GeoPoint(0.0,0.0),
    var id: String = ""
){

    //Get distance in km from given user location
    fun getDistance(userLocation: Location?): Float?{
        return if(userLocation == null) null
        else {
            val restaurantLocation = Location("")
            restaurantLocation.let {
                it.latitude = location.latitude
                it.longitude = location.longitude
            }

            (userLocation.distanceTo(restaurantLocation) / 1000).toBigDecimal()
                .setScale(1, RoundingMode.UP).toFloat()
        }
    }
}