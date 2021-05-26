package im.gian.tr.home.home

import android.location.Location
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import im.gian.tr.R
import im.gian.tr.home.model.Restaurant
import java.math.RoundingMode

class RestaurantCardAdapter(private var restaurantList: List<Restaurant>?, private val savedList: MutableList<Restaurant>?, private val userLocation: Location?, private val sortByDistance: Boolean) : RecyclerView.Adapter<RestaurantCardAdapter.RestaurantCardViewHolder>() {
    private val storage = Firebase.storage
    //private val db = Firebase.firestore
    //private val user = Firebase.auth

    init {
        if(sortByDistance){
            val sortedRestaurantList = restaurantList?.toMutableList()
            sortedRestaurantList?.sortBy { it.getDistance(userLocation) }
            restaurantList = sortedRestaurantList
        }
    }

    class RestaurantCardViewHolder(private val row: View) : RecyclerView.ViewHolder(row) {
        val textViewRestaurantName: TextView = row.findViewById(R.id.textViewRestaurantName)
        val textViewRestaurantCity: TextView = row.findViewById(R.id.textViewRestaurantCity)
        val textViewRestaurantDistance: TextView = row.findViewById(R.id.textViewRestaurantDistance)
        val imageViewRestaurant: ImageView = row.findViewById(R.id.imageViewRestaurant)
        val checkBoxRestaurant: CheckBox = row.findViewById(R.id.checkboxRestaurant)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantCardViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.restaurant_card_view,
            parent, false)
        return RestaurantCardViewHolder(layout)
    }

    override fun onBindViewHolder(holder: RestaurantCardViewHolder, position: Int) {
        if(restaurantList != null){
            holder.textViewRestaurantName.text = restaurantList!![position].name
            holder.textViewRestaurantCity.text = restaurantList!![position].city
            holder.textViewRestaurantDistance.text = "${restaurantList!![position].getDistance(userLocation)} km"

            if(restaurantList!![position] in savedList!!)
                holder.checkBoxRestaurant.isChecked = true

            val imageReference = storage.reference.child("propics/${restaurantList!![position].id}.jpg")
            imageReference.downloadUrl.addOnSuccessListener {
                Glide.with(holder.imageViewRestaurant).load(it).into(holder.imageViewRestaurant)
            }
        }

    }

    override fun getItemCount(): Int = restaurantList!!.size

}