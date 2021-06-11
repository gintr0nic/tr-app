package im.gian.tr

import android.location.Location
import com.google.firebase.firestore.GeoPoint
import im.gian.tr.model.Restaurant
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [29])
class DistanceUnitTest {
    private lateinit var restaurant: Restaurant
    private lateinit var userLocation: Location

    @Before
    fun setup() {
        restaurant = Restaurant(location = GeoPoint(41.4571191,15.5334409))
        userLocation = Location("")
        userLocation.let {
            it.latitude = 41.8373284
            it.longitude = 15.5573004
        }
    }

    @Test
    fun distance() {
        val distance = restaurant.getDistance(userLocation)
        assertEquals(42.2f, distance!!, 0.1f)
    }
}