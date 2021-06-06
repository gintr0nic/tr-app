package im.gian.tr.model

import android.location.Location
import com.google.firebase.firestore.GeoPoint
import java.io.Serializable
import java.math.RoundingMode

data class Producer (
    val name: String = "",
    val city: String = "",
    val description: String = "",
    val certifications: List<String> = listOf(),
    val products: List<String> = listOf(),
    var id: String = ""
)