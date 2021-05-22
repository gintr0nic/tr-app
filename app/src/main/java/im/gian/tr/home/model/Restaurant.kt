package im.gian.tr.home.model

import android.location.Location
import com.google.firebase.firestore.GeoPoint
import java.math.RoundingMode

data class Restaurant(
    val name: String = "",
    val city: String = "",
    val location: GeoPoint = GeoPoint(0.0,0.0)
){
    fun getDistance(userLocation: Location?): Float?{
        if(userLocation == null) return null
        else {
            val restaurantLocation = Location("")
            restaurantLocation.let {
                it.latitude = location.latitude
                it.longitude = location.longitude
            }

            return (userLocation.distanceTo(restaurantLocation) / 1000).toBigDecimal()
                .setScale(1, RoundingMode.UP).toFloat()
        }
    }
}
