package im.gian.tr.home.model

import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.GeoPoint

data class Restaurant(
    val name: String = "",
    val city: String = "",
    val position: GeoPoint = GeoPoint(0.0,0.0)
)
