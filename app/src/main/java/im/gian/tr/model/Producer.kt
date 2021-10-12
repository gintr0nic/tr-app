package im.gian.tr.model

import android.location.Location
import com.google.firebase.firestore.GeoPoint
import java.io.Serializable
import java.math.RoundingMode

data class Producer (
    var name: String = "",
    var city: String = "",
    var description: String = "",
    val facebook: String = "",
    val instagram: String = "",
    val cellphone: String = "",
    val address: String = "",
    val website: String = "",
    val certifications: List<String> = listOf(),
    val products: List<String> = listOf(),
    var id: String = ""
)