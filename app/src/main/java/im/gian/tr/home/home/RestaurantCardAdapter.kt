package im.gian.tr.home.home

import android.location.Location
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import im.gian.tr.R
import im.gian.tr.home.model.Restaurant
import java.math.RoundingMode

class RestaurantCardAdapter(private var restaurantList: List<Restaurant>?, private val userLocation: Location?, private val sortByDistance: Boolean) : RecyclerView.Adapter<RestaurantCardAdapter.RestaurantCardViewHolder>() {
    var storage = Firebase.storage
    var list = restaurantList

    init {
        if(sortByDistance){
            val sortedRestaurantList = restaurantList?.toMutableList()
            sortedRestaurantList?.sortBy { it.getDistance(userLocation) }
            list = sortedRestaurantList
        }
    }

    class RestaurantCardViewHolder(private val row: View) : RecyclerView.ViewHolder(row) {
        val textViewRestaurantName: TextView = row.findViewById(R.id.textViewRestaurantName)
        val textViewRestaurantCity: TextView = row.findViewById(R.id.textViewRestaurantCity)
        val textViewRestaurantDistance: TextView = row.findViewById(R.id.textViewRestaurantDistance)
        val imageViewRestaurant: ImageView = row.findViewById(R.id.imageViewRestaurant)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantCardViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.restaurant_card_view,
            parent, false)
        return RestaurantCardViewHolder(layout)
    }

    override fun onBindViewHolder(holder: RestaurantCardViewHolder, position: Int) {
        if(list != null){
            holder.textViewRestaurantName.text = list!![position].name
            holder.textViewRestaurantCity.text = list!![position].city
            holder.textViewRestaurantDistance.text = "${list!![position].getDistance(userLocation)} km"

            val imageReference = storage.reference.child("propics/${list!![position].id}.jpg")
        }

    }

    override fun getItemCount(): Int = restaurantList!!.size

}